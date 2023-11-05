package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.AnimalDietRequestDTO;
import net.wintang.zooapp.dto.response.AnimalDietResponseDTO;
import net.wintang.zooapp.entity.AnimalDiet;
import net.wintang.zooapp.entity.Food;

import java.util.List;

public class AnimalDietMapper {

    public static List<AnimalDietResponseDTO> mapToDietDto(List<AnimalDiet> dietList) {
        return dietList.stream().map(AnimalDietResponseDTO::new).toList();
    }

    public static AnimalDietResponseDTO mapToDietDto(AnimalDiet diet) {
        return new AnimalDietResponseDTO(diet);
    }

    public static AnimalDiet mapToDietEntity(AnimalDietRequestDTO animalDietRequestDto) {
        return AnimalDiet.builder()
                .type(animalDietRequestDto.getType())
                .foodList(animalDietRequestDto.getFoodListIds().stream().map(Food::new).toList())
                .status(animalDietRequestDto.isStatus())
                .build();
    }
}
