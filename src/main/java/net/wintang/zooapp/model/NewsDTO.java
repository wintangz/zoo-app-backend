package net.wintang.zooapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.News;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private String title;
    private String shortDescription;
    private String content;
    private int author;
    private String imgUrl;
    private String thumbnailUrl;
    private LocalDateTime createdDate;


    public NewsDTO(News news) {
        this.title = news.getTitle();
        this.shortDescription = news.getShortDescription();
        this.content = news.getContent();
        this.author = news.getAuthor().getId();
        this.imgUrl = news.getImgUrl();
        this.thumbnailUrl = news.getThumbnailUrl();
        this.createdDate = news.getCreatedDate();
    }
}
