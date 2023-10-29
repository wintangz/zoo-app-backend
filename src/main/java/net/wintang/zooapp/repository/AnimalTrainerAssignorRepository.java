package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalTrainerAssignor;
import net.wintang.zooapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalTrainerAssignorRepository extends JpaRepository<AnimalTrainerAssignor, Integer> {
    boolean existsByAnimalAndTrainer(Animal animal, User trainer);

    Optional<AnimalTrainerAssignor> findByAnimalAndTrainer(Animal animal, User trainer);
}