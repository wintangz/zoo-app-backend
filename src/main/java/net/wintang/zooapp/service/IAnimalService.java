package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IAnimalService {
    ResponseEntity<ResponseObject> getAllAnimals();

    ResponseEntity<ResponseObject> createAnimal(AnimalRequestDTO animalDto);
}
