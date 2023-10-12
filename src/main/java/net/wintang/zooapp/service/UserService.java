package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.dto.response.UserResponseDTO;
import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.repository.RoleRepository;
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.security.JwtGenerator;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final JwtGenerator jwtGenerator;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
    }

    private List<UserResponseDTO> mapToResponseDTO(List<User> users) {
        return users.stream().map(UserResponseDTO::new).toList();
    }

    private User mapToUserEntity(UserRequestDTO user) {
        return User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .sex(user.isSex())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .nationality(user.getNationality())
                .dateOfBirth(user.getDateOfBirth())
                .roles(user.getRoles())
                .build();
    }

    private User mapToUserEntity(UserUpdateDTO user, User oldUser) {
        User.UserBuilder userBuilder = User.builder();
        userBuilder.username(user.getUsername() != null ? user.getUsername() : oldUser.getUsername());
        userBuilder.password(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : oldUser.getPassword());
        userBuilder.lastname(user.getLastname() != null ? user.getLastname() : oldUser.getLastname());
        userBuilder.firstname(user.getFirstname() != null ? user.getFirstname() : oldUser.getFirstname());
        userBuilder.sex(user.getSex() != null ? Boolean.parseBoolean(user.getSex()) : oldUser.isSex());
        userBuilder.email(user.getEmail() != null ? user.getEmail() : oldUser.getEmail());
        userBuilder.phone(user.getPhone() != null ? user.getPhone() : oldUser.getPhone());
        userBuilder.address(user.getAddress() != null ? user.getAddress() : oldUser.getAddress());
        userBuilder.nationality(user.getNationality() != null ? user.getNationality() : oldUser.getNationality());
        userBuilder.dateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth() : oldUser.getDateOfBirth());
        userBuilder.roles(new ArrayList<>(user.getRoles() != null ? user.getRoles() : oldUser.getRoles()));
        return userBuilder.build();
    }

    private Role getRoles(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        return role.orElse(null);
    }

    @Override
    public ResponseEntity<ResponseObject> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getUserById(int id) {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                            null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(Collections.singletonList(user)).get(0))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findByRole(4)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getStaff() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findByRole(2)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getZooTrainers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findByRole(3)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createCustomer(UserRequestDTO userDto) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.EXISTED,
                            userDto.getUsername())
            );
        }
        User user = mapToUserEntity(userDto);
        user.setRoles(Collections.singletonList(new Role(4, "CUSTOMER")));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, user)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createStaff(UserRequestDTO userDto) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.EXISTED,
                            userDto.getUsername())
            );
        }
        User user = mapToUserEntity(userDto);
        user.setRoles(Collections.singletonList(new Role(2, "STAFF")));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, user)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createZooTrainer(UserRequestDTO userDto) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.EXISTED,
                            userDto.getUsername())
            );
        }
        User user = mapToUserEntity(userDto);
        user.setRoles(Collections.singletonList(new Role(3, "ZOO_TRAINER")));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, user)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateUser(UserUpdateDTO newUser, int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            User updatedUser = mapToUserEntity(newUser, existingUser);
            updatedUser.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            mapToResponseDTO(Collections.singletonList(userRepository.save(updatedUser))))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                        id)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteUserById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                        id)
        );
    }
}
