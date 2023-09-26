package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Role")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
