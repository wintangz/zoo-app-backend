package net.wintang.zooapp.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsRequestDTO {
    private String title;
    private String shortDescription;
    private String content;
    private String type;
    private String imgUrl;
    private String thumbnailUrl;
    private boolean status;
}
