package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @SequenceGenerator(name = "seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    private Long id;
    private String username;
    private String password;
    private String role;
}
