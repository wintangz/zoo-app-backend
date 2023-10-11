package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;
import net.wintang.zooapp.dto.OrderDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "[order]")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private LocalDateTime createdDate;

    private float total;

    private String paymentMethod;

    @Lob
    private String qrCodeUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    private boolean status;

    public Order(OrderDTO orderDto) {
        this.total = orderDto.getTotal();
        this.paymentMethod = orderDto.getPaymentMethod();
        this.qrCodeUrl = orderDto.getQrCodeUrl();
        this.customer = orderDto.getCustomer();
    }
}
