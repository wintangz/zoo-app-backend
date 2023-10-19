package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.FoodRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final IFoodService foodService;

    @Autowired
    public FoodController(IFoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getFoods() {
        return foodService.getFoods();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getFoodById(@PathVariable int id) throws NotFoundException {
        return foodService.getFoodById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createFood(@Valid @RequestBody FoodRequestDTO foodRequestDTO) {
        return foodService.createFood(foodRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateFoodById(@PathVariable int id, @Valid @RequestBody FoodRequestDTO foodRequestDTO) throws NotFoundException {
        return foodService.updateFoodById(id, foodRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteFoodById(@PathVariable int id) throws NotFoundException {
        return foodService.deleteFoodById(id);
    }
}
