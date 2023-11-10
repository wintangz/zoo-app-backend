package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.SpeciesRequestDTO;
import net.wintang.zooapp.dto.response.SpeciesResponseDTO;
import net.wintang.zooapp.entity.Habitat;
import net.wintang.zooapp.entity.Species;

import java.util.List;

public class SpeciesMapper {

    public static List<SpeciesResponseDTO> mapToSpeciesDto(List<Species> species) {
        return species.stream().map(SpeciesResponseDTO::new).toList();
    }

    public static SpeciesResponseDTO mapToSpeciesDto(Species species) {
        return new SpeciesResponseDTO(species);
    }

    public static Species mapToSpeciesEntity(SpeciesRequestDTO speciesRequestDTO) {
        return Species.builder()
                .name(speciesRequestDTO.getName().trim())
                .species(speciesRequestDTO.getSpecies().trim())
                .diet(speciesRequestDTO.getDiet().trim())
                .family(speciesRequestDTO.getFamily().trim())
                .genus(speciesRequestDTO.getGenus().trim())
                .avatarUrl(speciesRequestDTO.getAvatarUrl())
                .habitat(Habitat.builder().id(speciesRequestDTO.getHabitatId()).build())
                .imgUrl(speciesRequestDTO.getImgUrl())
                .conversationStatus(speciesRequestDTO.getConversationStatus().trim())
                .status(speciesRequestDTO.isStatus())
                .description(speciesRequestDTO.getDescription().trim())
                .build();
    }

    public static Species mapToSpeciesEntity(SpeciesRequestDTO speciesRequestDTO, Species species) {
        return Species.builder()
                .id(species.getId())
                .name(speciesRequestDTO.getName().trim())
                .species(speciesRequestDTO.getSpecies().trim())
                .diet(speciesRequestDTO.getDiet().trim())
                .family(speciesRequestDTO.getFamily().trim())
                .genus(speciesRequestDTO.getGenus().trim())
                .avatarUrl(speciesRequestDTO.getAvatarUrl())
                .habitat(Habitat.builder().id(speciesRequestDTO.getHabitatId()).build())
                .imgUrl(speciesRequestDTO.getImgUrl().trim())
                .conversationStatus(speciesRequestDTO.getConversationStatus().trim())
                .status(speciesRequestDTO.isStatus() == species.isStatus() ? species.isStatus() : speciesRequestDTO.isStatus())
                .description(speciesRequestDTO.getDescription().trim())
                .build();
    }
}
