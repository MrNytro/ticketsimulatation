package com.example.server.controller;

import com.example.server.model.TicketingConfiguration;
import com.example.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private TicketService ticketService;

    // Endpoint to set configuration
    @PostMapping("/set")
    public ResponseEntity<Integer> setConfiguration(@RequestBody TicketingConfiguration config) {
        int success = ticketService.updateConfiguration(config);
        if (success == 1) {
            return ResponseEntity.ok(1); // Success status code
        } else {
            return ResponseEntity.status(500).body(-1); // Failure status code
        }
    }

    // Endpoint to start the system
    @PostMapping("/start")
    public ResponseEntity<Integer> startSystem(@RequestBody TicketingConfiguration config) {
        int result = ticketService.startSimulation(config);
        if (result == 1) {
            return ResponseEntity.ok(1); // Success status code
        } else {
            return ResponseEntity.status(500).body(-1); // Failure status code
        }
    }

    // Endpoint to stop the system
    @PostMapping("/stop")
    public ResponseEntity<Integer> stopSystem() {
        int result = ticketService.stopSimulation();
        if (result == 1) {
            return ResponseEntity.ok(1); // Success status code
        } else {
            return ResponseEntity.status(500).body(-1); // Failure status code
        }
    }
}
