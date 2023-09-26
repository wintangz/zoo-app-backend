package net.wintang.zooapp.service;

import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.UserEntity;
import net.wintang.zooapp.model.UserDTO;
import net.wintang.zooapp.model.UserInfoDTO;
import net.wintang.zooapp.repository.RoleRepository;
import net.wintang.zooapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private UserEntity mapToUserEntity(UserDTO user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .sex(user.isSex())
                .citizenId(user.getCitizenId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .nationality(user.getNationality())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    private List<UserInfoDTO> mapToInfoDTO(List<UserEntity> users) {
        return users.stream().map(UserInfoDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToInfoDTO(userRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createNewStaff(UserDTO userDto) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Failed", "Username existed", userDto.getUsername())
            );
        }
        UserEntity user = mapToUserEntity(userDto);
        Optional<Role> role = roleRepository.findByName("STAFF");
        if(role.isPresent()) {
            Role roles = role.get();
            user.setRoles(Collections.singletonList(roles));
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                            ApplicationConstants.ResponseStatusMessage.SUCCESS, userDto)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAllStaff() {
        List<UserEntity> list = userRepository.findByRole(2);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToInfoDTO(list))
        );
    }
}
