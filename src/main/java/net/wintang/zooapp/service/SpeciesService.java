package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.SpeciesMapper;
import net.wintang.zooapp.dto.request.SpeciesRequestDTO;
import net.wintang.zooapp.entity.Species;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.SpeciesRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SpeciesService implements ISpeciesService {

    private SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getSpecies() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        SpeciesMapper.mapToSpeciesDto(speciesRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getSpeciesById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        SpeciesMapper.mapToSpeciesDto(speciesRepository.findById(id).orElseThrow(() -> new NotFoundException("Species ID: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createSpecies(SpeciesRequestDTO speciesRequestDTO) throws DuplicatedKeyException {
        if (Boolean.FALSE.equals(speciesRepository.existsBySpecies(speciesRequestDTO.getSpecies()))) {
            Species species = SpeciesMapper.mapToSpeciesEntity(speciesRequestDTO);
            speciesRepository.save(species);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            speciesRequestDTO)
            );
        }
        throw new DuplicatedKeyException("Species: " + speciesRequestDTO.getSpecies());
    }

    @Override
    public ResponseEntity<ResponseObject> updateSpeciesById(int id, SpeciesRequestDTO speciesRequestDTO) throws NotFoundException {
        Species species = speciesRepository.findById(id).orElseThrow(() -> new NotFoundException("Species ID: " + id));
        speciesRepository.save(SpeciesMapper.mapToSpeciesEntity(speciesRequestDTO, species));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        id)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteSpeciesById(int id) throws NotFoundException {
        if (speciesRepository.existsById(id)) {
            try {
                speciesRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id)
                );
            } catch (DataAccessException ex) {
                Species species = speciesRepository.findById(id).get();
                species.setStatus(false);
                speciesRepository.save(species);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id)
                );
            }
        }
        throw new NotFoundException("Species ID: " + id);
    }
}
