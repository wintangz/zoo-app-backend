package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.HabitatMapper;
import net.wintang.zooapp.dto.request.HabitatRequestDTO;
import net.wintang.zooapp.entity.Habitat;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.HabitatRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HabitatService implements IHabitatService {

    private final HabitatRepository habitatRepository;

    @Autowired
    public HabitatService(HabitatRepository habitatRepository) {
        this.habitatRepository = habitatRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getHabitats() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        HabitatMapper.mapToHabitatDTO(habitatRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getHabitatById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        HabitatMapper.mapToHabitatDTO(habitatRepository.findById(id).orElseThrow(() -> new NotFoundException("Habitat ID: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteHabitatById(int id) throws NotFoundException {
        if (habitatRepository.existsById(id)) {
            habitatRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Habitat ID: " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> createHabitat(HabitatRequestDTO habitatRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        HabitatMapper.mapToHabitatEntity(habitatRequestDTO))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateHabitatById(int id, HabitatRequestDTO habitatRequestDTO) throws NotFoundException {
        Habitat habitat = habitatRepository.findById(id).orElseThrow(() -> new NotFoundException("Habitat ID: " + id));
        habitatRepository.save(HabitatMapper.mapToHabitatEntity(habitatRequestDTO, habitat));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        id)
        );
    }
}
