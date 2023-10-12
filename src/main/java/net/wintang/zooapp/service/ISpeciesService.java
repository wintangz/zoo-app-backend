package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ISpeciesService {
    ResponseEntity<ResponseObject> getAllSpecies();
}
