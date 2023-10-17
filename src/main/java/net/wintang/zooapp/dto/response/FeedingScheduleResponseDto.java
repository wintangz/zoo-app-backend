package net.wintang.zooapp.dto.response;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.FeedingSchedule;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedingScheduleResponseDto implements Serializable {
    int id;
    LocalDateTime createdDate;
    LocalDateTime feedingTime;
    boolean approved;
    int zooTrainerId;
    int staffId;
    int dietId;
    int animalId;
    boolean fed;
    int feederId;

    public FeedingScheduleResponseDto(FeedingSchedule feedingSchedule) {
        this.id = feedingSchedule.getId();
        this.createdDate = feedingSchedule.getCreatedDate();
        this.feedingTime = feedingSchedule.getFeedingTime();
        this.approved = feedingSchedule.isApproved();
        this.zooTrainerId = feedingSchedule.getZooTrainer().getId();
        this.staffId = feedingSchedule.getStaff() != null ? feedingSchedule.getStaff().getId() : 0;
        this.dietId = feedingSchedule.getDiet().getId();
        this.animalId = feedingSchedule.getAnimal().getId();
        this.fed = feedingSchedule.isFed();
        this.feederId = feedingSchedule.getFeeder() != null ? feedingSchedule.getFeeder().getId() : 0;
    }
}