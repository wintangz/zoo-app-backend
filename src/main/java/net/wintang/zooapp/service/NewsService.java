package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.entity.UserEntity;
import net.wintang.zooapp.model.NewsDTO;
import net.wintang.zooapp.repository.NewsRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
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

    private List<NewsDTO> mapToDTO(List<News> newsList) {
        return newsList.stream().map(NewsDTO::new).toList();
    }

    private News mapToNewsEntity(NewsDTO newsDto) {
        return News.builder()
                .title(newsDto.getTitle())
                .content(newsDto.getContent())
                .imgUrl(newsDto.getImgUrl())
                .thumbnailUrl(newsDto.getThumbnailUrl())
                .author(UserEntity.builder().id(newsDto.getAuthor()).build())
                .build();
    }

    @Override
    public ResponseEntity<ResponseObject> findAllNews() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToDTO(newsRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createNewNews(NewsDTO newsDto) {
        News news = mapToNewsEntity(newsDto);
        newsRepository.save(news);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS, newsDto)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> find3LatestNews() {
        List<NewsDTO> news = mapToDTO(newsRepository.findAll());
        List<NewsDTO> recommend = List.of(news.get(news.size()-1), news.get(news.size()-2), news.get(news.size()-3));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        recommend)
        );
    }

}
