package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.FeedingScheduleRequestDTO;
import net.wintang.zooapp.dto.response.FeedingScheduleResponseDTO;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalDiet;
import net.wintang.zooapp.entity.FeedingSchedule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedingScheduleMapper {

    public List<FeedingScheduleResponseDTO> mapToFeedScheduleDto(List<FeedingSchedule> feedingSchedules) {
        return feedingSchedules.stream().map(FeedingScheduleResponseDTO::new).toList();
    }

    public FeedingSchedule mapToFeedScheduleEntity(FeedingScheduleRequestDTO feedingScheduleRequestDto) {
        return FeedingSchedule.builder()
                .feedingTime(feedingScheduleRequestDto.getFeedingTime())
                .animal(Animal.builder().id(feedingScheduleRequestDto.getAnimalId()).build())
                .diet(AnimalDiet.builder().id(feedingScheduleRequestDto.getDietId()).build())
                .build();
    }
}
