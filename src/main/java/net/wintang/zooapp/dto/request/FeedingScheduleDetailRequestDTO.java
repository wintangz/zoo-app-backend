package net.wintang.zooapp.dto.request;

import lombok.Data;

@Data
public class FeedingScheduleDetailRequestDTO {

    private int foodId;

    private int expectedQuantity;
}
