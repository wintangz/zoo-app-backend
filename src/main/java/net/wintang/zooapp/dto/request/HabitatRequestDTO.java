package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitatRequestDTO implements Serializable {

    @NotBlank(message = "Name cannot be empty")
    String name;
    @NotBlank(message = "Info cannot be empty")
    String info;
    @NotBlank(message = "Image url cannot be empty")
    String imgUrl;
    @NotBlank(message = "Banner url cannot be empty")
    String bannerUrl;
    boolean status;
}