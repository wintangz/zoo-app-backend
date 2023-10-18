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
    private String type;
    private String authorFirstname;
    private String authorLastname;
    private String imgUrl;
    private String thumbnailUrl;
    private LocalDateTime createdDate;
    private boolean status;


    public NewsResponseDTO(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.shortDescription = news.getShortDescription();
        this.content = news.getContent();
        this.authorFirstname = news.getAuthor().getFirstname();
        this.authorLastname = news.getAuthor().getLastname();
        this.imgUrl = news.getImgUrl();
        this.thumbnailUrl = news.getThumbnailUrl();
        this.createdDate = news.getCreatedDate();
        this.type = news.getType();
        this.status = news.isStatus();
    }
}
