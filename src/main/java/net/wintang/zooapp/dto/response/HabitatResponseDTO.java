package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.wintang.zooapp.entity.Habitat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitatResponseDTO {
    private int id;
    private String name;
    private String info;
    private LocalDateTime createdDate;
    private String imgUrl;
    private String bannerUrl;
    private boolean status;

    public HabitatResponseDTO(Habitat habitat) {
        this.id = habitat.getId();
        this.name = habitat.getName();
        this.info = habitat.getInfo();
        this.createdDate = habitat.getCreatedDate();
        this.imgUrl = habitat.getImgUrl();
        this.bannerUrl = habitat.getBannerUrl();
        this.status = habitat.isStatus();
    }
}