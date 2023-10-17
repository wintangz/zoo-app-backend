package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.FeedingScheduleRequestDto;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IFeedingScheduleService {
    ResponseEntity<ResponseObject> getFeedingSchedules();

    ResponseEntity<ResponseObject> createFeedingSchedule(FeedingScheduleRequestDto feedingScheduleRequestDto) throws NotFoundException;

    ResponseEntity<ResponseObject> updateFeedingSchedule();

    ResponseEntity<ResponseObject> deleteFeedingSchedule();

    ResponseEntity<ResponseObject> confirmFeedingSchedule();

}
