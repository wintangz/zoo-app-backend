package net.wintang.zooapp.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    private List<UserResponseDTO> mapToResponseDTO(List<User> users) {
        return users.stream().map(UserResponseDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> getUsersByRoles(String role) {
        role = role.toUpperCase().replace("-", "_");
        Role roleObject = roleRepository.findByName(role).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToResponseDTO(userRepository.findByRole(roleObject.getId())))
        );
    }
}
