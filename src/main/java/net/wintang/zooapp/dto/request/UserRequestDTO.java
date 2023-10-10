package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.*;
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
public class UserRequestDTO {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Last name cannot be empty")
    private String lastname;

    @NotBlank(message = "First name cannot be empty")
    private String firstname;

    private boolean sex;

    @NotNull(message = "Date of Birth cannot be empty")
    @Past(message = "Date of Birth cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateOfBirth;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    private String email;

    @Null(message = "Roles must be null")
    private List<Role> roles;
}
