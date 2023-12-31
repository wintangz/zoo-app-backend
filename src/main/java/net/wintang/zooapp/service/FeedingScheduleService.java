package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.FeedingScheduleMapper;
import net.wintang.zooapp.dto.request.FeedingScheduleConfirmDTO;
import net.wintang.zooapp.dto.request.FeedingScheduleDetailConfirmDTO;
import net.wintang.zooapp.dto.request.FeedingScheduleDetailRequestDTO;
import net.wintang.zooapp.dto.request.FeedingScheduleRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.*;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.*;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedingScheduleService implements IFeedingScheduleService {

    private final FeedingScheduleRepository feedingScheduleRepository;

    private final AnimalRepository animalRepository;

    private final FeedingScheduleDetailRepository feedingScheduleDetailRepository;

    private final AnimalEnclosureRepository animalEnclosureRepository;
    private final AnimalTrainerAssignorRepository animalTrainerAssignorRepository;

    @Autowired
    public FeedingScheduleService(FeedingScheduleRepository feedingScheduleRepository, AnimalRepository animalRepository, FeedingScheduleDetailRepository feedingScheduleDetailRepository, AnimalEnclosureRepository animalEnclosureRepository,
                                  AnimalTrainerAssignorRepository animalTrainerAssignorRepository) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.animalRepository = animalRepository;
        this.feedingScheduleDetailRepository = feedingScheduleDetailRepository;
        this.animalEnclosureRepository = animalEnclosureRepository;
        this.animalTrainerAssignorRepository = animalTrainerAssignorRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedingSchedules() {
        List<FeedingSchedule> feedingSchedules = feedingScheduleRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FeedingScheduleMapper.mapToFeedScheduleDto(feedingScheduleRepository.findAll())));
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedingSchedulesByAnimal(int id) {
        List<FeedingSchedule> feedingSchedules = feedingScheduleRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FeedingScheduleMapper.mapToFeedScheduleDto(feedingSchedules.stream().filter(feedingSchedule -> feedingSchedule.getAnimal().getId() == id).toList())));
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedingSchedulesByEnclosure(int id) {
        List<FeedingSchedule> feedingSchedules = feedingScheduleRepository.findAll();
        List<FeedingSchedule> filteredSchedules = feedingSchedules.stream()
                .filter(schedule -> animalEnclosureRepository.findByAnimalAndEnclosureAndMoveOutDate(schedule.getAnimal(), Enclosure.builder().id(id).build(), null).isPresent())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FeedingScheduleMapper.mapToFeedScheduleDto(filteredSchedules)));
    }

    @Override
    public ResponseEntity<ResponseObject> getFeedingSchedulesByTrainer(int id) {
        List<FeedingSchedule> feedingSchedules = feedingScheduleRepository.findAll();
        List<FeedingSchedule> filteredSchedules = feedingSchedules.stream()
                .filter(schedule -> schedule.getAnimal().getAnimalTrainerAssignors().stream()
                        .anyMatch(obj -> obj.getTrainer().getId() == id && obj.getUnassignedDate() == null))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        FeedingScheduleMapper.mapToFeedScheduleDto(filteredSchedules)));
    }

    @Override
    public ResponseEntity<ResponseObject> createFeedingSchedule(FeedingScheduleRequestDTO feedingScheduleDto) throws NotFoundException {
        if (animalRepository.existsById(feedingScheduleDto.getAnimalId())) {
            FeedingSchedule feedingSchedule = FeedingScheduleMapper.mapToFeedScheduleEntity(feedingScheduleDto);
            UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            feedingSchedule.setZooTrainer(User.builder().id(Integer.parseInt(authenticatedUser.getUsername())).build());

            FeedingSchedule saved = feedingScheduleRepository.save(feedingSchedule);

            for (FeedingScheduleDetailRequestDTO detail : feedingScheduleDto.getDetails()) {
                feedingScheduleDetailRepository.save(
                        FeedingScheduleDetail.builder()
                                .feedingSchedule(saved)
                                .expectedQuantity(detail.getExpectedQuantity())
                                .food(new Food(detail.getFoodId()))
                                .build());
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            feedingScheduleDto));
        }
        throw new NotFoundException("Animal ID or Diet ID");
    }

    @Override
    public ResponseEntity<ResponseObject> updateFeedingScheduleById(int id, FeedingScheduleRequestDTO feedingScheduleDto) throws NotFoundException {
        if (animalRepository.existsById(feedingScheduleDto.getAnimalId())) {
            FeedingSchedule oldFeedingSchedule = feedingScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Feeding Schedule ID: " + id));
            if (!oldFeedingSchedule.isFed()) {
                feedingScheduleRepository.save(FeedingScheduleMapper.mapToFeedScheduleEntity(feedingScheduleDto, oldFeedingSchedule));
                for (FeedingScheduleDetailRequestDTO detail : feedingScheduleDto.getDetails()) {
                    Optional<FeedingScheduleDetail> old = feedingScheduleDetailRepository.findByFoodAndFeedingSchedule(new Food(detail.getFoodId()), FeedingSchedule.builder().id(id).build());
                    if (old.isPresent()) {
                        old.get().setExpectedQuantity(detail.getExpectedQuantity());
                        feedingScheduleDetailRepository.save(old.get());
                    } else {
                        FeedingScheduleDetail newDetail = FeedingScheduleDetail.builder().feedingSchedule(FeedingSchedule.builder().id(id).build()).food(new Food(detail.getFoodId())).expectedQuantity(detail.getExpectedQuantity()).build();
                        feedingScheduleDetailRepository.save(newDetail);
                    }
                }
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
        updatedFeedingSchedule.setFeeder(User.builder().id(Integer.parseInt(feeder.getUsername())).build());
        updatedFeedingSchedule.setFed(true);
        feedingScheduleRepository.save(updatedFeedingSchedule);
        List<FeedingScheduleDetail> details = feedingScheduleDetailRepository.findAllByFeedingSchedule(updatedFeedingSchedule).orElseThrow(() -> new NotFoundException(""));
        for (FeedingScheduleDetail detail : details) {
            for (FeedingScheduleDetailConfirmDTO confirmDetail : feedingScheduleConfirmDto.getDetails()) {
                if (confirmDetail.getId() == detail.getId()) {
                    detail.setImgUrl(confirmDetail.getImgUrl());
                    detail.setActualQuantity(confirmDetail.getActualQuantity());
                    feedingScheduleDetailRepository.save(detail);
                }
            }
        }
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
            feedingScheduleDetailRepository.deleteAll(feedingSchedule.getFeeding_schedule_details());
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
