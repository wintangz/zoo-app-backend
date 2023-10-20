package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesRequestDTO implements Serializable {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Species cannot be empty")
    private String species;
    @NotBlank(message = "Genus cannot be empty")
    private String genus;
    @NotBlank(message = "Family cannot be empty")
    private String family;
    @NotBlank(message = "Diet cannot be empty")
    private String diet;
    @NotBlank(message = "Conversation status cannot be empty")
    private String conversationStatus;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "Image url cannot be empty")
    private String imgUrl;
    @NotBlank(message = "Avatar url cannot be empty")
    private String avatarUrl;
    private int habitatId;
    private boolean status;
}