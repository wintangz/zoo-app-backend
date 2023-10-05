package net.wintang.zooapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.wintang.zooapp.entity.Ticket;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private int id;

    private String name;

    private float price;

    private String type;

    private String description;

    private String imgUrl;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.name = ticket.getName();
        this.price = ticket.getPrice();
        this.type = ticket.getType();
        this.description = ticket.getDescription();
        this.imgUrl = ticket.getImgUrl();
    }
}
