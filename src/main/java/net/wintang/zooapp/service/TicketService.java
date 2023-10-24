package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.mapper.TicketMapper;
import net.wintang.zooapp.dto.request.TicketRequestDTO;
import net.wintang.zooapp.dto.response.TicketResponseDTO;
import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.exception.NotFoundException;
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

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ResponseEntity<ResponseObject> getTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        TicketMapper.mapToTicketDTO(ticketRepository.findAll()))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> getTicketById(int id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        TicketMapper.mapToTicketDTO(
                                ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket ID: " + id))
                        ))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> createTicket(TicketRequestDTO ticketRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                        ApplicationConstants.ResponseMessage.SUCCESS,
                        ticketRepository.save(TicketMapper.mapToTicketEntity(ticketRequestDTO)))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateTicketById(int id, TicketRequestDTO ticketRequestDTO) throws NotFoundException {
        if (ticketRepository.existsById(id)) {
            Ticket ticket = TicketMapper.mapToTicketEntity(ticketRequestDTO);
            ticket.setId(id);
            ticketRepository.save(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Ticket ID: " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteTicketById(int id) throws NotFoundException {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(ApplicationConstants.ResponseStatus.OK,
                            ApplicationConstants.ResponseMessage.SUCCESS,
                            id)
            );
        }
        throw new NotFoundException("Ticket ID: " + id);
    }
}
