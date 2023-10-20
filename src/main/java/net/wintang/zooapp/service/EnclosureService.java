package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.EnclosureMapper;
import net.wintang.zooapp.dto.request.EnclosureRequestDTO;
import net.wintang.zooapp.dto.response.EnclosureResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.Enclosure;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.AnimalEnclosureRepository;
import net.wintang.zooapp.repository.AnimalRepository;
import net.wintang.zooapp.repository.EnclosureRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnclosureService implements IEnclosureService {

    private final EnclosureRepository enclosureRepository;
    private final AnimalRepository animalRepository;
    private final AnimalEnclosureRepository animalEnclosureRepository;

    @Autowired
    public EnclosureService(EnclosureRepository enclosureRepository,
                            AnimalRepository animalRepository,
                            AnimalEnclosureRepository animalEnclosureRepository) {
        this.enclosureRepository = enclosureRepository;
        this.animalRepository = animalRepository;
        this.animalEnclosureRepository = animalEnclosureRepository;
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
    public ResponseEntity<ResponseObject> createEnclosure(EnclosureRequestDTO enclosureRequestDTO) {
        enclosureRepository.save(EnclosureMapper.mapToEnclosureEntity(enclosureRequestDTO));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        enclosureRequestDTO)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateEnclosureById(int id, EnclosureRequestDTO enclosureRequestDTO) throws NotFoundException {
        Enclosure enclosure = enclosureRepository.findById(id).orElseThrow(() -> new NotFoundException("Enclosure ID: " + id));
        enclosureRepository.save(EnclosureMapper.mapToEnclosureEntity(enclosureRequestDTO, enclosure));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        id)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteEnclosureById(int id) throws NotFoundException {
        if (enclosureRepository.existsById(id)) {
            enclosureRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Enclosure ID: " + id);
    }
}
