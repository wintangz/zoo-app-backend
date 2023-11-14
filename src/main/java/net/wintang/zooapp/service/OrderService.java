package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.OrderMapper;
import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.entity.OrderDetail;
import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import net.wintang.zooapp.repository.OrderDetailRepository;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.repository.TicketRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public OrderService(OrderDetailRepository orderDetailRepository,
                        OrderRepository orderRepository,
                        UserRepository userRepository,
                        TicketRepository ticketRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        OrderMapper.mapToOrderDto(orderRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getOrderById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        OrderMapper.mapToOrderDto(orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order ID: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getOrdersByCustomerId(int id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        OrderMapper.mapToOrderDto(orderRepository.findAllByCustomer(User.builder().id(id).build())))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getPurchasedTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        orderDetailRepository.findAll())
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getPurchasedTicketsByOrderId(int id) throws NotFoundException {
        if (orderRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            orderDetailRepository.findAllByOrderId(id))
            );
        }
        throw new NotFoundException("Order ID: " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> createOrder(OrderRequestDTO orderDto) {

        if (!orderDto.getPaymentMethod().equals("VNPAY")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.INVALID, orderDto.getPaymentMethod())
            );
        }

        Order order = new Order();
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> customer = userRepository.findById(Integer.parseInt(authenticatedUser.getUsername()));
        customer.ifPresent(order::setCustomer);

        if (order.getCustomer() != null) {
            List<Ticket> allTickets = ticketRepository.findAll();
            float totalOrderPrice = 0;

            for (OrderRequestDTO.TicketItem ticketItem : orderDto.getTicketItems()) {
                //Check if ticketId is valid
                Optional<Ticket> matchingTicket = allTickets.stream()
                        .filter(ticket -> ticket.getId() == ticketItem.getTicketId())
                        .findFirst();

                if (matchingTicket.isPresent()) {
                    //Ticket is valid, calculate total price
                    double ticketPrice = matchingTicket.get().getPrice();
                    int quantity = ticketItem.getQuantity();
                    totalOrderPrice += (ticketPrice * quantity);
                } else {
                    //Return INVALID TICKET
                }
            }
            order.setTotal(totalOrderPrice);
            order.setPaymentMethod(orderDto.getPaymentMethod());
            order.setStatus(false);
            orderRepository.save(order);

            //Save orderDetails
            for (OrderRequestDTO.TicketItem ticketItem : orderDto.getTicketItems()) {
                int ticketId = ticketItem.getTicketId();
                Ticket ticket = ticketRepository.findById(ticketId)
                        .orElseThrow();

                for (int i = 0; i < ticketItem.getQuantity(); i++) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setTicket(ticket);
                    orderDetail.setOrder(order);
                    orderDetailRepository.save(orderDetail);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            new OrderResponseDTO(totalOrderPrice, order.getId()))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                        new OrderResponseDTO())
        );
    }

    @Override
    public ResponseEntity<ResponseObject> verifyTickets(int orderId, int customerId, int ticketId, String ticketType, LocalDateTime issuedDate, String hashData) throws NoSuchAlgorithmException, InvalidKeyException, NotFoundException, PermissionDeniedException {
        String generatedHashData = Encryptor.calculateHMAC(ApplicationConstants.Keys.KEY,
                "?orderId=" + orderId
                        + "&customerId=" + customerId
                        + "&ticket=" + ticketId
                        + "&type=" + ticketType
                        + "&issuedDate=" + issuedDate + "&hashData=");
        if (generatedHashData.equals(hashData)) {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order ID: " + orderId));
            if (customerId == order.getCustomer().getId()) {
                List<OrderDetail> tickets = orderDetailRepository.findAllByOrderId(orderId);
                boolean ticketExist = false;
                for (OrderDetail t : tickets) {
                    if (t.getId() == ticketId && t.getTicket().getName().equals(ticketType)) {
                        ticketExist = true;
                        break;
                    }
                }
                if (ticketExist) {
                    if (!issuedDate.isBefore(LocalDateTime.now().minus(30, ChronoUnit.DAYS))) {
                        OrderDetail ticket = orderDetailRepository.findById(ticketId).orElseThrow();
                        if (!ticket.isChecked()) {
                            ticket.setChecked(true);
                            UserDetails staff = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                            ticket.setCheckedBy(User.builder().id(Integer.parseInt(staff.getUsername())).build());
                            orderDetailRepository.save(ticket);
                            return ResponseEntity.status(HttpStatus.OK).body(
                                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                            ApplicationConstants.ResponseMessage.SUCCESS,
                                            ticket)
                            );
                        }
                        throw new PermissionDeniedException("Ticket is used");
                    }
                    throw new PermissionDeniedException("Ticket Expired");
                }
                throw new NotFoundException("Ticket ID: " + ticketId);
            }
            throw new NotFoundException("Customer: " + customerId);
        }
        throw new NotFoundException("Validated Details");
    }
}
