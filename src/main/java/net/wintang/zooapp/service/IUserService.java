package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    ResponseEntity<ResponseObject> findAllUsers();

    ResponseEntity<ResponseObject> findUsersByRole(String role);

    ResponseEntity<ResponseObject> createUserByRole(UserRequestDTO user, String role);

    ResponseEntity<ResponseObject> updateStaff(UserRequestDTO userDto, int id);

    ResponseEntity<ResponseObject> deleteUserByRoleAndId(String role, int id);
}