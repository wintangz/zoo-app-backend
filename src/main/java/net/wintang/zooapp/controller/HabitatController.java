package net.wintang.zooapp.controller;

import net.wintang.zooapp.service.HabitatService;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habitats")
public class HabitatController {

    private final HabitatService habitatService;

    @Autowired
    public HabitatController(HabitatService habitatService) {
        this.habitatService = habitatService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllHabitats() {
        return habitatService.findAllHabitats();
    }
}
