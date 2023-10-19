package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequestDTO implements Serializable {
    @NotBlank(message = "Name cannot be empty")
    String name;
    @NotBlank(message = "Type cannot be empty")
    String type;
}