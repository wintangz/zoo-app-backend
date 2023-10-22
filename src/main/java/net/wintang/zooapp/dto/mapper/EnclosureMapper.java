package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.EnclosureRequestDTO;
import net.wintang.zooapp.dto.response.EnclosureResponseDTO;
import net.wintang.zooapp.entity.Enclosure;
import net.wintang.zooapp.entity.Habitat;
import net.wintang.zooapp.entity.Species;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnclosureMapper {

    public static List<EnclosureResponseDTO> mapToEnclosureDto(List<Enclosure> enclosures) {
        return enclosures.stream().map(EnclosureResponseDTO::new).toList();
    }

    public static Enclosure mapToEnclosureEntity(EnclosureRequestDTO enclosureRequestDTO) {
        return Enclosure.builder()
                .name(enclosureRequestDTO.getName())
                .info(enclosureRequestDTO.getInfo())
                .imgUrl(enclosureRequestDTO.getImgUrl())
                .status(enclosureRequestDTO.isStatus())
                .maxCapacity(enclosureRequestDTO.getMaxCapacity())
                .habitat(Habitat.builder().id(enclosureRequestDTO.getHabitatId()).build())
                .build();
    }

    public static Enclosure mapToEnclosureEntity(EnclosureRequestDTO enclosureRequestDTO, Enclosure enclosure) {
        return Enclosure.builder()
                .id(enclosure.getId())
                .name(enclosureRequestDTO.getName().isBlank() ? enclosure.getName() : enclosureRequestDTO.getName())
                .info(enclosureRequestDTO.getInfo().isBlank() ? enclosure.getInfo() : enclosureRequestDTO.getInfo())
                .imgUrl(enclosureRequestDTO.getImgUrl().isBlank() ? enclosure.getImgUrl() : enclosureRequestDTO.getImgUrl())
                .maxCapacity(enclosureRequestDTO.getMaxCapacity() == 0 ? enclosure.getMaxCapacity() : enclosureRequestDTO.getMaxCapacity())
                .status(enclosureRequestDTO.isStatus() == enclosure.isStatus() ? enclosure.isStatus() : enclosureRequestDTO.isStatus())
                .habitat(enclosureRequestDTO.getHabitatId() == 0 ? enclosure.getHabitat() : Habitat.builder().id(enclosureRequestDTO.getHabitatId()).build())
                .build();
    }
}
