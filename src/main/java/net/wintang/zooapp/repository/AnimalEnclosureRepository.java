package net.wintang.zooapp.repository;

import jakarta.validation.constraints.NotNull;
import net.wintang.zooapp.entity.Animal;
import net.wintang.zooapp.entity.AnimalEnclosure;
import net.wintang.zooapp.entity.Enclosure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalEnclosureRepository extends JpaRepository<AnimalEnclosure, Integer> {

    Optional<AnimalEnclosure> findByAnimalAndEnclosure(@NotNull Animal animal, @NotNull Enclosure enclosure);

    List<AnimalEnclosure> findByAnimal(Animal animalId);

    boolean existsByAnimalAndEnclosure(@NotNull Animal animal, @NotNull Enclosure enclosure);
}