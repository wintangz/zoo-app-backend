package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.dto.response.TicketResponseDTO;
import net.wintang.zooapp.repository.TicketRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> findAllTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        mapToDTO(ticketRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createNewTicket(Ticket ticket) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatusMessage.OK,
                        ApplicationConstants.ResponseStatusMessage.SUCCESS,
                        ticketRepository.save(ticket))
        );
    }

    private List<TicketResponseDTO> mapToDTO(List<Ticket> tickets) {
        List<TicketResponseDTO> result = new ArrayList<>();
        for (Ticket ticket:tickets) {
            if(ticket.isStatus()){
                result.add(new TicketResponseDTO(ticket));
            }
        }
        return result;
    }
}
