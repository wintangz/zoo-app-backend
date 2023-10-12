package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Species;
import net.wintang.zooapp.dto.response.SpeciesResponseDTO;
import net.wintang.zooapp.repository.SpeciesRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesService implements ISpeciesService {

    private SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    private List<SpeciesResponseDTO> mapToDTO(List<Species> species) {
        return species.stream().map(SpeciesResponseDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> getAllSpecies() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToDTO(speciesRepository.findAll()))
        );
    }
}
