package net.wintang.zooapp.controller;

import net.wintang.zooapp.entity.Ticket;
import net.wintang.zooapp.service.ITicketService;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createNewTicket(@RequestBody Ticket ticket) {
        return ticketService.createNewTicket(ticket);
    }
}
