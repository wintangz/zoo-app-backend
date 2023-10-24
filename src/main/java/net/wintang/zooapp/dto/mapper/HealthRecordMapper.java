package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.HealthRecordRequestDTO;
import net.wintang.zooapp.dto.response.HealthRecordResponseDTO;
import net.wintang.zooapp.entity.HealthRecord;

import java.time.LocalDateTime;
import java.util.List;

public class HealthRecordMapper {

    public static HealthRecordResponseDTO mapToHealthRecordDto(HealthRecord healthRecord) {
        return new HealthRecordResponseDTO(healthRecord);
    }

    public static List<HealthRecordResponseDTO> mapToHealthRecordDto(List<HealthRecord> healthRecord) {
        return healthRecord.stream().map(HealthRecordResponseDTO::new).toList();
    }

    public static HealthRecord mapToHealthRecordEntity(HealthRecordRequestDTO healthRecordRequestDTO) {
        return HealthRecord.builder()
                .recordedDateTime(LocalDateTime.now())
                .diagnosis(healthRecordRequestDTO.getDiagnosis())
                .height(healthRecordRequestDTO.getHeight())
                .weight(healthRecordRequestDTO.getWeight())
                .length(healthRecordRequestDTO.getLength())
                .lifeStage(healthRecordRequestDTO.getLifeStage())
                .temperature(healthRecordRequestDTO.getTemperature())
                .imgUrl(healthRecordRequestDTO.getImgUrl())
                .build();
    }
}
