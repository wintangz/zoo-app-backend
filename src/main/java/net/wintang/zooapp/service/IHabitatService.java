package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.HabitatRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IHabitatService {
    ResponseEntity<ResponseObject> getHabitats();

    ResponseEntity<ResponseObject> getHabitatById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteHabitatById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createHabitat(HabitatRequestDTO habitatRequestDTO);

    ResponseEntity<ResponseObject> updateHabitatById(int id, HabitatRequestDTO habitatRequestDTO) throws NotFoundException;
}
