package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.NewsRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface INewsService {
    ResponseEntity<ResponseObject> getNews();

    ResponseEntity<ResponseObject> getNewsById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createNews(NewsRequestDTO newsRequestDTO);

    ResponseEntity<ResponseObject> get3LatestNews();

    ResponseEntity<ResponseObject> updateNewsById(NewsRequestDTO newsRequestDTO, int id) throws NotFoundException;
}
