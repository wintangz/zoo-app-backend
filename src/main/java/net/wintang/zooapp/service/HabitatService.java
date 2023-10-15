package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.HabitatMapper;
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

    private final HabitatMapper habitatMapper;

    @Autowired
    public HabitatService(HabitatRepository habitatRepository, HabitatMapper habitatMapper) {
        this.habitatRepository = habitatRepository;
        this.habitatMapper = habitatMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getHabitats() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        habitatMapper.mapToHabitatDTO(habitatRepository.findAll()))
        );
    }
}
