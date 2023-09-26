package net.wintang.zooapp.service;

import net.wintang.zooapp.ResponseObject;
import net.wintang.zooapp.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    ResponseEntity<ResponseObject> findAllUsers();
    ResponseEntity<ResponseObject> createNewStaff(UserDTO user);
    ResponseEntity<ResponseObject> findAllStaff();
}
