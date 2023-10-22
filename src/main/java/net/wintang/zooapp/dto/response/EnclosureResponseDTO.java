package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.dto.mapper.HabitatMapper;
import net.wintang.zooapp.entity.Enclosure;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnclosureResponseDTO implements Serializable {
    private int id;
    private String name;
    private String info;
    private int maxCapacity;
    private LocalDateTime createdDate;
    private String imgUrl;
    private HabitatResponseDTO habitat;
    private boolean status;

    public EnclosureResponseDTO(Enclosure enclosure) {
        this.id = enclosure.getId();
        this.name = enclosure.getName();
        this.info = enclosure.getInfo();
        this.maxCapacity = enclosure.getMaxCapacity();
        this.createdDate = enclosure.getCreatedDate();
        this.imgUrl = enclosure.getImgUrl();
        this.habitat = HabitatMapper.mapToHabitatDTO(enclosure.getHabitat());
        this.status = enclosure.isStatus();
    }
}