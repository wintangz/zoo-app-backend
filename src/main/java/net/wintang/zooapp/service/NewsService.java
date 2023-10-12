package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.repository.NewsRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService implements INewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    private List<NewsResponseDTO> mapToDTO(List<News> newsList) {
        return newsList.stream().map(NewsResponseDTO::new).toList();
    }

    private News mapToNewsEntity(NewsResponseDTO newsResponseDto) {
        return News.builder()
                .title(newsResponseDto.getTitle())
                .content(newsResponseDto.getContent())
                .imgUrl(newsResponseDto.getImgUrl())
                .thumbnailUrl(newsResponseDto.getThumbnailUrl())
                .author(User.builder().id(newsResponseDto.getAuthor()).build())
                .build();
    }

    @Override
    public ResponseEntity<ResponseObject> getNews() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        mapToDTO(newsRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createNews(NewsResponseDTO newsResponseDto) {
        News news = mapToNewsEntity(newsResponseDto);
        newsRepository.save(news);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, newsResponseDto)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> get3LatestNews() {
        List<NewsResponseDTO> news = mapToDTO(newsRepository.findAll());
        List<NewsResponseDTO> recommend = List.of(news.get(news.size() - 1), news.get(news.size() - 2), news.get(news.size() - 3));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        recommend)
        );
    }

}
