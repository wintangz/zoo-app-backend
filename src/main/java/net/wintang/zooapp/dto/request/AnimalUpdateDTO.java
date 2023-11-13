package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private boolean sex;

    @NotBlank(message = "ImgUrl cannot be empty")
    private String imgUrl;

    private LocalDateTime arrivalDate;

    private LocalDateTime dateOfBirth;

    private LocalDateTime dateOfDeath;

    @NotBlank(message = "Origin cannot be empty")
    private String origin;

    private int species;

    private boolean status;
}
