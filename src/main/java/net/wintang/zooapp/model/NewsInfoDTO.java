package net.wintang.zooapp.model;

import lombok.Data;
import net.wintang.zooapp.entity.News;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class NewsInfoDTO {
    private Long id;
    private String title;
    private String shortDesc;
    private String thumbnailUrl;
    private String imgUrl;
    private String content;
    private LocalDateTime createDate;
    private int authorId;
    private List<News> news;

    public NewsInfoDTO(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.shortDesc = news.getShortDesc();
        this.thumbnailUrl = news.getThumbnailUrl();
        this.imgUrl = news.getImgUrl();
        this.content = news.getContent();
        this.createDate = LocalDateTime.now();
        this.authorId = news.getAuthorId().getId();
        this.news = news.getAuthorId().getNews();
    }
}
