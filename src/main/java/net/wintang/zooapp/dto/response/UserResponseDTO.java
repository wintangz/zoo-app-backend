package net.wintang.zooapp.dto.response;

import lombok.Data;
import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponseDTO {
    private int id;
    private String username;
    private String lastname;
    private String firstname;
    private boolean sex;
    private LocalDateTime dateOfBirth;
    private String address;
    private String nationality;
    private String phone;
    private String email;
    private LocalDateTime createdDate;
    private boolean status;
    private List<Role> roles;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.lastname = user.getLastname();
        this.firstname = user.getFirstname();
        this.sex = user.isSex();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
        this.nationality = user.getNationality();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
        this.status = user.isStatus();
        this.roles = user.getRoles();
    }
}
