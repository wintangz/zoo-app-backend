package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.FeedingScheduleConfirmDTO;
import net.wintang.zooapp.dto.request.FeedingScheduleRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IFeedingScheduleService {
    ResponseEntity<ResponseObject> getFeedingSchedules();

    ResponseEntity<ResponseObject> getFeedingSchedulesByAnimal(int id);

    ResponseEntity<ResponseObject> getFeedingSchedulesByEnclosure(int id);

    ResponseEntity<ResponseObject> createFeedingSchedule(FeedingScheduleRequestDTO feedingScheduleRequestDto) throws NotFoundException;


    ResponseEntity<ResponseObject> updateFeedingScheduleById(int id, FeedingScheduleRequestDTO feedingScheduleDto) throws NotFoundException;

    ResponseEntity<ResponseObject> confirmFeedingSchedule(int id, FeedingScheduleConfirmDTO feedingScheduleConfirmDto) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteFeedingScheduleById(int id) throws NotFoundException;
}
