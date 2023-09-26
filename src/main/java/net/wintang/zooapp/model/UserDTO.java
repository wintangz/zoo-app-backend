package net.wintang.zooapp.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private boolean sex;
    private String citizenId;
    private LocalDateTime dateOfBirth;
    private String address;
    private String nationality;
    private String phone;
    private String email;
}
