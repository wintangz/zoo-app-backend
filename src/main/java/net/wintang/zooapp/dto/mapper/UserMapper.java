package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapToUserEntity(UserRequestDTO user) {
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

    public User mapToUserEntity(UserUpdateDTO user, User oldUser) {
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
        userBuilder.avatarUrl(user.getAvatarUrl() != null ? user.getAvatarUrl() : oldUser.getAvatarUrl());
        return userBuilder.build();
    }
}
