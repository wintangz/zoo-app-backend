package net.wintang.zooapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "feeding_schedule_detail")
public class FeedingScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "feeding_schedule_id")
    @JsonIgnore
    private FeedingSchedule feedingSchedule;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private int expectedQuantity;

    private int actualQuantity;

    @Lob
    private String imgUrl;
}
