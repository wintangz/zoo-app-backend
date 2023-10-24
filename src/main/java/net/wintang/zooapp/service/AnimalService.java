package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.AnimalMapper;
import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.*;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.AnimalEnclosureRepository;
import net.wintang.zooapp.repository.AnimalRepository;
import net.wintang.zooapp.repository.AnimalTrainerAssignorRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AnimalService implements IAnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalTrainerAssignorRepository animalTrainerAssignorRepository;
    private final AnimalEnclosureRepository animalEnclosureRepository;

    public AnimalService(AnimalRepository animalRepository,
                         AnimalMapper animalMapper,
                         AnimalTrainerAssignorRepository animalTrainerAssignorRepository,
                         AnimalEnclosureRepository animalEnclosureRepository) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.animalTrainerAssignorRepository = animalTrainerAssignorRepository;
        this.animalEnclosureRepository = animalEnclosureRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        AnimalMapper.mapToAnimalDTO(animalRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createAnimal(AnimalRequestDTO animalDto) {
        User zooTrainer = new User();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        zooTrainer.setId(Integer.parseInt(userDetails.getUsername()));
        Animal animal = animalMapper.mapToAnimalEntity(animalDto);
        animal.setCreatedBy(zooTrainer);
        animalRepository.save(animal);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        AnimalMapper.mapToAnimalDTO(animal))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteAnimalById(int id) throws NotFoundException {
        if (!animalRepository.existsById(id)) {
            throw new NotFoundException("Animal: " + id);
        }
        animalRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        id)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> assignZooTrainerToAnimal(int animalId, int zooTrainerId) throws NotFoundException {
        if (animalRepository.existsById(animalId)) {
            UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User assignor = User.builder().id(Integer.parseInt(authenticatedUser.getUsername())).build();
            User zooTrainer = User.builder().id(zooTrainerId).build();
            Animal animal = Animal.builder().id(animalId).build();
            AnimalTrainerAssignor animalTrainerAssignor = AnimalTrainerAssignor.builder()
                    .assignedBy(assignor)
                    .animal(animal)
                    .trainer(zooTrainer)
                    .build();
            animalTrainerAssignorRepository.save(animalTrainerAssignor);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            animalId));
        }
        throw new NotFoundException("Animal: " + animalId);
    }

    @Override
    public ResponseEntity<ResponseObject> getAnimalsEnclosures() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        AnimalMapper.mapToAEDto(animalEnclosureRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> moveInAnAnimal(int id, int animalId) throws DuplicatedKeyException {
        if (!animalEnclosureRepository.existsByAnimalAndEnclosure(Animal.builder().id(animalId).build(),
                Enclosure.builder().id(id).build())) {
            AnimalEnclosure animalEnclosure = AnimalEnclosure.builder()
                    .animal(Animal.builder().id(animalId).build())
                    .enclosure(Enclosure.builder().id(id).build())
                    .moveInDate(LocalDateTime.now())
                    .build();
            animalEnclosureRepository.save(animalEnclosure);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            "Moved in")
            );
        }
        throw new DuplicatedKeyException("This Animal - Enclosure");
    }

    @Override
    public ResponseEntity<ResponseObject> moveOutAnAnimal(int id, int animalId) throws NotFoundException {
        AnimalEnclosure animalEnclosure = animalEnclosureRepository
                .findByAnimalAndEnclosure(
                        Animal.builder().id(animalId).build(),
                        Enclosure.builder().id(id).build())
                .orElseThrow(() -> new NotFoundException("Animal or Enclosure"));
        animalEnclosure.setAnimal(Animal.builder().id(animalId).build());
        animalEnclosure.setEnclosure(Enclosure.builder().id(id).build());
        animalEnclosure.setMoveOutDate(LocalDateTime.now());
        animalEnclosureRepository.save(animalEnclosure);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        "Moved out")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getAnimalEnclosuresByAnimalId(int id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        AnimalMapper.mapToAEDto(animalEnclosureRepository.findByAnimal(Animal.builder().id(id).build())))
        );
    }
}
