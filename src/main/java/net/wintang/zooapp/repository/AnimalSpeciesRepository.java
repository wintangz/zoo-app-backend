package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.AnimalSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalSpeciesRepository extends JpaRepository<AnimalSpecies, Integer> {
}
