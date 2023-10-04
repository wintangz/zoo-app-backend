package net.wintang.zooapp.service;

import net.wintang.zooapp.model.NewsDTO;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface INewsService {
    ResponseEntity<ResponseObject> findAllNews();
    ResponseEntity<ResponseObject> createNewNews(NewsDTO newsDto);
    ResponseEntity<ResponseObject> find3LatestNews();
}
