package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.News;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String content;
    private int author;
    private String imgUrl;
    private String thumbnailUrl;
    private LocalDateTime createdDate;
    private boolean status;


    public NewsResponseDTO(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.shortDescription = news.getShortDescription();
        this.content = news.getContent();
        this.author = news.getAuthor().getId();
        this.imgUrl = news.getImgUrl();
        this.thumbnailUrl = news.getThumbnailUrl();
        this.createdDate = news.getCreatedDate();
        this.status = news.isStatus();
    }
}
