package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.service.INewsService;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final INewsService newsService;

    @Autowired
    public NewsController(INewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getNews() {
        return newsService.getNews();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> postNews(@RequestBody NewsResponseDTO newsResponseDto) {
        return newsService.createNews(newsResponseDto);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ResponseObject> getRecommend() {
        return newsService.get3LatestNews();
    }
}
