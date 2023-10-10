package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.dto.OrderDTO;
import net.wintang.zooapp.repository.OrderDetailRepository;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderDetailRepository orderDetailRepository,
                        OrderRepository orderRepository,
                        UserRepository userRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllOrders() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> createNewOrder(OrderDTO orderDto, String username) {
        Order order = mapToEntity(orderDto);
        Optional<User> customer = userRepository.findByUsername(username);
        customer.ifPresent(order::setCustomer);
        if(order.getCustomer() != null) {
            orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS, orderDto)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED, orderDto)
        );
    }

    private Order mapToEntity(OrderDTO orderDto) {
        return Order.builder()
                .total(orderDto.getTotal())
                .qrCodeUrl(orderDto.getQrCodeUrl())
                .paymentMethod(orderDto.getPaymentMethod())
                .build();
    }
}
