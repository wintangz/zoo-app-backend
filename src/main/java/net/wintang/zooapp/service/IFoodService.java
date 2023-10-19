package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.FoodRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IFoodService {
    ResponseEntity<ResponseObject> getFoods();

    ResponseEntity<ResponseObject> getFoodById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createFood(FoodRequestDTO foodRequestDTO);

    ResponseEntity<ResponseObject> updateFoodById(int id, FoodRequestDTO foodRequestDTO) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteFoodById(int id) throws NotFoundException;
}
