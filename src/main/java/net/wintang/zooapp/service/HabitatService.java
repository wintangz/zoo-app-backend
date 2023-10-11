package net.wintang.zooapp.service;

import net.wintang.zooapp.repository.HabitatRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.ResponseObject;
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
                        (habitatRepository.findAll()))
        );
    }
}
