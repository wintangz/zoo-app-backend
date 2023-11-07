package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedingScheduleRequestDTO implements Serializable {

    @NotNull(message = "Feeding time cannot be null")
    @Future(message = "Feeding time cannot be in the past")
    private LocalDateTime feedingTime;

    private List<FeedingScheduleDetailRequestDTO> details;

    private int animalId;
}