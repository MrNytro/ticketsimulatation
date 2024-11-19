package com.example.server.model;

public class Ticket {
    private final int ticketId;

    public Ticket(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }
}
