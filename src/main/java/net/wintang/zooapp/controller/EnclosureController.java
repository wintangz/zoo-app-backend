package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.EnclosureRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IEnclosureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enclosures")
public class EnclosureController {

    private final IEnclosureService enclosureService;

    public EnclosureController(IEnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getEnclosures() {
        return enclosureService.getEnclosures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEnclosureById(@PathVariable int id) throws NotFoundException {
        return enclosureService.getEnclosureById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createEnclosure(@Valid @RequestBody EnclosureRequestDTO enclosureRequestDTO) {
        return enclosureService.createEnclosure(enclosureRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateEnclosureById(@PathVariable int id, @Valid @RequestBody EnclosureRequestDTO enclosureRequestDTO) throws NotFoundException {
        return enclosureService.updateEnclosureById(id, enclosureRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteEnclosureById(@PathVariable int id) throws NotFoundException {
        return enclosureService.deleteEnclosureById(id);
    }
}
