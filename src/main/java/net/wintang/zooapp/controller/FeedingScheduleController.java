package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.FeedingScheduleConfirmDTO;
import net.wintang.zooapp.dto.request.FeedingScheduleRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IFeedingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feeding_schedules")
public class FeedingScheduleController {

    private final IFeedingScheduleService feedingScheduleService;

    @Autowired
    public FeedingScheduleController(IFeedingScheduleService feedingScheduleService) {
        this.feedingScheduleService = feedingScheduleService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getFeedingSchedules() {
        return feedingScheduleService.getFeedingSchedules();
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<ResponseObject> getFeedingSchedulesByAnimal(@PathVariable int id) {
        return feedingScheduleService.getFeedingSchedulesByAnimal(id);
    }

    @GetMapping("/enclosures/{id}")
    public ResponseEntity<ResponseObject> getFeedingSchedulesByEnclosure(@PathVariable int id) {
        return feedingScheduleService.getFeedingSchedulesByEnclosure(id);
    }

    @GetMapping("/zoo-trainers/{id}")
    public ResponseEntity<ResponseObject> getFeedingSchedulesByTrainer(@PathVariable int id) {
        return feedingScheduleService.getFeedingSchedulesByTrainer(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createFeedingSchedule(@Valid @RequestBody FeedingScheduleRequestDTO feedingScheduleRequestDto) throws NotFoundException {
        return feedingScheduleService.createFeedingSchedule(feedingScheduleRequestDto);
    }

    @PutMapping("/{id}/confirmation")
    public ResponseEntity<ResponseObject> confirmFeedingSchedule(@PathVariable int id, @Valid @RequestBody FeedingScheduleConfirmDTO feedingScheduleConfirmDto) throws NotFoundException {
        return feedingScheduleService.confirmFeedingSchedule(id, feedingScheduleConfirmDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateFeedingScheduleById(@PathVariable int id, @RequestBody FeedingScheduleRequestDTO feedingScheduleRequestDTO) throws NotFoundException {
        return feedingScheduleService.updateFeedingScheduleById(id, feedingScheduleRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteFeedingScheduleById(@PathVariable int id) throws NotFoundException {
        return feedingScheduleService.deleteFeedingScheduleById(id);
    }
}
