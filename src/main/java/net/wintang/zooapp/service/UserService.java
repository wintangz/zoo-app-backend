package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.response.UserResponseDTO;
import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;
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

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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

    private Role getRoles(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        return role.orElse(null);
    }

    @Override
    public ResponseEntity<ResponseObject> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findUsersByRole(String role) {
        role = role.replace("-", "_");
        int roleId = roleRepository.findByName(role).map(Role::getId).orElse(0);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findByRole(roleId)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createUserByRole(UserRequestDTO userDto, String role) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDto.getUsername()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            ApplicationConstants.ResponseMessage.EXISTED,
                            userDto.getUsername())
            );
        }
        Role roleObject = roleRepository.findByName(role.replace("-", "_").toUpperCase()).orElse(new Role());
        userDto.setRoles(Collections.singletonList(roleObject));
        User user = mapToUserEntity(userDto);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, user)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateStaff(UserRequestDTO user, int id) {
        Optional<User> updateUser = userRepository.findById(id);
        if (updateUser.isPresent()) {
            User newUser = updateUser.get();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setLastname(user.getLastname());
            newUser.setFirstname(user.getFirstname());
            newUser.setSex(user.isSex());
            newUser.setEmail(user.getEmail());
            newUser.setPhone(user.getPhone());
            newUser.setAddress(user.getAddress());
            newUser.setNationality(user.getNationality());
            newUser.setDateOfBirth(user.getDateOfBirth());

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            mapToResponseDTO(Collections.singletonList(userRepository.save(newUser))))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                        null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteUserByRoleAndId(String role, int id) {

        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElse(new User());
            if (user.getRoles().stream().anyMatch(r -> role.toUpperCase().replace("-", "_").equals(r.getName()))) {
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                                ApplicationConstants.ResponseMessage.SUCCESS,
                                id)
                );
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                        ApplicationConstants.ResponseMessage.NOT_MODIFIED,
                        null)
        );
    }
}
