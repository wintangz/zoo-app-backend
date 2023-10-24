package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.response.OrderDTO;
import net.wintang.zooapp.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public static OrderDTO mapToOrderDto(Order order) {
        return new OrderDTO(order);
    }

    public static List<OrderDTO> mapToOrderDto(List<Order> order) {
        return order.stream().map(OrderDTO::new).toList();
    }
}
