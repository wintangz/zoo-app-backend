package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.HabitatRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IHabitatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habitats")
public class HabitatController {

    private final IHabitatService habitatService;

    @Autowired
    public HabitatController(IHabitatService habitatService) {
        this.habitatService = habitatService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getHabitats() {
        return habitatService.getHabitats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getHabitatById(@PathVariable int id) throws NotFoundException {
        return habitatService.getHabitatById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createHabitat(@Valid @RequestBody HabitatRequestDTO habitatRequestDTO) {
        return habitatService.createHabitat(habitatRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateHabitatById(@PathVariable int id, @Valid @RequestBody HabitatRequestDTO habitatRequestDTO) throws NotFoundException {
        return habitatService.updateHabitatById(id, habitatRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteHabitatById(@PathVariable int id) throws NotFoundException {
        return habitatService.deleteHabitatById(id);
    }
}
