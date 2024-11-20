package com.example.server.service;

import com.example.server.model.TicketingConfiguration;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class TicketService {
    private TicketingConfiguration config;
    private Timer timer;
    private boolean simulationRunning;
    private String latestEventLog = ""; // Store only the latest event log
    private String previousEventLog = ""; // Store the previous event log

    // Start the simulation with the provided configuration
    public int startSimulation(TicketingConfiguration config) {
        this.config = config;
        this.simulationRunning = true;

        // Initialize totalTickets and availableTickets
        if (this.config.getTotalTickets() == 0) {
            this.config.setTotalTickets(0);
        }
        this.config.setAvailableTickets(this.config.getTotalTickets()); // Set availableTickets to totalTickets

        // Start a background thread for simulation
        startTicketSimulation();
        return 1;
    }

    // Stop the simulation
    public int stopSimulation() {
        this.simulationRunning = false;
        if (timer != null) {
            timer.cancel(); // Stop the timer if simulation is stopped
        }
        return 1;
    }

    // Update the simulation configuration
    public int updateConfiguration(TicketingConfiguration newConfig) {
        this.config = newConfig;
        return 1;
    }

    // Fetch the current ticketing configuration
    public TicketingConfiguration getLiveUpdates() {
        return this.config;
    }

    // Fetch the latest event log with both logs (customer and vendor)
    public String getLatestEventLog() {
        // Return both the logs with a newline separator
        return previousEventLog + "\n" + latestEventLog;
    }

    // Start the ticket simulation with customer and vendor threads
    private void startTicketSimulation() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!simulationRunning) return; // Exit if simulation is stopped

                // Customer thread simulating ticket retrieval
                if (config.getAvailableTickets() >= config.getCustomerRetrievalRate()) {
                    config.setAvailableTickets(config.getAvailableTickets() - config.getCustomerRetrievalRate());
                    logTicketUpdate("Customer bought " + config.getCustomerRetrievalRate() + " tickets");
                }

                // Vendor thread simulating ticket release
                if (config.getTotalTickets() < config.getMaxTicketCapacity()) {
                    config.setTotalTickets(config.getTotalTickets() + config.getTicketReleaseRate());
                    config.setAvailableTickets(config.getAvailableTickets() + config.getTicketReleaseRate());
                    logTicketUpdate("Vendor released " + config.getTicketReleaseRate() + " tickets");
                }

                // Ensure totalTickets never exceeds maxTickets and doesn't go below zero
                if (config.getTotalTickets() > config.getMaxTicketCapacity()) {
                    config.setTotalTickets(config.getMaxTicketCapacity());
                }
                if (config.getAvailableTickets() < 0) {
                    config.setAvailableTickets(0);
                }
            }
        }, 0, 1000); // Runs every 1 second
    }

    // Log the current ticket pool state (to be sent to the frontend)
    private void logTicketUpdate(String action) {
        // Store the previous log before updating
        previousEventLog = latestEventLog;

        // Create the log message with the current ticket state
        latestEventLog = action + " | " +
                String.format("{totalTickets: %d, availableTickets: %d}",
                        config.getTotalTickets(), config.getAvailableTickets());

        // Optionally, you can log it to the console
        System.out.println(latestEventLog);
    }
}
