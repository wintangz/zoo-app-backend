package net.wintang.zooapp.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalEnclosure;
import net.wintang.zooapp.entity.Enclosure;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalEnclosureResponseDTO implements Serializable {
    private int id;
    @NotNull
    private Animal animal;
    @NotNull
    private Enclosure enclosure;
    private LocalDateTime moveInDate;
    private LocalDateTime moveOutDate;

    public AnimalEnclosureResponseDTO(AnimalEnclosure animalEnclosure) {
        this.id = animalEnclosure.getId();
        this.animal = animalEnclosure.getAnimal();
        this.enclosure = animalEnclosure.getEnclosure();
        this.moveInDate = animalEnclosure.getMoveInDate();
        this.moveOutDate = animalEnclosure.getMoveOutDate();
    }
}