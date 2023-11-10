package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalTrainerAssignor;
import net.wintang.zooapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalTrainerAssignorRepository extends JpaRepository<AnimalTrainerAssignor, Integer> {
    boolean existsByAnimalAndTrainerAndUnassignedDate(Animal animal, User trainer, LocalDateTime unassignedDate);

    Optional<AnimalTrainerAssignor> findByAnimalAndTrainer(Animal animal, User trainer);

    Optional<AnimalTrainerAssignor> findByAnimalAndTrainerAndUnassignedDate(Animal animal, User trainer, LocalDateTime unassignedDate);

    Optional<List<AnimalTrainerAssignor>> findAllByAnimal(Animal build);
}