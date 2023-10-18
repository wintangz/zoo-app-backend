package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IEnclosureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
