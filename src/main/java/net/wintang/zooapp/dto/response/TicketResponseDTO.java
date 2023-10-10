package net.wintang.zooapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.Ticket;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private int id;

    private String name;

    private float price;

    private String type;

    private String description;

    private String imgUrl;

    public TicketResponseDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.name = ticket.getName();
        this.price = ticket.getPrice();
        this.type = ticket.getType();
        this.description = ticket.getDescription();
        this.imgUrl = ticket.getImgUrl();
    }
}
