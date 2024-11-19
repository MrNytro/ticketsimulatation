package com.example.server.model;

public class Ticket {

    private int ticketId;  // Unique ticket ID
    private String status; // "available", "sold", etc.
    private boolean isAvailable;

    // Constructor to initialize with ticketId (unique ID)
    public Ticket(int ticketId) {
        this.ticketId = ticketId;
        this.status = "available";  // Default status as available
        this.isAvailable = true;
    }

    // Constructor for other use cases
    public Ticket(String ticketId, String status) {
        this.ticketId = Integer.parseInt(ticketId);  // Parse string ID to int, if needed
        this.status = status;
    }

    // Getters and setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", status='" + status + '\'' +
                '}';
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

}
