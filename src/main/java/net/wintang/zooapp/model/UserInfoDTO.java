package net.wintang.zooapp.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.UserEntity;

import java.util.Date;

@Data
public class UserInfoDTO {
    private int id;
    private String username;
    private String lastname;
    private String firstname;
    private boolean sex;
    private String citizenId;
    private Date dateOfBirth;
    private String address;
    private String nationality;
    private String phone;
    private String email;

    public UserInfoDTO(UserEntity user) {
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
    }
}
