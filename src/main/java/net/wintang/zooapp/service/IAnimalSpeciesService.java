package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IAnimalSpeciesService {
    ResponseEntity<ResponseObject> getAllSpecies();
}
