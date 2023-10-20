package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.dto.request.VerificationRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    ResponseEntity<ResponseObject> getAllUsers();

    ResponseEntity<ResponseObject> getUserById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> getCustomers();

    ResponseEntity<ResponseObject> getStaff();

    ResponseEntity<ResponseObject> getZooTrainers();

    ResponseEntity<ResponseObject> createCustomer(UserRequestDTO userDto) throws DuplicatedKeyException;

    ResponseEntity<ResponseObject> createStaff(UserRequestDTO userDto) throws DuplicatedKeyException;

    ResponseEntity<ResponseObject> createZooTrainer(UserRequestDTO userDto) throws DuplicatedKeyException;

    ResponseEntity<ResponseObject> updateUserById(UserUpdateDTO newUser, int id) throws NotFoundException, PermissionDeniedException;

    ResponseEntity<ResponseObject> deleteUserById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> resetPassword(String newPassword, String token) throws NotFoundException;

    ResponseEntity<ResponseObject> verifyEmail(String email) throws NotFoundException;

    ResponseEntity<ResponseObject> verifyCode(VerificationRequestDTO code) throws NotFoundException;
}