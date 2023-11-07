package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feeding_schedule_detail")
public class FeedingScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "feeding_schedule_id")
    private FeedingSchedule feedingSchedule;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private int expectedQuantity;

    private int actualQuantity;

    @Lob
    private String imgUrl;
}
