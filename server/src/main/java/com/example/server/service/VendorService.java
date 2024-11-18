package com.example.server.service;

import org.springframework.stereotype.Service;

@Service
public class VendorService {

    private final TicketService ticketService;

    public VendorService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public boolean releaseTicket() {
        return ticketService.releaseTicket();
    }
}
