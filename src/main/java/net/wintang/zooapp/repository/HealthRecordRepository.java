package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, Integer> {
}