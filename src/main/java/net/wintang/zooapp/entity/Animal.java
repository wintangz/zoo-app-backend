package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private boolean sex;

    private float size;

    private boolean heightLength;

    private float weight;

    private String imgUrl;

    @Column(insertable = false, updatable = false, nullable = true)
    private LocalDateTime arrivalDate;

    private LocalDateTime dateOfBirth;

    private LocalDateTime dateOfDeath;

    private String origin;

    private String healthStatus;
}
