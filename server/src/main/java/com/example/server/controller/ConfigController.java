package com.example.server.controller;

import com.example.server.model.TicketingConfiguration;
import com.example.server.service.TicketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final TicketService ticketService;

    public ConfigController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/set")
    public void setConfiguration(@RequestBody TicketingConfiguration configuration) {
        ticketService.configureTickets(configuration);
    }
}
