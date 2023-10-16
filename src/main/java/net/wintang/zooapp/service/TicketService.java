package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.TicketMapper;
import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.repository.TicketRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.dto.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> getTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        ticketMapper.mapToTicketDTO(ticketRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createTicket(Ticket ticket) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        ticketRepository.save(ticket))
        );
    }
}
