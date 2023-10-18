package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.AnimalDietRequestDto;
import net.wintang.zooapp.dto.response.AnimalDietResponseDto;
import net.wintang.zooapp.entity.AnimalDiet;
import net.wintang.zooapp.entity.Food;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimalDietMapper {

    public static List<AnimalDietResponseDto> mapToDietDto(List<AnimalDiet> dietList) {
        return dietList.stream().map(AnimalDietResponseDto::new).toList();
    }

    public static AnimalDietResponseDto mapToDietDto(AnimalDiet diet) {
        return new AnimalDietResponseDto(diet);
    }

    public static AnimalDiet mapToDietEntity(AnimalDietRequestDto animalDietRequestDto) {
        return AnimalDiet.builder()
                .type(animalDietRequestDto.getType())
                .foodList(animalDietRequestDto.getFoodListIds().stream().map(Food::new).toList())
                .build();
    }
}
