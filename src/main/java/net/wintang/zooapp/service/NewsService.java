package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.model.NewsDTO;
import net.wintang.zooapp.model.NewsInfoDTO;
import net.wintang.zooapp.repository.NewsRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService implements INewsService{
    private final NewsRepository newsRepository;
    private News mapToNewsEntity(NewsDTO news){
        return News.builder()
                .title(news.getTitle())
                .shortDesc(news.getShortDesc())
                .thumbnailUrl(news.getThumbnailUrl())
                .imgUrl(news.getImgUrl())
                .content(news.getContent())
                .createDate(LocalDateTime.now())
                .authorId(news.getAuthorId().getId())
                .build();
    }
    @Autowired
    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }
    private List<NewsInfoDTO> mapToInfoDTO(List<News> news){
        return news.stream().map(NewsInfoDTO::new).toList();
    }
    @Override
    public ResponseEntity<ResponseObject> findAllNews(){
        return ResponseEntity.ok(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToInfoDTO(newsRepository.findAll()))
        );
    }
    @Override
    public ResponseEntity<ResponseObject> createNewNews(NewsDTO newsDTO){
        News news = mapToNewsEntity(newsDTO);
        newsRepository.save(news);
        return ResponseEntity.ok(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        newsDTO)
        );
    }

}
