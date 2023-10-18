package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feeding_schedule")
public class FeedingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime feedingTime;

    @Column(columnDefinition = "bit default 0")
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User zooTrainer;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User staff;

    @ManyToOne
    @JoinColumn(name = "diet_id")
    private AnimalDiet diet;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(columnDefinition = "bit default 0")
    private boolean fed;

    @ManyToOne
    @JoinColumn(name = "fed_by")
    private User feeder;

    @Column(updatable = false, insertable = false)
    private LocalDateTime fedTime;

    @Lob
    private String confirmationImgUrl;
}