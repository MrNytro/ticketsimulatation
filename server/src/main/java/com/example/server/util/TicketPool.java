package com.example.server.util;

import com.example.server.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class TicketPool {

    private final Queue<Ticket> ticketPool;
    private int totalTickets;
    private int maxTicketCapacity;

    public TicketPool() {
        this.ticketPool = new LinkedList<>();
        this.maxTicketCapacity = 100; // Default max capacity
        this.totalTickets = maxTicketCapacity;
        initializeTicketPool();
    }

    // Initialize the ticket pool with max capacity
    private void initializeTicketPool() {
        for (int i = 0; i < maxTicketCapacity; i++) {
            ticketPool.add(new Ticket(i + 1));  // Add tickets with unique IDs
        }
    }

    // Method to set max ticket capacity
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        // Reinitialize the pool with the new capacity
        ticketPool.clear();
        initializeTicketPool();
    }

    // Method to set total tickets
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public synchronized boolean addTicket() {
        if (ticketPool.size() < maxTicketCapacity) {
            ticketPool.add(new Ticket(ticketPool.size() + 1));  // Add a new ticket if capacity allows
            totalTickets++;
            return true;
        }
        return false;
    }

    public synchronized boolean removeTicket() {
        if (!ticketPool.isEmpty()) {
            Ticket ticket = ticketPool.poll();
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
