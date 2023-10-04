package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.model.UserDTO;
import net.wintang.zooapp.model.UserInfoDTO;
import net.wintang.zooapp.repository.RoleRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private User mapToUserEntity(UserDTO user) {
        return User.builder()
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

    private List<UserInfoDTO> mapToInfoDTO(List<User> users) {
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


    //Staff
    @Override
    public ResponseEntity<ResponseObject> createNewStaff(UserDTO userDto) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Failed", "Username existed", userDto.getUsername())
            );
        }
        User user = mapToUserEntity(userDto);
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
        List<User> list = userRepository.findByRole(2);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToInfoDTO(list))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateStaff(UserDTO user, int id) {
        Optional<User> updateUser = userRepository.findById(id);
        if(updateUser.isPresent()) {
            User newUser = updateUser.get();

            //Check Input and Update to newUser
            if(user.getUsername() != null && !user.getUsername().isEmpty())
                newUser.setUsername(user.getUsername());
            if(user.getPassword() != null && !user.getPassword().isEmpty())
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getLastname() != null && !user.getLastname().isEmpty())
                newUser.setLastname(user.getLastname());
            if(user.getFirstname() != null && !user.getFirstname().isEmpty())
                newUser.setFirstname(user.getFirstname());
            if(user.isSex() != newUser.isSex())
                newUser.setSex(user.isSex());
            if(user.getCitizenId() != null && !user.getCitizenId().isEmpty())
                newUser.setCitizenId(user.getCitizenId());
            if(user.getEmail() != null && !user.getEmail().isEmpty())
                newUser.setEmail(user.getEmail());
            if(user.getPhone() != null && !user.getPhone().isEmpty())
                newUser.setPhone(user.getPhone());
            if(user.getAddress() != null && !user.getAddress().isEmpty())
                newUser.setAddress(user.getAddress());
            if(user.getNationality() != null && !user.getNationality().isEmpty())
                newUser.setNationality(user.getNationality());
            if(user.getDateOfBirth() != null)
                newUser.setDateOfBirth(user.getDateOfBirth());

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                    ApplicationConstants.ResponseStatusMessage.SUCCESS,
                            mapToInfoDTO(Collections.singletonList(userRepository.save(newUser))))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.FAILED,
                        ApplicationConstants.ResponseStatusMessage.NOT_MODIFIED,
                        null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteStaff(int id) {
        return null;
    }


    //Zoo-trainer
    @Override
    public ResponseEntity<ResponseObject> createNewTrainer(UserDTO userDto) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Failed", "Username existed", userDto.getUsername())
            );
        }
        User user = mapToUserEntity(userDto);
        Optional<Role> role = roleRepository.findByName("ZOO_TRAINER");
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
    public ResponseEntity<ResponseObject> findAllTrainer() {
        List<User> list = userRepository.findByRole(3);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToInfoDTO(list))
        );
    }
}
