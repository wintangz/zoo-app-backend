package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.response.NewsResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface INewsService {
    ResponseEntity<ResponseObject> getNews();

    ResponseEntity<ResponseObject> createNews(NewsResponseDTO newsResponseDto);

    ResponseEntity<ResponseObject> get3LatestNews();
}
