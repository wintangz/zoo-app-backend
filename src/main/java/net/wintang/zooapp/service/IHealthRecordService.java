package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.HealthRecordRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IHealthRecordService {
    ResponseEntity<ResponseObject> getHealthRecords();

    ResponseEntity<ResponseObject> getHealthRecordById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createHealthRecord(HealthRecordRequestDTO healthRecordRequestDTO);

    ResponseEntity<ResponseObject> updateHealthRecordById(int id, HealthRecordRequestDTO healthRecordRequestDTO) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteHealthRecordById(int id) throws NotFoundException;
}
