package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.FeedingScheduleMapper;
import net.wintang.zooapp.dto.request.FeedingScheduleConfirmDTO;
import net.wintang.zooapp.dto.request.FeedingScheduleRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.FeedingSchedule;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.AnimalDietRepository;
import net.wintang.zooapp.repository.AnimalRepository;
import net.wintang.zooapp.repository.FeedingScheduleRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FeedingScheduleService implements IFeedingScheduleService {

    private final FeedingScheduleRepository feedingScheduleRepository;

    private final AnimalRepository animalRepository;

    private final AnimalDietRepository animalDietRepository;

    @Autowired
    public FeedingScheduleService(FeedingScheduleRepository feedingScheduleRepository, AnimalRepository animalRepository, AnimalDietRepository animalDietRepository) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.animalRepository = animalRepository;
        this.animalDietRepository = animalDietRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedingSchedules() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FeedingScheduleMapper.mapToFeedScheduleDto(feedingScheduleRepository.findAll())));
    }

    @Override
    public ResponseEntity<ResponseObject> createFeedingSchedule(FeedingScheduleRequestDTO feedingScheduleDto) throws NotFoundException {
        if (animalRepository.existsById(feedingScheduleDto.getAnimalId()) && animalDietRepository.existsById(feedingScheduleDto.getDietId())) {
            FeedingSchedule feedingSchedule = FeedingScheduleMapper.mapToFeedScheduleEntity(feedingScheduleDto);
            UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            feedingSchedule.setZooTrainer(User.builder().id(Integer.parseInt(authenticatedUser.getUsername())).build());
            feedingScheduleRepository.save(feedingSchedule);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            feedingScheduleDto));
        }
        throw new NotFoundException("Animal ID or Diet ID");
    }

    @Override
    public ResponseEntity<ResponseObject> updateFeedingScheduleById(int id, FeedingScheduleRequestDTO feedingScheduleDto) throws NotFoundException {
        if (animalRepository.existsById(feedingScheduleDto.getAnimalId()) && animalDietRepository.existsById(feedingScheduleDto.getDietId())) {
            FeedingSchedule oldFeedingSchedule = feedingScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Feeding Schedule ID: " + id));
            if (!oldFeedingSchedule.isFed()) {
                feedingScheduleRepository.save(FeedingScheduleMapper.mapToFeedScheduleEntity(feedingScheduleDto, oldFeedingSchedule));
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                feedingScheduleDto));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.NOT_MODIFIED, "Confirmed feeding schedule cannot be updated")
            );
        }
        throw new NotFoundException("Animal ID or Diet ID");
    }

    @Override
    public ResponseEntity<ResponseObject> confirmFeedingSchedule(int id, FeedingScheduleConfirmDTO feedingScheduleConfirmDto) throws NotFoundException {
        UserDetails feeder = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FeedingSchedule updatedFeedingSchedule = feedingScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Feeding Schedule ID: " + id));
        updatedFeedingSchedule.setConfirmationImgUrl(feedingScheduleConfirmDto.getConfirmationImgUrl());
        updatedFeedingSchedule.setFeeder(User.builder().id(Integer.parseInt(feeder.getUsername())).build());
        updatedFeedingSchedule.setFed(true);
        feedingScheduleRepository.save(updatedFeedingSchedule);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        id)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteFeedingScheduleById(int id) throws NotFoundException {
        FeedingSchedule feedingSchedule = feedingScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Feeding Schedult ID: " + id));
        if (!feedingSchedule.isFed()) {
            feedingScheduleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED, "Confirmed feeding schedule cannot be deleted")
        );
    }
}
