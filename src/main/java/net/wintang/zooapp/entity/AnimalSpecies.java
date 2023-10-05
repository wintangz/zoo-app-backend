package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "species")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AnimalSpecies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String species;

    private String genus;

    private String family;

    private String habitat;

    private String diet;

    private String conversationStatus;

    @Lob
    private String description;

    @Lob
    private String imgUrl;

    @Lob
    private String avatarUrl;
}
