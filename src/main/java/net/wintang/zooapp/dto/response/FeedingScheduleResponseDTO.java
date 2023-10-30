package net.wintang.zooapp.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.FeedingSchedule;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedingScheduleResponseDTO implements Serializable {

    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime feedingTime;
    private int zooTrainerId;
    private int dietId;
    private int animalId;
    private boolean fed;
    private int feederId;
    private String confirmationImgUrl;

    public FeedingScheduleResponseDTO(FeedingSchedule feedingSchedule) {
        this.id = feedingSchedule.getId();
        this.createdDate = feedingSchedule.getCreatedDate();
        this.feedingTime = feedingSchedule.getFeedingTime();
        this.zooTrainerId = feedingSchedule.getZooTrainer().getId();
        this.dietId = feedingSchedule.getDiet().getId();
        this.animalId = feedingSchedule.getAnimal().getId();
        this.fed = feedingSchedule.isFed();
        this.feederId = feedingSchedule.getFeeder() != null ? feedingSchedule.getFeeder().getId() : 0;
        this.confirmationImgUrl = feedingSchedule.getConfirmationImgUrl();
    }
}