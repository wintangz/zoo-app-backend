package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nationalized
    private String name;

    private float price;

    @Nationalized
    private String type;

    @Nationalized
    private String description;

    @Lob
    @Nationalized
    private String imgUrl;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    private boolean status;
}
