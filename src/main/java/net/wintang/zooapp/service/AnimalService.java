package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.dto.AnimalDTO;
import net.wintang.zooapp.repository.AnimalRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService implements IAnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    private Object mapToDTO(List<Animal> animals) {
        return animals.stream().map(AnimalDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> findAllAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToDTO(animalRepository.findAll()))
        );
    }
}
