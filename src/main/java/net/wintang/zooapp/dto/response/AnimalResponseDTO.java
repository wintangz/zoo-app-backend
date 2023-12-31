package net.wintang.zooapp.dto.response;

import lombok.Data;
import net.wintang.zooapp.dto.mapper.SpeciesMapper;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalEnclosure;

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

    private SpeciesResponseDTO species;

    private List<AnimalTrainerAssignorResponseDTO> animalTrainerAssignors;

    private AnimalEnclosure currentEnclosure;

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
        this.species = SpeciesMapper.mapToSpeciesDto(animal.getSpecies());
        this.animalTrainerAssignors = animal.getAnimalTrainerAssignors().stream().filter(a -> a.getUnassignedDate() == null).map(AnimalTrainerAssignorResponseDTO::new).toList();
        this.currentEnclosure = animal.getEnclosures().stream().filter(e -> e.getMoveOutDate() == null).findFirst().orElse(null);
    }
}
