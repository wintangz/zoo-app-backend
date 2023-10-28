package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.AnimalRequestDTO;
import net.wintang.zooapp.dto.request.AnimalUpdateDTO;
import net.wintang.zooapp.dto.response.AnimalEnclosureResponseDTO;
import net.wintang.zooapp.dto.response.AnimalResponseDTO;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalEnclosure;
import net.wintang.zooapp.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimalMapper {

    private final SpeciesRepository speciesRepository;

    @Autowired
    public AnimalMapper(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public static List<AnimalResponseDTO> mapToAnimalDTO(List<Animal> animals) {
        return animals.stream().map(AnimalResponseDTO::new).toList();
    }

    public static AnimalResponseDTO mapToAnimalDTO(Animal animal) {
        return new AnimalResponseDTO(animal);
    }

    public Animal mapToAnimalEntity(AnimalRequestDTO dto) {
        return Animal.builder()
                .name(dto.getName())
                .sex(dto.isSex())
                .arrivalDate(dto.getArrivalDate())
                .dateOfBirth(dto.getDateOfBirth())
                .origin(dto.getOrigin())
                .species(speciesRepository.findByName(dto.getSpecies()))
                .imgUrl(dto.getImgUrl())
                .status(dto.isStatus())
                .build();
    }

    public Animal mapToAnimalEntity(AnimalUpdateDTO dto, Animal old) {
        old.setName(dto.getName());
        old.setSex(dto.isSex());
        old.setImgUrl(dto.getImgUrl());
        old.setArrivalDate(dto.getArrivalDate());
        old.setDateOfBirth(dto.getDateOfBirth());
        old.setDateOfDeath(dto.getDateOfDeath());
        old.setOrigin(dto.getOrigin());
        old.setSpecies(speciesRepository.findByName(dto.getSpecies()));
        old.setStatus(dto.isStatus());
        return old;
    }

    public static List<AnimalEnclosureResponseDTO> mapToAEDto(List<AnimalEnclosure> animalEnclosure) {
        return animalEnclosure.stream().map(AnimalEnclosureResponseDTO::new).toList();
    }

    public static AnimalEnclosureResponseDTO mapToAEDto(AnimalEnclosure animalEnclosure) {
        return new AnimalEnclosureResponseDTO(animalEnclosure);
    }
}
