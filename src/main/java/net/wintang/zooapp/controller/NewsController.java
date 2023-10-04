package net.wintang.zooapp.controller;

import net.wintang.zooapp.model.NewsDTO;
import net.wintang.zooapp.service.INewsService;
import net.wintang.zooapp.util.ResponseObject;
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
    public ResponseEntity<ResponseObject> getAllNews() {
        return newsService.findAllNews();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> postNews(@RequestBody NewsDTO newsDto) {
        return newsService.createNewNews(newsDto);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ResponseObject> getRecommend() { return newsService.find3LatestNews(); }
}
