package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.FeedingScheduleRequestDTO;
import net.wintang.zooapp.dto.response.FeedingScheduleResponseDTO;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.FeedingSchedule;

import java.util.List;

public class FeedingScheduleMapper {

    public static List<FeedingScheduleResponseDTO> mapToFeedScheduleDto(List<FeedingSchedule> feedingSchedules) {
        return feedingSchedules.stream().map(FeedingScheduleResponseDTO::new).toList();
    }

    public static FeedingSchedule mapToFeedScheduleEntity(FeedingScheduleRequestDTO feedingScheduleRequestDto) {
        return FeedingSchedule.builder()
                .feedingTime(feedingScheduleRequestDto.getFeedingTime())
                .animal(Animal.builder().id(feedingScheduleRequestDto.getAnimalId()).build())
                .build();
    }

    public static FeedingSchedule mapToFeedScheduleEntity(FeedingScheduleRequestDTO feedingScheduleRequestDto, FeedingSchedule old) {
        old.setFeedingTime(feedingScheduleRequestDto.getFeedingTime());
        old.setAnimal(Animal.builder().id(feedingScheduleRequestDto.getAnimalId()).build());
        return old;
    }
}
