package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "animal_health_record")
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(insertable = false, updatable = false)
    private LocalDateTime recordedDateTime;

    private float weight;

    private float height;

    private float length;

    private float temperature;

    private String lifeStage;

    @Lob
    private String diagnosis;

    @Lob
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "recorded_by")
    private User zooTrainer;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}