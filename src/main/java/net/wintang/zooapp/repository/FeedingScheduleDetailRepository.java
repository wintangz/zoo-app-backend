package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.FeedingSchedule;
import net.wintang.zooapp.entity.FeedingScheduleDetail;
import net.wintang.zooapp.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedingScheduleDetailRepository extends JpaRepository<FeedingScheduleDetail, Integer> {
    Optional<FeedingScheduleDetail> findByFoodAndFeedingSchedule(Food food, FeedingSchedule feedingSchedule);

    Optional<List<FeedingScheduleDetail>> findAllByFeedingSchedule(FeedingSchedule feedingSchedule);
}
