package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.Order;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private int id;
    private LocalDateTime createdDate;
    private float total;
    private String paymentMethod;
    private UserResponseDTO customer;
    private boolean status;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.createdDate = order.getCreatedDate();
        this.total = order.getTotal();
        this.paymentMethod = order.getPaymentMethod();
        this.customer = UserMapper.mapToUserDTO(order.getCustomer());
        this.status = order.isStatus();
    }
}