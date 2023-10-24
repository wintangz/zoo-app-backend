package net.wintang.zooapp.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.wintang.zooapp.entity.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateDTO {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String lastname;

    private String firstname;

    private String sex;

    @Past(message = "Date of Birth cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateOfBirth;

    private String address;

    private String nationality;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number format")
    private String phone;

    @Email(message = "Invalid email")
    private String email;

    private String avatarUrl;

    private List<Role> roles;

    private boolean status;
}
