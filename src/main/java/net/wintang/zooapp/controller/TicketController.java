package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.request.TicketRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.ITicketService;
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
    public ResponseEntity<ResponseObject> getTickets() {
        return ticketService.getTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTicketById(@PathVariable int id) throws NotFoundException {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createTicket(@RequestBody TicketRequestDTO ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTicketById(@PathVariable int id, @RequestBody TicketRequestDTO ticket) throws NotFoundException {
        return ticketService.updateTicketById(id, ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTicketById(@PathVariable int id) throws NotFoundException {
        return ticketService.deleteTicketById(id);
    }
}
