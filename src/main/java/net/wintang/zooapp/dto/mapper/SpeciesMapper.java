package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.response.SpeciesResponseDTO;
import net.wintang.zooapp.entity.Species;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpeciesMapper {

    public static List<SpeciesResponseDTO> mapToSpeciesDTO(List<Species> species) {
        return species.stream().map(SpeciesResponseDTO::new).toList();
    }
}
