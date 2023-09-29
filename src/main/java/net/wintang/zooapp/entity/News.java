package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "News")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String shortDesc;
    private String thumbnailUrl;
    @Column(nullable = true)
    private String imgUrl;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
//    private int authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity authorId;

}
