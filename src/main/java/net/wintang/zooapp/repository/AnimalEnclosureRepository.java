package net.wintang.zooapp.repository;

import jakarta.validation.constraints.NotNull;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalEnclosure;
import net.wintang.zooapp.entity.Enclosure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalEnclosureRepository extends JpaRepository<AnimalEnclosure, Integer> {

    Optional<AnimalEnclosure> findByAnimalAndEnclosureAndMoveOutDate(@NotNull Animal animal, @NotNull Enclosure enclosure, LocalDateTime moveOutDate);

    List<AnimalEnclosure> findByAnimalAndMoveOutDate(@NotNull Animal animal, LocalDateTime moveOutDate);

    List<AnimalEnclosure> findByAnimal(Animal animalId);
}