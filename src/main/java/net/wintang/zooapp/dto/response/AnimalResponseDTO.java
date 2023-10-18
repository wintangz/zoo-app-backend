package net.wintang.zooapp.dto.response;

import lombok.Data;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnimalResponseDTO {
    private int id;

    private String name;

    private boolean sex;

    private String imgUrl;

    private LocalDateTime arrivalDate;

    private LocalDateTime createdDate;

    private LocalDateTime dateOfBirth;

    private LocalDateTime dateOfDeath;

    private String origin;

    private boolean status;

    private String species;

    private List<UserResponseDTO> trainers;

    private List<UserResponseDTO> assignors;

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
        this.trainers = UserMapper.mapToUserDTO(animal.getZooTrainer());
        this.assignors = UserMapper.mapToUserDTO(animal.getAssignor());
    }
}
