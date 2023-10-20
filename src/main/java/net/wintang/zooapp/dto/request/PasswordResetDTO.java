package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDTO {

    @Size(min = 8, message = "New Password requires at least 8 characters")
    private String newPassword;
    private String token;
}
