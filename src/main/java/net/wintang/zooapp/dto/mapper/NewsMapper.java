package net.wintang.zooapp.dto.mapper;

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

    public News mapToNewsEntity(NewsResponseDTO newsResponseDto) {
        return News.builder()
                .title(newsResponseDto.getTitle())
                .content(newsResponseDto.getContent())
                .imgUrl(newsResponseDto.getImgUrl())
                .thumbnailUrl(newsResponseDto.getThumbnailUrl())
                .author(User.builder().id(newsResponseDto.getAuthor()).build())
                .build();
    }
}
