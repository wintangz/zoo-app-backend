package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.AnimalSpecies;
import net.wintang.zooapp.dto.AnimalSpeciesDTO;
import net.wintang.zooapp.repository.AnimalSpeciesRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalSpeciesService implements IAnimalSpeciesService {

    private AnimalSpeciesRepository animalSpeciesRepository;

    @Autowired
    public AnimalSpeciesService(AnimalSpeciesRepository animalSpeciesRepository) {
        this.animalSpeciesRepository = animalSpeciesRepository;
    }

    private List<AnimalSpeciesDTO> mapToDTO(List<AnimalSpecies> animalSpecies) {
        return animalSpecies.stream().map(AnimalSpeciesDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> getAllSpecies() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToDTO(animalSpeciesRepository.findAll()))
        );
    }
}
