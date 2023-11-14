package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.NewsMapper;
import net.wintang.zooapp.dto.request.NewsRequestDTO;
import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.NewsRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService implements INewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getNews() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        NewsMapper.mapToNewsDTO(newsRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getNewsById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        new NewsResponseDTO(newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News with id: " + id))))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createNews(NewsRequestDTO newsRequestDTO) {
        News news = NewsMapper.mapToNewsEntity(newsRequestDTO);
        User author = new User();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        author.setId(Integer.parseInt(userDetails.getUsername()));
        news.setAuthor(author);
        newsRepository.save(news);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS, newsRequestDTO)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> get3LatestNews() {
        List<NewsResponseDTO> news = NewsMapper.mapToNewsDTO(newsRepository.findAll().stream().filter(n -> n.isStatus()).toList());
        List<NewsResponseDTO> recommend = List.of(news.get(news.size() - 1), news.get(news.size() - 2), news.get(news.size() - 3));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        recommend)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateNewsById(NewsRequestDTO newsRequestDTO, int id) throws NotFoundException {
        News oldNews = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News ID: " + id));
        newsRepository.save(NewsMapper.mapToNewsEntity(newsRequestDTO, oldNews));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        newsRequestDTO)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteNewsById(int id) throws NotFoundException {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("News ID: " + id);
    }
}
