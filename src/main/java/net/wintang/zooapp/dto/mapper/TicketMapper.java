package net.wintang.zooapp.dto.mapper;

import net.wintang.zooapp.dto.response.TicketResponseDTO;
import net.wintang.zooapp.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

    public List<TicketResponseDTO> mapToTicketDTO(List<Ticket> tickets) {
        return tickets.stream().map(TicketResponseDTO::new).toList();
    }
}
