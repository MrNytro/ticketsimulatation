package com.example.server.service;

import com.example.server.model.TicketingConfiguration;
import com.example.server.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketPool ticketPool;
    private TicketingConfiguration configuration;

    @Autowired
    public TicketService(TicketPool ticketPool, TicketingConfiguration configuration) {
        this.ticketPool = ticketPool;
        this.configuration = configuration;
        // You can use the configuration here to adjust the behavior of ticketing, like rate limits
    }

    // Method to update the configuration from the controller
    public void configureTickets(TicketingConfiguration newConfiguration) {
        this.configuration = newConfiguration;  // Update the configuration with the new values
        ticketPool.setTotalTickets(newConfiguration.getTotalTickets());
        ticketPool.setMaxTicketCapacity(newConfiguration.getMaxTicketCapacity());
        // Update other configurations related to ticket release or retrieval rate, if needed
        System.out.println("Configuration updated: " + newConfiguration);
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

    // You can add additional methods to use configuration values like release rates, etc.
}
