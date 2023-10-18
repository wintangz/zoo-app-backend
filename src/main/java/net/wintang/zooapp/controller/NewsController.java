package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.request.NewsRequestDTO;
import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.exception.NotFoundException;
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getNewsById(@PathVariable int id) throws NotFoundException {
        return newsService.getNewsById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> postNews(@RequestBody NewsRequestDTO newsRequestDto) {
        return newsService.createNews(newsRequestDto);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ResponseObject> getRecommend() {
        return newsService.get3LatestNews();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateNewsById(@PathVariable int id, @RequestBody NewsRequestDTO newsRequestDTO) throws NotFoundException {
        return newsService.updateNewsById(newsRequestDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteNewsById(@PathVariable int id) throws NotFoundException {
        return newsService.deleteNewsById(id);
    }
}
