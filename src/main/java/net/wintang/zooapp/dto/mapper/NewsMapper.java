package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.NewsRequestDTO;
import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMapper {

    public static List<NewsResponseDTO> mapToNewsDTO(List<News> newsList) {
        return newsList.stream().map(NewsResponseDTO::new).toList();
    }

    public static News mapToNewsEntity(NewsRequestDTO newsRequestDTO) {
        return News.builder()
                .title(newsRequestDTO.getTitle())
                .shortDescription(newsRequestDTO.getShortDescription())
                .content(newsRequestDTO.getContent())
                .type(newsRequestDTO.getType())
                .imgUrl(newsRequestDTO.getImgUrl())
                .thumbnailUrl(newsRequestDTO.getThumbnailUrl())
                .build();
    }

    public static News mapToNewsEntity(NewsRequestDTO newsRequestDTO, News oldNews) {
        return News.builder()
                .id(oldNews.getId())
                .title(!newsRequestDTO.getTitle().isBlank() ? newsRequestDTO.getTitle() : oldNews.getTitle())
                .shortDescription(!newsRequestDTO.getShortDescription().isBlank() ? newsRequestDTO.getShortDescription() : oldNews.getShortDescription())
                .thumbnailUrl(!newsRequestDTO.getThumbnailUrl().isBlank() ? newsRequestDTO.getThumbnailUrl() : oldNews.getThumbnailUrl())
                .content(!newsRequestDTO.getContent().isBlank() ? newsRequestDTO.getContent() : oldNews.getContent())
                .imgUrl(!newsRequestDTO.getImgUrl().isBlank() ? newsRequestDTO.getImgUrl() : oldNews.getImgUrl())
                .author(oldNews.getAuthor())
                .type(!newsRequestDTO.getType().isBlank() ? newsRequestDTO.getType() : oldNews.getType())
                .status(newsRequestDTO.isStatus() != oldNews.isStatus() ? newsRequestDTO.isStatus() : oldNews.isStatus())
                .build();
    }
}
