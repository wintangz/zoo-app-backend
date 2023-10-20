package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.SpeciesRequestDTO;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.ISpeciesService;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {

    private final ISpeciesService animalSpeciesService;

    @Autowired
    public SpeciesController(ISpeciesService animalSpeciesService) {
        this.animalSpeciesService = animalSpeciesService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getSpecies() {
        return animalSpeciesService.getSpecies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getSpeciesById(@PathVariable int id) throws NotFoundException {
        return animalSpeciesService.getSpeciesById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createSpecies(@Valid @RequestBody SpeciesRequestDTO speciesRequestDTO) throws DuplicatedKeyException {
        return animalSpeciesService.createSpecies(speciesRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateSpeciesById(@PathVariable int id, @Valid @RequestBody SpeciesRequestDTO speciesRequestDTO) throws NotFoundException {
        return animalSpeciesService.updateSpeciesById(id, speciesRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteSpeciesById(@PathVariable int id) throws NotFoundException {
        return animalSpeciesService.deleteSpeciesById(id);
    }
}
