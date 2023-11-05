package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Integer> {
}