package net.wintang.zooapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "enclosure")
public class Enclosure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    private String info;

    private int maxCapacity;

    @ManyToMany
    @JoinTable(name = "enclosure_species",
            joinColumns = @JoinColumn(name = "enclosure_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "species_id", referencedColumnName = "id"))
    private List<Species> speciesList;

    @Column(insertable = false, updatable = false, nullable = true)
    private LocalDateTime createdDate;

    @Lob
    @Nationalized
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "habitat_id")
    private Habitat habitat;

    @Column(columnDefinition = "bit default 1")
    private boolean status;
}