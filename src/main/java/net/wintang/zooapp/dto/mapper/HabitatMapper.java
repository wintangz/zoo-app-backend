package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.HabitatRequestDTO;
import net.wintang.zooapp.dto.response.HabitatResponseDTO;
import net.wintang.zooapp.entity.Habitat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitatMapper {

    public static List<HabitatResponseDTO> mapToHabitatDTO(List<Habitat> habitats) {
        return habitats.stream().map(HabitatResponseDTO::new).toList();
    }

    public static HabitatResponseDTO mapToHabitatDTO(Habitat habitat) {
        return new HabitatResponseDTO(habitat);
    }

    public static Habitat mapToHabitatEntity(HabitatRequestDTO habitatRequestDTO) {
        return Habitat.builder()
                .name(habitatRequestDTO.getName())
                .info(habitatRequestDTO.getInfo())
                .status(habitatRequestDTO.isStatus())
                .imgUrl(habitatRequestDTO.getImgUrl())
                .bannerUrl(habitatRequestDTO.getBannerUrl())
                .build();
    }

    public static Habitat mapToHabitatEntity(HabitatRequestDTO habitatRequestDTO, Habitat habitat) {
        return Habitat.builder()
                .id(habitat.getId())
                .name(habitatRequestDTO.getName())
                .info(habitatRequestDTO.getInfo())
                .status(habitatRequestDTO.isStatus() == habitat.isStatus() ? habitat.isStatus() : habitatRequestDTO.isStatus())
                .imgUrl(habitatRequestDTO.getImgUrl())
                .bannerUrl(habitatRequestDTO.getBannerUrl())
                .build();
    }
}
