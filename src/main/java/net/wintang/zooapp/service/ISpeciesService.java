package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.SpeciesRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface ISpeciesService {
    ResponseEntity<ResponseObject> getSpecies();

    ResponseEntity<ResponseObject> getSpeciesById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createSpecies(SpeciesRequestDTO speciesRequestDTO) throws DuplicatedKeyException;

    ResponseEntity<ResponseObject> updateSpeciesById(int id, SpeciesRequestDTO speciesRequestDTO) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteSpeciesById(int id) throws NotFoundException;
}
