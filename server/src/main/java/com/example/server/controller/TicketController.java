package com.example.server.controller;

import com.example.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/available")
    public int getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }

    @GetMapping("/total")
    public int getTotalTickets() {
        return ticketService.getTotalTickets();
    }

    @PostMapping("/purchase")
    public boolean purchaseTicket() {
        return ticketService.purchaseTicket();
    }

    @PostMapping("/release")
    public boolean releaseTicket() {
        return ticketService.releaseTicket();
    }
}
