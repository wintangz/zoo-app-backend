package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
