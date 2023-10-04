package net.wintang.zooapp.controller;

import net.wintang.zooapp.service.IAnimalSpeciesService;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/species")
public class AnimalSpeciesController {

    private final IAnimalSpeciesService animalSpeciesService;

    @Autowired
    public AnimalSpeciesController(IAnimalSpeciesService animalSpeciesService) {
        this.animalSpeciesService = animalSpeciesService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllSpecies() {
        return animalSpeciesService.getAllSpecies();
    }
}
