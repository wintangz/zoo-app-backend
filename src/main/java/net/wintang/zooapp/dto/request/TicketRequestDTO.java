package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO implements Serializable {
    @NotBlank(message = "Name cannot be empty")
    String name;
    float price;
    @NotBlank
    String type;
    @NotBlank(message = "Description cannot be empty")
    String description;
    @NotBlank(message = "Image cannot be empty")
    String imgUrl;
    boolean status;
}