package com.example.server.service;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final TicketService ticketService;

    public CustomerService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public boolean purchaseTicket() {
        return ticketService.purchaseTicket();
    }
}
