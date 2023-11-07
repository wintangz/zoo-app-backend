package net.wintang.zooapp.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.dto.mapper.AnimalMapper;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.FeedingSchedule;
import net.wintang.zooapp.entity.FeedingScheduleDetail;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FeedingScheduleResponseDTO implements Serializable {

    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime feedingTime;
    private UserResponseDTO zooTrainerId;
    private AnimalResponseDTO animalId;
    private boolean fed;
    private UserResponseDTO feederId;
    private String confirmationImgUrl;
    private List<FeedingScheduleDetail> details;

    public FeedingScheduleResponseDTO(FeedingSchedule feedingSchedule) {
        this.id = feedingSchedule.getId();
        this.createdDate = feedingSchedule.getCreatedDate();
        this.feedingTime = feedingSchedule.getFeedingTime();
        this.zooTrainerId = UserMapper.mapToUserDTO(feedingSchedule.getZooTrainer());
        this.animalId = AnimalMapper.mapToAnimalDTO(feedingSchedule.getAnimal());
        this.fed = feedingSchedule.isFed();
        this.feederId = feedingSchedule.getFeeder() != null ? UserMapper.mapToUserDTO(feedingSchedule.getFeeder()) : null;
        this.details = feedingSchedule.getFeeding_schedule_details();
    }
}