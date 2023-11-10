package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.request.TicketRequestDTO;
import net.wintang.zooapp.dto.response.TicketResponseDTO;
import net.wintang.zooapp.entity.Ticket;

import java.util.List;

public class TicketMapper {

    public static List<TicketResponseDTO> mapToTicketDTO(List<Ticket> tickets) {
        return tickets.stream().map(TicketResponseDTO::new).toList();
    }

    public static TicketResponseDTO mapToTicketDTO(Ticket ticket) {
        return new TicketResponseDTO(ticket);
    }

    public static Ticket mapToTicketEntity(TicketRequestDTO ticketRequestDTO) {
        return Ticket.builder()
                .name(ticketRequestDTO.getName().trim())
                .price(ticketRequestDTO.getPrice())
                .type(ticketRequestDTO.getType().trim())
                .description(ticketRequestDTO.getDescription().trim())
                .imgUrl(ticketRequestDTO.getImgUrl())
                .status(ticketRequestDTO.isStatus())
                .build();
    }
}
