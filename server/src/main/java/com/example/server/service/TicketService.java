package com.example.server.service;

import com.example.server.model.TicketingConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class TicketService {
    private TicketingConfiguration config;
    private Timer timer;
    private boolean simulationRunning;
    private List<String> eventLogs = new ArrayList<>(); // List to hold event logs

    // Start the simulation with the provided configuration
    public int startSimulation(TicketingConfiguration config) {
        this.config = config;
        this.simulationRunning = true;

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

    // Fetch event logs
    public List<String> getEventLogs() {
        return new ArrayList<>(eventLogs); // Return a copy of the logs
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
        String logMessage = action + " | " +
                String.format("{totalTickets: %d, availableTickets: %d}",
                        config.getTotalTickets(), config.getAvailableTickets());

        // Add the log message to the eventLogs list
        eventLogs.add(logMessage);

        // Optionally, you can limit the log size (e.g., keep only the last 50 logs)
        if (eventLogs.size() > 50) {
            eventLogs.remove(0); // Remove the oldest log if the list exceeds the limit
        }

        // Optionally log to the console
        System.out.println(logMessage);
    }
}
