package net.wintang.zooapp.model;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;
import net.wintang.zooapp.entity.AnimalSpecies;

@Data
public class AnimalSpeciesDTO {

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

    public AnimalSpeciesDTO(AnimalSpecies animalSpecies) {
        this.name = animalSpecies.getName();
        this.species = animalSpecies.getSpecies();
        this.genus = animalSpecies.getGenus();
        this.family = animalSpecies.getFamily();
        this.habitat = animalSpecies.getHabitat();
        this.diet = animalSpecies.getDiet();
        this.conversationStatus = animalSpecies.getConversationStatus();
        this.description = animalSpecies.getDescription();
        this.imgUrl = animalSpecies.getImgUrl();
        this.avatarUrl = animalSpecies.getAvatarUrl();
    }
}
