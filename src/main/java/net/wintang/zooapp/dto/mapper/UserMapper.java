package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.dto.response.UserResponseDTO;
import net.wintang.zooapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static List<UserResponseDTO> mapToUserDTO(List<User> users) {
        return users.stream().map(UserResponseDTO::new).toList();
    }

    public static UserResponseDTO mapToUserDTO(User user) {
        return new UserResponseDTO(user);
    }

    public User mapToUserEntity(UserRequestDTO user) {
        return User.builder()
                .username(user.getUsername().trim())
                .password(passwordEncoder.encode(user.getPassword()))
                .lastname(user.getLastname().trim())
                .firstname(user.getFirstname().trim())
                .sex(user.isSex())
                .email(user.getEmail().trim())
                .phone(user.getPhone().trim())
                .address(user.getAddress().trim())
                .nationality(user.getNationality().trim())
                .dateOfBirth(user.getDateOfBirth())
                .roles(user.getRoles())
                .status(true)
                .build();
    }

    public User mapToUserEntity(UserUpdateDTO user, User oldUser) {
        User.UserBuilder userBuilder = User.builder();
        userBuilder.username(user.getUsername() != null ? user.getUsername().trim() : oldUser.getUsername());
        userBuilder.password(user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : oldUser.getPassword());
        userBuilder.lastname(user.getLastname() != null ? user.getLastname().trim() : oldUser.getLastname());
        userBuilder.firstname(user.getFirstname() != null ? user.getFirstname().trim() : oldUser.getFirstname());
        userBuilder.sex(user.getSex() != null ? Boolean.parseBoolean(user.getSex()) : oldUser.isSex());
        userBuilder.email(user.getEmail() != null ? user.getEmail().trim() : oldUser.getEmail());
        userBuilder.phone(user.getPhone() != null ? user.getPhone().trim() : oldUser.getPhone());
        userBuilder.address(user.getAddress() != null ? user.getAddress().trim() : oldUser.getAddress());
        userBuilder.nationality(user.getNationality() != null ? user.getNationality().trim() : oldUser.getNationality());
        userBuilder.dateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth() : oldUser.getDateOfBirth());
        userBuilder.roles(new ArrayList<>(user.getRoles() != null ? user.getRoles() : oldUser.getRoles()));
        userBuilder.avatarUrl(user.getAvatarUrl() != null ? user.getAvatarUrl() : oldUser.getAvatarUrl());
        userBuilder.status(user.isStatus());
        return userBuilder.build();
    }

    public User encodePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }
}
