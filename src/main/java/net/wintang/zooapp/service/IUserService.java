package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    ResponseEntity<ResponseObject> findAllUsers();
    ResponseEntity<ResponseObject> createStaff(UserRequestDTO user);
    ResponseEntity<ResponseObject> findAllStaff();
    ResponseEntity<ResponseObject> findAllTrainer();
    ResponseEntity<ResponseObject> createZooTrainer(UserRequestDTO user);
    ResponseEntity<ResponseObject> updateStaff(UserDTO userDto, int id);
    ResponseEntity<ResponseObject> deleteStaff(int id);
}