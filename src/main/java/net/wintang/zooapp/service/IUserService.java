package net.wintang.zooapp.service;

import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    ResponseEntity<ResponseObject> findAllUsers();
    ResponseEntity<ResponseObject> createNewStaff(UserDTO user);
    ResponseEntity<ResponseObject> findAllStaff();
    ResponseEntity<ResponseObject> findAllTrainer();
    ResponseEntity<ResponseObject> createNewTrainer(UserDTO user);
    ResponseEntity<ResponseObject> updateStaff(UserDTO userDto, int id);
    ResponseEntity<ResponseObject> deleteStaff(int id);
}