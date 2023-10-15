package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.response.HabitatResponseDTO;
import net.wintang.zooapp.entity.Habitat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitatMapper {

    public List<HabitatResponseDTO> mapToHabitatDTO(List<Habitat> habitats) {
        return habitats.stream().map(HabitatResponseDTO::new).toList();
    }
}