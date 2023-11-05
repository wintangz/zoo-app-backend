package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.FoodRequestDTO;
import net.wintang.zooapp.dto.response.FoodResponseDTO;
import net.wintang.zooapp.entity.Food;

import java.util.List;

public class FoodMapper {

    public static List<FoodResponseDTO> mapToFoodDto(List<Food> foodList) {
        return foodList.stream().map(FoodResponseDTO::new).toList();
    }

    public static FoodResponseDTO mapToFoodDto(Food food) {
        return new FoodResponseDTO(food);
    }

    public static Food mapToFoodEntity(FoodRequestDTO foodRequestDTO) {
        return Food.builder()
                .type(foodRequestDTO.getType())
                .name(foodRequestDTO.getName())
                .status(foodRequestDTO.isStatus())
                .build();
    }
}
