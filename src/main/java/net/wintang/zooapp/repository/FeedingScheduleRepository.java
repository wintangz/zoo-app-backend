package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Integer> {
}