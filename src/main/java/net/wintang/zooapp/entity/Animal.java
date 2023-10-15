package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private boolean sex;

    private String imgUrl;

    private LocalDateTime arrivalDate;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime dateOfBirth;

    private LocalDateTime dateOfDeath;

    private String origin;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    @Column(columnDefinition = "bit default 0")
    private boolean status;

    @OneToOne
    @JoinColumn(name = "created_by", unique = false)
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "approved_by", nullable = true, unique = false)
    private User approvedBy;
}
