package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AnimalDietRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IAnimalDietService {
    ResponseEntity<ResponseObject> getAnimalDiets();

    ResponseEntity<ResponseObject> getAnimalDietById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createAnimalDiet(AnimalDietRequestDTO animalDietRequestDto);

    ResponseEntity<ResponseObject> updateAnimalDietById(int id, AnimalDietRequestDTO animalDietRequestDTO) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteAnimalDietById(int id) throws NotFoundException;
}
