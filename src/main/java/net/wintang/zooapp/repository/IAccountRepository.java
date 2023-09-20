package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Long> {
}
