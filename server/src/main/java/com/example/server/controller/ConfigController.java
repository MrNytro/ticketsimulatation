package com.example.server.controller;

import com.example.server.service.TicketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final TicketService ticketService;

    public ConfigController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Fetch the current total tickets available
    @GetMapping("/tickets/available")
    public int getAvailableTickets() {
        return ticketService.getAvailableTickets();
    }

    // Fetch the total tickets count
    @GetMapping("/tickets/total")
    public int getTotalTickets() {
        return ticketService.getTotalTickets();
    }

    // Optionally: Add/remove tickets manually for testing
    @PostMapping("/tickets/add")
    public boolean addTicket() {
        return ticketService.releaseTicket();
    }

    @PostMapping("/tickets/remove")
    public boolean removeTicket() {
        return ticketService.purchaseTicket();
    }
}
