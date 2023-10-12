package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "species")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private String species;

    private String genus;

    private String family;

    private String diet;

    private String conversationStatus;

    @Lob
    private String description;

    @Lob
    private String imgUrl;

    @Lob
    private String avatarUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "habitat_id")
    private Habitat habitat;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "bit default 1")
    private boolean status;
}
