package com.example.server.service;

import com.example.server.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketPool ticketPool;

    @Autowired
    public TicketService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public synchronized boolean purchaseTicket() {
        return ticketPool.removeTicket();
    }

    public synchronized boolean releaseTicket() {
        return ticketPool.addTicket();
    }

    public int getAvailableTickets() {
        return ticketPool.getAvailableTickets();
    }

    public int getTotalTickets() {
        return ticketPool.getTotalTickets();
    }
}
