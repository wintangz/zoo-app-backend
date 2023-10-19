package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnclosureRequestDTO implements Serializable {

    @NotBlank(message = "Name cannot be empty")
    String name;
    @NotBlank(message = "Info cannot be empty")
    String info;
    @Min(message = "Max Capacity cannot be negative or zero", value = 0)
    int maxCapacity;
    List<Integer> speciesListIds;
    @NotBlank(message = "Image url cannot be empty")
    String imgUrl;
    int habitatId;
    boolean status;
}