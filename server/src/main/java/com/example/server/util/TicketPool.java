package com.example.server.util;

import com.example.server.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class TicketPool {

    private final Queue<Ticket> ticketPool;
    private int totalTickets;
    private final int maxTicketCapacity;

    public TicketPool() {
        this.ticketPool = new LinkedList<>();
        this.maxTicketCapacity = 100; // Default max capacity
        this.totalTickets = maxTicketCapacity;
        initializeTicketPool();
    }

    // Initialize the ticket pool with max capacity
    private void initializeTicketPool() {
        for (int i = 0; i < maxTicketCapacity; i++) {
            // Create a ticket with a unique ID (String), default status, and availability
            ticketPool.add(new Ticket(i + 1));
        }
    }

    public synchronized boolean addTicket() {
        if (ticketPool.size() < maxTicketCapacity) {
            // Add a new ticket with a unique ID (String), default status, and availability
            ticketPool.add(new Ticket(ticketPool.size() + 1));
            totalTickets++;
            return true;
        }
        return false;
    }

    public synchronized boolean removeTicket() {
        if (!ticketPool.isEmpty()) {
            ticketPool.poll(); // Remove a ticket
            totalTickets--;
            return true;
        }
        return false;
    }

    public int getAvailableTickets() {
        return ticketPool.size();
    }

    public int getTotalTickets() {
        return totalTickets;
    }
}
