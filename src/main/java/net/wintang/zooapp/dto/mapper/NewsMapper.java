package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.NewsRequestDTO;
import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.entity.News;

import java.util.List;

public class NewsMapper {

    public static List<NewsResponseDTO> mapToNewsDTO(List<News> newsList) {
        return newsList.stream().map(NewsResponseDTO::new).toList();
    }

    public static News mapToNewsEntity(NewsRequestDTO newsRequestDTO) {
        return News.builder()
                .title(newsRequestDTO.getTitle().trim())
                .shortDescription(newsRequestDTO.getShortDescription().trim())
                .content(newsRequestDTO.getContent().trim())
                .type(newsRequestDTO.getType().trim())
                .imgUrl(newsRequestDTO.getImgUrl())
                .thumbnailUrl(newsRequestDTO.getThumbnailUrl())
                .build();
    }

    public static News mapToNewsEntity(NewsRequestDTO newsRequestDTO, News oldNews) {
        return News.builder()
                .id(oldNews.getId())
                .title(!newsRequestDTO.getTitle().isBlank() ? newsRequestDTO.getTitle().trim() : oldNews.getTitle())
                .shortDescription(!newsRequestDTO.getShortDescription().isBlank() ? newsRequestDTO.getShortDescription().trim() : oldNews.getShortDescription())
                .thumbnailUrl(!newsRequestDTO.getThumbnailUrl().isBlank() ? newsRequestDTO.getThumbnailUrl() : oldNews.getThumbnailUrl())
                .content(!newsRequestDTO.getContent().isBlank() ? newsRequestDTO.getContent().trim() : oldNews.getContent())
                .imgUrl(!newsRequestDTO.getImgUrl().isBlank() ? newsRequestDTO.getImgUrl() : oldNews.getImgUrl())
                .author(oldNews.getAuthor())
                .type(!newsRequestDTO.getType().isBlank() ? newsRequestDTO.getType().trim() : oldNews.getType())
                .status(newsRequestDTO.isStatus() != oldNews.isStatus() ? newsRequestDTO.isStatus() : oldNews.isStatus())
                .build();
    }
}
