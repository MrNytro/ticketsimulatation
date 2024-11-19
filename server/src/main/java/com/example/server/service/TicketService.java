package com.example.server.service;

import com.example.server.model.TicketingConfiguration;
import com.example.server.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TicketService {

    private TicketingConfiguration currentConfig;
    private List<Ticket> tickets = new ArrayList<>();
    private int totalTickets;
    private ExecutorService executorService;  // Executor for handling concurrent tasks

    // Start the simulation with the given configuration
    public int startSimulation(TicketingConfiguration config) {
        this.currentConfig = config;
        this.totalTickets = config.getTotalTickets();
        initializeTickets(config.getTotalTickets());  // Initialize ticket pool
        System.out.println("Simulation started with config: " + config);

        // Initialize ExecutorService to manage concurrent tasks
        executorService = Executors.newFixedThreadPool(2);  // 2 threads: one for booking, one for releasing

        // Start ticket release simulation in a separate thread
        executorService.submit(this::simulateTicketRelease);

        return 1;  // Success
    }

    // Initialize tickets based on the total count
    private void initializeTickets(int totalTickets) {
        for (int i = 0; i < totalTickets; i++) {
            tickets.add(new Ticket(i + 1));  // Add tickets with unique IDs and status
        }
    }

    // Stop the simulation
    public int stopSimulation() {
        tickets.clear();
        executorService.shutdownNow();  // Stop the executor service
        System.out.println("Simulation stopped.");
        return 1;  // Success
    }

    // Update the configuration dynamically
    public int updateConfiguration(TicketingConfiguration config) {
        this.currentConfig = config;
        System.out.println("Configuration updated to: " + config);
        return 1;  // Success
    }

    // Get all tickets (for backend monitoring)
    public List<Ticket> getAllTickets() {
        return tickets;
    }

    // Purchase (retrieve) a ticket
    public synchronized int retrieveTicket(Ticket ticket) {
        if (ticket.getStatus().equals("Available")) {
            ticket.setStatus("Sold");  // Mark ticket as sold
            tickets.remove(ticket);
            System.out.println("Ticket " + ticket.getId() + " sold.");
            return 1;  // Success
        }
        return -1;  // Ticket already sold
    }

    // Release a ticket (add it back to the pool)
    public synchronized int releaseTicket() {
        if (tickets.size() < totalTickets) {
            int newTicketId = tickets.size() + 1;  // Assign new ticket ID
            tickets.add(new Ticket(newTicketId));  // Add a new available ticket
            System.out.println("Ticket " + newTicketId + " released.");
            return 1;  // Success
        }
        return -2;  // Capacity reached
    }

    // Simulate ticket release periodically (this method runs in a separate thread)
    private void simulateTicketRelease() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(currentConfig.getTicketReleaseRate());  // Wait for the release rate time
                releaseTicket();  // Release a ticket
            }
        } catch (InterruptedException e) {
            System.out.println("Ticket release simulation interrupted.");
        }
    }

    // Method to allocate a ticket
    public boolean allocateTicket(Ticket ticket) {
        if (ticket != null && ticket.isAvailable()) {
            ticket.setStatus("Allocated");
            ticket.setAvailable(false);
            return true;
        }
        return false;
    }

    // Method to purchase a ticket
    public int purchaseTicket(Ticket ticket) {
        if (ticket != null && ticket.isAvailable()) {
            ticket.setStatus("Purchased");
            ticket.setAvailable(false);  // Mark the ticket as unavailable
            return 1;  // Success
        }
        return -2;  // Ticket unavailable
    }
}
