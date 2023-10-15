package net.wintang.zooapp.dto.response;

import lombok.Data;
import net.wintang.zooapp.entity.Animal;

import java.time.LocalDateTime;

@Data
public class AnimalResponseDTO {
    private int id;

    private String name;

    private boolean sex;

    private float size;

    private boolean heightLength;

    private float weight;

    private String imgUrl;

    private LocalDateTime arrivalDate;

    private LocalDateTime createdDate;

    private LocalDateTime dateOfBirth;

    private LocalDateTime dateOfDeath;

    private String origin;

    private String healthStatus;

    private boolean status;

    private String species;

    public AnimalResponseDTO(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.sex = animal.isSex();
        this.imgUrl = animal.getImgUrl();
        this.arrivalDate = animal.getArrivalDate();
        this.createdDate = animal.getCreatedDate();
        this.dateOfBirth = animal.getDateOfBirth();
        this.dateOfDeath = animal.getDateOfDeath();
        this.origin = animal.getOrigin();
        this.status = animal.isStatus();
        this.species = animal.getSpecies().getName();
    }
}
