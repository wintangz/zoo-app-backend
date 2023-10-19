package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.EnclosureRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface IEnclosureService {
    ResponseEntity<ResponseObject> getEnclosures();

    ResponseEntity<ResponseObject> getEnclosureById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createEnclosure(EnclosureRequestDTO enclosureRequestDTO);

    ResponseEntity<ResponseObject> updateEnclosureById(int id, EnclosureRequestDTO enclosureRequestDTO) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteEnclosureById(int id) throws NotFoundException;
}
