package com.example.server.controller;

import com.example.server.model.Ticket;
import com.example.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Get all tickets (for backend monitoring)
    @GetMapping("/")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Ticket release API (for internal backend process)
    @PostMapping("/release")
    public ResponseEntity<?> releaseTicket() {
        int released = ticketService.releaseTicket();
        if (released == 1) {
            return ResponseEntity.ok("Ticket released successfully");
        }
        return ResponseEntity.status(500).body("Failed to release ticket");
    }

    // Customer retrieves a ticket (for customer action)
    @PostMapping("/retrieve")
    public ResponseEntity<?> retrieveTicket(@RequestBody Ticket ticket) {
        boolean allocated = ticketService.allocateTicket(ticket);
        if (allocated) {
            return ResponseEntity.ok("Ticket allocated successfully");
        }
        return ResponseEntity.status(500).body("Failed to allocate ticket");
    }
}
