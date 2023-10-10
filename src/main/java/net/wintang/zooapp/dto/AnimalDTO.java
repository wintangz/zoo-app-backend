package net.wintang.zooapp.dto;

import lombok.Data;
import net.wintang.zooapp.entity.Animal;

import java.time.LocalDateTime;

@Data
public class AnimalDTO {
    private int id;

    private String name;

    private boolean sex;

    private float size;

    private boolean heightLength;

    private float weight;

    private String imgUrl;

    private LocalDateTime arrivalDate;

    private LocalDateTime dateOfBirth;

    private LocalDateTime dateOfDeath;

    private String origin;

    private String healthStatus;

    public AnimalDTO(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.sex = animal.isSex();
        this.size = animal.getSize();
        this.heightLength = animal.isHeightLength();
        this.weight = animal.getWeight();
        this.imgUrl = animal.getImgUrl();
        this.arrivalDate = animal.getArrivalDate();
        this.dateOfBirth = animal.getDateOfBirth();
        this.dateOfDeath = animal.getDateOfDeath();
        this.origin = animal.getOrigin();
        this.healthStatus = animal.getHealthStatus();
    }
}
