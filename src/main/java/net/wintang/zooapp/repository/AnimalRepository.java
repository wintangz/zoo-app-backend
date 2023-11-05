package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    boolean existsByIdAndStatus(int id, boolean status);
}
