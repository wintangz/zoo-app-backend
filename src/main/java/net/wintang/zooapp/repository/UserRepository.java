package net.wintang.zooapp.repository;

import net.wintang.zooapp.entity.Role;
import net.wintang.zooapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    @Query(value = "select distinct * from " +
            "[user] u left join user_role r on u.id=r.user_id " +
            "where r.role_id=:role", nativeQuery = true)
    List<UserEntity> findByRole(@Param("role") int roleId);
}
