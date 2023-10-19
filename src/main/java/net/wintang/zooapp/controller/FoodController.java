package net.wintang.zooapp.controller;

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
    public ResponseEntity<ResponseObject> createFood(@RequestBody FoodRequestDTO foodRequestDTO) {
        return foodService.createFood(foodRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateFood(@PathVariable int id, @RequestBody FoodRequestDTO foodRequestDTO) throws NotFoundException {
        return foodService.updateFood(id, foodRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteFood(@PathVariable int id) throws NotFoundException {
        return foodService.deleteFood(id);
    }
}
