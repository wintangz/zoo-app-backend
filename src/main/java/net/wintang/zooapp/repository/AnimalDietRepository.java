package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.AnimalDiet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalDietRepository extends JpaRepository<AnimalDiet, Integer> {
}