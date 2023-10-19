package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.AnimalDietRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IAnimalDietService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diets")
public class AnimalDietController {

    private final IAnimalDietService animalDietService;

    public AnimalDietController(IAnimalDietService animalDietService) {
        this.animalDietService = animalDietService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getDiets() {
        return animalDietService.getAnimalDiets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getDietById(@PathVariable int id) throws NotFoundException {
        return animalDietService.getAnimalDietById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createDiet(@RequestBody AnimalDietRequestDTO animalDietRequestDto) {
        return animalDietService.createAnimalDiet(animalDietRequestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateDietById(@PathVariable int id, @Valid @RequestBody AnimalDietRequestDTO animalDietRequestDTO) throws NotFoundException {
        return animalDietService.updateAnimalDietById(id, animalDietRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteDietById(@PathVariable int id) throws NotFoundException {
        return animalDietService.deleteAnimalDietById(id);
    }
}
