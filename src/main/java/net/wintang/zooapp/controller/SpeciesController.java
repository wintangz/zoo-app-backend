package net.wintang.zooapp.controller;

import net.wintang.zooapp.service.ISpeciesService;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {

    private final ISpeciesService animalSpeciesService;

    @Autowired
    public SpeciesController(ISpeciesService animalSpeciesService) {
        this.animalSpeciesService = animalSpeciesService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllSpecies() {
        return animalSpeciesService.getAllSpecies();
    }
}
