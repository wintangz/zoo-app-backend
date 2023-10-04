package net.wintang.zooapp.model;

import lombok.Data;
import net.wintang.zooapp.entity.Role;

import java.util.Optional;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private Optional<Role> role;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken,Optional<Role> role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}
