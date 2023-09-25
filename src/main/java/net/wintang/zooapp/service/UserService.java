package net.wintang.zooapp.service;

import net.wintang.zooapp.ResponseObject;
import net.wintang.zooapp.entity.UserEntity;
import net.wintang.zooapp.model.UserDTO;
import net.wintang.zooapp.model.UserInfoDTO;
import net.wintang.zooapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private List<UserInfoDTO> mapToInfoDTO(List<UserEntity> users) {
        return users.stream().map(UserInfoDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok", "Success", mapToInfoDTO(userRepository.findAll()))
        );
    }
}
