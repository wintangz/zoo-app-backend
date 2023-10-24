package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.TicketRequestDTO;
import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface ITicketService {
    ResponseEntity<ResponseObject> getTickets();

    ResponseEntity<ResponseObject> getTicketById(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createTicket(TicketRequestDTO ticketRequestDTO);

    ResponseEntity<ResponseObject> updateTicketById(int id, TicketRequestDTO ticketRequestDTO) throws NotFoundException;

    ResponseEntity<ResponseObject> deleteTicketById(int id) throws NotFoundException;
}
