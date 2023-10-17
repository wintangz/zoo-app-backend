package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.FeedingScheduleRequestDto;
import net.wintang.zooapp.dto.response.FeedingScheduleResponseDto;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalDiet;
import net.wintang.zooapp.entity.FeedingSchedule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedingScheduleMapper {

    public List<FeedingScheduleResponseDto> mapToFeedScheduleDto(List<FeedingSchedule> feedingSchedules) {
        return feedingSchedules.stream().map(FeedingScheduleResponseDto::new).toList();
    }

    public FeedingSchedule mapToFeedScheduleEntity(FeedingScheduleRequestDto feedingScheduleRequestDto) {
        return FeedingSchedule.builder()
                .feedingTime(feedingScheduleRequestDto.getFeedingTime())
                .animal(Animal.builder().id(feedingScheduleRequestDto.getAnimalId()).build())
                .diet(AnimalDiet.builder().id(feedingScheduleRequestDto.getDietId()).build())
                .build();
    }
}
