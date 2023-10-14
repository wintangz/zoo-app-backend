package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.AnimalMapper;
import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.repository.AnimalRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnimalService implements IAnimalService {

    private final AnimalRepository animalRepository;

    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getAllAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        animalMapper.mapToAnimalDTO(animalRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createAnimal(AnimalRequestDTO animalDto) {
        animalRepository.save(animalMapper.mapToAnimalEntity(animalDto));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        animalDto)
        );
    }
}
