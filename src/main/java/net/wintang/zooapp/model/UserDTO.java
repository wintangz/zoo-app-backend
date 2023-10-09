package net.wintang.zooapp.model;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Username cannot be empty.")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
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
    private List<Role> roles;
}
