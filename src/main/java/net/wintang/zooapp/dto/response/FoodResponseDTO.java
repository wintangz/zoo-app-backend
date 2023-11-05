package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.Food;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDTO implements Serializable {

    private int id;
    private String name;
    private String type;
    private LocalDateTime createdDate;
    private UserResponseDTO creator;
    private boolean status;

    public FoodResponseDTO(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.type = food.getType();
        this.createdDate = food.getCreatedDate();
        this.creator = UserMapper.mapToUserDTO(food.getCreator());
        this.status = food.isStatus();
    }
}