package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nationalized
    private String title;

    @Nationalized
    private String shortDescription;

    @Lob
    @Nationalized
    private String content;

    @Column(insertable = false, updatable = false, nullable = true)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "author_id")
    private User author;

    @Lob
    @Nationalized
    private String imgUrl;

    @Lob
    @Nationalized
    private String thumbnailUrl;

}