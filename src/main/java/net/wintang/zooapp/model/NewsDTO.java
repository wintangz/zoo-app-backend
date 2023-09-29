package net.wintang.zooapp.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.wintang.zooapp.entity.News;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NewsDTO {
    private String title;
    private String shortDesc;
    private String thumbnailUrl;
    private String imgUrl;
    private String content;
//    private Date createDate;
    private int authorId;
    private List<News>news;
}
