package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    ResponseEntity<ResponseObject> findAllUsers();

    ResponseEntity<ResponseObject> createUser(UserRequestDTO user);

    ResponseEntity<ResponseObject> findUserById(int id);

    ResponseEntity<ResponseObject> updateUser(UserUpdateDTO userDto, int id);

    ResponseEntity<ResponseObject> deleteUserById(int id);

}