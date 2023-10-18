package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.AnimalDiet;
import net.wintang.zooapp.entity.Food;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDietResponseDTO implements Serializable {
    private int id;
    private String type;
    private LocalDateTime createdDate;
    private UserResponseDTO creator;
    private List<Food> foodList;

    public AnimalDietResponseDTO(AnimalDiet animalDiet) {
        this.id = animalDiet.getId();
        this.type = animalDiet.getType();
        this.createdDate = animalDiet.getCreatedDate();
        this.creator = UserMapper.mapToUserDTO(Collections.singletonList(animalDiet.getCreator())).get(0);
        this.foodList = animalDiet.getFoodList();
    }
}