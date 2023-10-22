package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    private float total;

    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(columnDefinition = "bit default 0")
    private boolean status;
}
