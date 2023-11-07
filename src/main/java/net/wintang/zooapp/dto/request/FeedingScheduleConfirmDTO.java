package net.wintang.zooapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedingScheduleConfirmDTO implements Serializable {

    private List<FeedingScheduleDetailConfirmDTO> details;
}