package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ITicketService {
    ResponseEntity<ResponseObject> findAllTickets();

    ResponseEntity<ResponseObject> createNewTicket(Ticket ticket);
}
