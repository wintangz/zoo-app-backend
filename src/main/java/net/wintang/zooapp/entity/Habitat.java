package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

@Entity
@Table(name = "habitat")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String info;

    @Column(insertable = false, updatable = false, nullable = true)
    private LocalDateTime createdDate;

    @Lob
    @Nationalized
    private String imgUrl;

    @Lob
    @Nationalized
    private String bannerUrl;

    private boolean status;
}
