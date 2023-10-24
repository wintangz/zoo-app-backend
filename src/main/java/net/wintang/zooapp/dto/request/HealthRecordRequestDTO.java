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
public class HealthRecordRequestDTO implements Serializable {
    private float weight;
    private float height;
    private float length;
    private float temperature;
    @NotBlank(message = "Life stage cannot be empty")
    private String lifeStage;
    @NotBlank(message = "Diagnosis cannot be empty")
    private String diagnosis;
    @NotBlank
    private String imgUrl;
    private int animalId;
}