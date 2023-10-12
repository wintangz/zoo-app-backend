package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Species findByName(String name);
}
