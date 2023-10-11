package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.dto.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    ResponseEntity<ResponseObject> getAllUsers();

    ResponseEntity<ResponseObject> getUserById(int id);

    ResponseEntity<ResponseObject> getCustomers();

    ResponseEntity<ResponseObject> getStaff();

    ResponseEntity<ResponseObject> getZooTrainers();

    ResponseEntity<ResponseObject> createCustomer(UserRequestDTO userDto);

    ResponseEntity<ResponseObject> createStaff(UserRequestDTO userDto);

    ResponseEntity<ResponseObject> createZooTrainer(UserRequestDTO userDto);

    ResponseEntity<ResponseObject> updateUser(UserUpdateDTO userDto, int id);

    ResponseEntity<ResponseObject> deleteUserById(int id);

}