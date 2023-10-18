package net.wintang.zooapp.dto.request;

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
public class AnimalDietRequestDto implements Serializable {

    @NotBlank(message = "Type cannot be null")
    private String type;
    private List<Integer> foodListIds;
}