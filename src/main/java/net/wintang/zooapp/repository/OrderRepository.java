package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomer(User customer);
}
