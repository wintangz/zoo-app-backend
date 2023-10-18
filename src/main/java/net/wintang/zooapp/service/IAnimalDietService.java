package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AnimalDietRequestDto;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IAnimalDietService {
    ResponseEntity<ResponseObject> getAnimalDiets();

    ResponseEntity<ResponseObject> getAnimalDietById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createAnimalDiet(AnimalDietRequestDto animalDietRequestDto);

    ResponseEntity<ResponseObject> deleteAnimalDIet(int id) throws NotFoundException;
}
