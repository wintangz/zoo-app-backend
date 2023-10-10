package net.wintang.zooapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.wintang.zooapp.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private boolean sex;
    private LocalDateTime dateOfBirth;
    private String address;
    private String nationality;
    private String phone;
    private String email;
    private List<Role> roles;
}
