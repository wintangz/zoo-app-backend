package net.wintang.zooapp.model;

import lombok.Data;
import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoDTO {
    private int id;
    private String username;
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

    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.lastname = user.getLastname();
        this.firstname = user.getFirstname();
        this.sex = user.isSex();
        this.citizenId = user.getCitizenId();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
        this.nationality = user.getNationality();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
