package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.entity.OrderDetail;
import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.repository.OrderDetailRepository;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.repository.TicketRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> createOrder(OrderRequestDTO orderDto, String id) {

        if (!orderDto.getPaymentMethod().equals("VNPAY")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.INVALID, orderDto.getPaymentMethod())
            );
        }

        Order order = new Order();
        Optional<User> customer = userRepository.findById(Integer.parseInt(id));
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
}
