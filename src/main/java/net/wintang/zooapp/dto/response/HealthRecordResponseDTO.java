package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.dto.mapper.AnimalMapper;
import net.wintang.zooapp.dto.mapper.UserMapper;
import net.wintang.zooapp.entity.HealthRecord;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthRecordResponseDTO implements Serializable {
    int id;
    LocalDateTime recordedDateTime;
    float weight;
    float height;
    float length;
    float temperature;
    String lifeStage;
    String diagnosis;
    String imgUrl;
    UserResponseDTO zooTrainer;
    AnimalResponseDTO animal;

    public HealthRecordResponseDTO(HealthRecord healthRecord) {
        this.id = healthRecord.getId();
        this.recordedDateTime = healthRecord.getRecordedDateTime();
        this.weight = healthRecord.getWeight();
        this.height = healthRecord.getHeight();
        this.length = healthRecord.getLength();
        this.temperature = healthRecord.getTemperature();
        this.lifeStage = healthRecord.getLifeStage();
        this.diagnosis = healthRecord.getDiagnosis();
        this.imgUrl = healthRecord.getImgUrl();
        this.zooTrainer = UserMapper.mapToUserDTO(healthRecord.getZooTrainer());
        this.animal = AnimalMapper.mapToAnimalDTO(healthRecord.getAnimal());
    }
}