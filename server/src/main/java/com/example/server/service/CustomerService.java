package com.example.server.service;

import org.springframework.stereotype.Service;
import com.example.server.model.Ticket;

@Service
public class CustomerService {

    private final TicketService ticketService;

    public CustomerService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public int purchaseTicket() {
        // Create a new Ticket (you may want to use a unique ID or other logic)
        Ticket newTicket = new Ticket(Integer.parseInt("1"));
        // Modify this based on your logic
        return ticketService.purchaseTicket(newTicket);
    }
}
