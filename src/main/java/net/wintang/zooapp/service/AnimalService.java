package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.dto.response.AnimalResponseDTO;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.repository.AnimalRepository;
import net.wintang.zooapp.repository.SpeciesRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService implements IAnimalService {

    private final AnimalRepository animalRepository;

    private final SpeciesRepository speciesRepository;

    private final UserRepository userRepository;

    public AnimalService(AnimalRepository animalRepository, SpeciesRepository speciesRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.speciesRepository = speciesRepository;
        this.userRepository = userRepository;
    }

    private Object mapToDTO(List<Animal> animals) {
        return animals.stream().map(AnimalResponseDTO::new).toList();
    }

    private Animal mapToEntity(AnimalRequestDTO dto) {
        return Animal.builder()
                .id(dto.getCreatedBy())
                .name(dto.getName())
                .sex(dto.isSex())
                .heightLength(dto.isHeightLength())
                .size(dto.getSize())
                .weight(dto.getWeight())
                .arrivalDate(dto.getArrivalDate())
                .dateOfBirth(dto.getDateOfBirth())
                .origin(dto.getOrigin())
                .healthStatus(dto.getHealthStatus())
                .species(speciesRepository.findByName(dto.getSpecies()))
                .imgUrl(dto.getImgUrl())
                .createdBy(userRepository.findById(dto.getCreatedBy()).orElseThrow())
                .build();
    }

    @Override
    public ResponseEntity<ResponseObject> getAllAnimals() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToDTO(animalRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createAnimal(AnimalRequestDTO animalDto) {
        animalRepository.save(mapToEntity(animalDto));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        animalDto)
        );
    }
}
