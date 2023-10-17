package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedingScheduleRequestDto implements Serializable {

    @NotNull(message = "Feeding time cannot be null")
    @Future(message = "Feeding time cannot be in the past")
    LocalDateTime feedingTime;

    int dietId;

    int animalId;
}