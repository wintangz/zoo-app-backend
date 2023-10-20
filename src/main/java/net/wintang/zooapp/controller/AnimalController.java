package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IAnimalService;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private final IAnimalService animalService;

    @Autowired
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAnimals() {
        return animalService.getAnimals();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createAnimal(@Valid @RequestBody AnimalRequestDTO animal) {
        return animalService.createAnimal(animal);
    }

    @PostMapping("/{id}/zoo-trainers/{zooTrainerId}")
    public ResponseEntity<ResponseObject> assignZooTrainerToAnimal(@PathVariable int id, @PathVariable int zooTrainerId) throws NotFoundException {
        return animalService.assignZooTrainerToAnimal(id, zooTrainerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteAnimalById(@PathVariable int id) throws NotFoundException {
        return animalService.deleteAnimalById(id);
    }

    @GetMapping("/enclosures")
    public ResponseEntity<ResponseObject> getAnimalsEnclosures() {
        return animalService.getAnimalsEnclosures();
    }

    @GetMapping("/{id}/enclosures")
    public ResponseEntity<ResponseObject> getAnimalEnclosuresByAnimalId(@PathVariable int id) {
        return animalService.getAnimalEnclosuresByAnimalId(id);
    }

    @PostMapping("/{animalId}/enclosures/{enclosureId}")
    public ResponseEntity<ResponseObject> moveInAnimalToEnclosure(@PathVariable int animalId,
                                                                  @PathVariable int enclosureId) {
        return animalService.moveInAnAnimal(enclosureId, animalId);
    }

    @PutMapping("/{animalId}/enclosures/{enclosureId}")
    public ResponseEntity<ResponseObject> moveOutAnimalFromEnclosure(@PathVariable int animalId,
                                                                     @PathVariable int enclosureId) throws NotFoundException {
        return animalService.moveOutAnAnimal(enclosureId, animalId);
    }
}
