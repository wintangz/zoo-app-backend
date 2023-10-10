package net.wintang.zooapp.service;

import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IHabitatService {
    ResponseEntity<ResponseObject> getHabitats();
}
