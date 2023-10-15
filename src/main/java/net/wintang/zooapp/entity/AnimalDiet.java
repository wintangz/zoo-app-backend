package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "animal_diet")
public class AnimalDiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String type;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User creator;

    @ManyToMany
    @JoinTable(name = "diet_food",
            joinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id"))
    private List<Food> foodList = new ArrayList<>();
}