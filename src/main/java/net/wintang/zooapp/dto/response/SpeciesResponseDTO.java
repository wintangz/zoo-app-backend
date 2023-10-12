package net.wintang.zooapp.dto.response;

import lombok.Data;
import net.wintang.zooapp.entity.Species;

import java.time.LocalDateTime;

@Data
public class SpeciesResponseDTO {

    private int id;

    private String name;

    private String species;

    private String genus;

    private String family;

    private String habitat;

    private String diet;

    private String conversationStatus;

    private String description;

    private String imgUrl;

    private String avatarUrl;

    private LocalDateTime createdDate;

    private boolean status;

    public SpeciesResponseDTO(Species species) {
        this.id = species.getId();
        this.name = species.getName();
        this.species = species.getSpecies();
        this.genus = species.getGenus();
        this.family = species.getFamily();
        this.diet = species.getDiet();
        this.conversationStatus = species.getConversationStatus();
        this.description = species.getDescription();
        this.imgUrl = species.getImgUrl();
        this.avatarUrl = species.getAvatarUrl();
        this.habitat = species.getHabitat().getName();
        this.createdDate = species.getCreatedDate();
        this.status = species.isStatus();
    }
}
