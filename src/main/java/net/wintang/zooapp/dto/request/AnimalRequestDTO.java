package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private boolean sex;

    @Min(value = 0, message = "Please provide size")
    private float size;

    private boolean heightLength;

    @Min(value = 0, message = "Please provide weight")
    private float weight;

    @NotBlank(message = "ImgUrl cannot be empty")
    private String imgUrl;

    private LocalDateTime arrivalDate;

    private LocalDateTime createdDate;

    private LocalDateTime dateOfBirth;

    @NotBlank(message = "Origin cannot be empty")
    private String origin;

    @NotBlank(message = "Health Status cannot be empty")
    private String healthStatus;

    @NotBlank(message = "Species cannot be empty")
    private String species;

    @Min(value = 1, message = "Please provide Id")
    private int createdBy;
}
