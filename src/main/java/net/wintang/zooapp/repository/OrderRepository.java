package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
