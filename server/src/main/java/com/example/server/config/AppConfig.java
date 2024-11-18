package com.example.server.config;

import com.example.server.model.TicketingConfiguration;  // Import the correct model
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    // Define the Configuration bean with default values for the ticketing system
    @Bean
    public TicketingConfiguration ticketingConfiguration() {  // Use TicketingConfiguration, not Configuration
        TicketingConfiguration configuration = new TicketingConfiguration();  // Instantiate the concrete class
        configuration.setTotalTickets(100); // Set default total tickets, this can be updated later
        configuration.setMaxTicketCapacity(100); // Set the max capacity of the ticket pool
        configuration.setTicketReleaseRate(10); // Set default ticket release rate per second
        configuration.setCustomerRetrievalRate(5); // Set default customer retrieval rate per second
        return configuration;
    }

    // CORS configuration to allow the frontend to communicate with the backend
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow Angular frontend at localhost:4200 to make requests
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")  // Adjust the URL if needed
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
