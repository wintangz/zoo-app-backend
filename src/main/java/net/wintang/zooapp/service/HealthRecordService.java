package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.HealthRecordMapper;
import net.wintang.zooapp.dto.request.HealthRecordRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.HealthRecord;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.HealthRecordRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordService implements IHealthRecordService {

    private final HealthRecordRepository healthRecordRepository;

    @Autowired
    public HealthRecordService(HealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getHealthRecords() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        HealthRecordMapper.mapToHealthRecordDto(healthRecordRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getHealthRecordById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        HealthRecordMapper.mapToHealthRecordDto(healthRecordRepository.findById(id).orElseThrow(() -> new NotFoundException("Record ID: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createHealthRecord(HealthRecordRequestDTO healthRecordRequestDTO) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HealthRecord healthRecord = HealthRecordMapper.mapToHealthRecordEntity(healthRecordRequestDTO);
        healthRecord.setZooTrainer(User.builder().id(Integer.parseInt(authenticatedUser.getUsername())).build());
        healthRecord.setAnimal(Animal.builder().id(healthRecordRequestDTO.getAnimalId()).build());
        healthRecordRepository.save(healthRecord);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        healthRecordRequestDTO)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateHealthRecordById(int id, HealthRecordRequestDTO healthRecordRequestDTO) throws NotFoundException {
        if (healthRecordRepository.existsById(id)) {
            UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            HealthRecord healthRecord = HealthRecordMapper.mapToHealthRecordEntity(healthRecordRequestDTO);
            healthRecord.setZooTrainer(User.builder().id(Integer.parseInt(authenticatedUser.getUsername())).build());
            healthRecord.setAnimal(Animal.builder().id(healthRecordRequestDTO.getAnimalId()).build());
            healthRecord.setId(id);
            healthRecordRepository.save(healthRecord);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Record: " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteHealthRecordById(int id) throws NotFoundException {
        if (healthRecordRepository.existsById(id)) {
            healthRecordRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Record: " + id);
    }
}
