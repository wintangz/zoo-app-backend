package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.News;
import net.wintang.zooapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long> {
//    Optional<News> findByAuthorId(int authorId);
}
