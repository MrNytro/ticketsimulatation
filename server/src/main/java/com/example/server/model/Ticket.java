package com.example.server.model;

public class Ticket {
    private int ticketId;
    private boolean isAvailable;

    public Ticket(int ticketId) {
        this.ticketId = ticketId;
        this.isAvailable = true; // Initially available
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
