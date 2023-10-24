package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.request.HealthRecordRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IHealthRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health_records")
public class HealthRecordController {

    private final IHealthRecordService healthRecordService;

    public HealthRecordController(IHealthRecordService healthRecordService) {
        this.healthRecordService = healthRecordService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getHealthRecords() {
        return healthRecordService.getHealthRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getHealthRecordById(@PathVariable int id) throws NotFoundException {
        return healthRecordService.getHealthRecordById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> getHealthRecordById(@RequestBody HealthRecordRequestDTO healthRecordRequestDTO) {
        return healthRecordService.createHealthRecord(healthRecordRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> getHealthRecordById(@PathVariable int id, @RequestBody HealthRecordRequestDTO healthRecordRequestDTO) throws NotFoundException {
        return healthRecordService.updateHealthRecordById(id, healthRecordRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteHealthRecordById(@PathVariable int id) throws NotFoundException {
        return healthRecordService.deleteHealthRecordById(id);
    }
}
