package com.example.server.controller;

import com.example.server.model.TicketingConfiguration;
import com.example.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/start")
    public int startSimulation(@RequestBody TicketingConfiguration config) {
        String clientIp = getClientIp();
        System.out.println("[tktControl] Received POST request from " + clientIp + " to start simulation with config: " + config);
        int result = ticketService.startSimulation(config);
        System.out.println("[tktControl] Simulation started successfully. Result: " + result);
        return result;
    }

    @PostMapping("/stop")
    public int stopSimulation() {
        String clientIp = getClientIp();
        System.out.println("[tktControl] Received POST request from " + clientIp + " to stop simulation.");
        int result = ticketService.stopSimulation();
        System.out.println("[tktControl] Simulation stopped successfully. Result: " + result);
        return result;
    }

    @PostMapping("/update")
    public int updateConfiguration(@RequestBody TicketingConfiguration config) {
        String clientIp = getClientIp();
        System.out.println("[tktControl] Received POST request from " + clientIp + " to update configuration: " + config);
        int result = ticketService.updateConfiguration(config);
        System.out.println("[tktControl] Configuration updated successfully. Result: " + result);
        return result;
    }

    @GetMapping("/state")
    public TicketingConfiguration getCurrentState() {
        String clientIp = getClientIp();
        System.out.println("[tktControl] Received GET request from " + clientIp + " to get the current state.");

        // Use getLiveUpdates to get the current configuration state
        TicketingConfiguration state = ticketService.getLiveUpdates();

        System.out.println("[tktControl] Current state: " + state);
        return state;
    }

    // New endpoint for live updates via basic API call
    @GetMapping("/live-updates")
    public TicketingConfiguration getLiveUpdates() {
        String clientIp = getClientIp();
        System.out.println("[tktControl] Received GET request from " + clientIp + " for live updates.");

        // Fetch the current ticketing configuration for live updates
        TicketingConfiguration config = ticketService.getLiveUpdates();

        return config;
    }

    // New endpoint to fetch event logs
    @GetMapping("/logs")
    public List<String> getLogs() {
        String clientIp = getClientIp();
        System.out.println("[tktControl] Received GET request from " + clientIp + " to fetch logs.");

        // Fetch the event logs from the service
        List<String> logs = ticketService.getEventLogs();

        return logs; // Return logs to frontend
    }

    // Helper method to get client IP (can be improved based on actual use case)
    private String getClientIp() {
        return "Unknown Client"; // Replace with logic to retrieve actual client IP if needed
    }
}
