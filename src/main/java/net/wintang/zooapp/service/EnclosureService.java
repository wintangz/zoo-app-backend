package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.EnclosureMapper;
import net.wintang.zooapp.dto.response.EnclosureResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.EnclosureRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnclosureService implements IEnclosureService {

    private final EnclosureRepository enclosureRepository;

    @Autowired
    public EnclosureService(EnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getEnclosures() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        EnclosureMapper.mapToEnclosureDto(enclosureRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getEnclosureById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        new EnclosureResponseDTO(enclosureRepository.findById(id).orElseThrow(() -> new NotFoundException("Enclosure with id: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createEnclosure() {
        return null;
    }
}
