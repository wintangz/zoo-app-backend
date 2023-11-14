package net.wintang.zooapp.dto.response;

import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalTrainerAssignor;

import java.time.LocalDateTime;

public class AnimalTrainerAssignorResponseDTO {

    private Integer id;
    private Animal animal;
    private UserResponseDTO trainer;
    private UserResponseDTO assignedBy;
    private LocalDateTime assignedDate;
    private LocalDateTime unassignedDate;

    public AnimalTrainerAssignorResponseDTO(AnimalTrainerAssignor aTa) {
        this.id = aTa.getId();
        this.animal = aTa.getAnimal();
        this.trainer = UserMapper.mapToUserDTO(aTa.getTrainer());
        this.assignedBy = UserMapper.mapToUserDTO(aTa.getAssignedBy());
        this.assignedDate = aTa.getAssignedDate();
        this.unassignedDate = aTa.getUnassignedDate();
    }
}
