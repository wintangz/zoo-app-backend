package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.response.EnclosureResponseDTO;
import net.wintang.zooapp.entity.Enclosure;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnclosureMapper {

    public static List<EnclosureResponseDTO> mapToEnclosureDto(List<Enclosure> enclosures) {
        return enclosures.stream().map(EnclosureResponseDTO::new).toList();
    }
}
