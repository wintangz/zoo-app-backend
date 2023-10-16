package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.NewsRequestDTO;
import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMapper {

    public List<NewsResponseDTO> mapToNewsDTO(List<News> newsList) {
        return newsList.stream().map(NewsResponseDTO::new).toList();
    }

    public News mapToNewsEntity(NewsRequestDTO newsRequestDTO) {
        return News.builder()
                .title(newsRequestDTO.getTitle())
                .shortDescription(newsRequestDTO.getShortDescription())
                .content(newsRequestDTO.getContent())
                .type(newsRequestDTO.getType())
                .imgUrl(newsRequestDTO.getImgUrl())
                .thumbnailUrl(newsRequestDTO.getThumbnailUrl())
                .build();
    }
}
