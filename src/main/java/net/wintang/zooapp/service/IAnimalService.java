package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IAnimalService {
    ResponseEntity<ResponseObject> getAllAnimals();

    ResponseEntity<ResponseObject> createAnimal(AnimalRequestDTO animalDto);

    ResponseEntity<ResponseObject> deleteAnimalById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> assignZooTrainerToAnimal(int id, int zooTrainerId) throws NotFoundException;
}
