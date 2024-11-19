# README FOR THE SERVER SIDE CODE

# PROJECT STRUCTRE
```
server/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── server/
│   │   │               ├── ServerApplication.java  // Main entry point (Spring Boot Application)
│   │   │               ├── config/
│   │   │               │   └── AppConfig.java  // Configuration classes (e.g., for system settings)
│   │   │               │   └── WebSocketConfig.java.java
│   │   │               ├── websocket/
│   │   │               │   └── CustomWebSocketHandler.java
│   │   │               ├── controller/
│   │   │               │   ├── TicketController.java  // REST controller for ticket management
│   │   │               │   └── ConfigController.java  // Controller for handling configuration requests
│   │   │               ├── model/
│   │   │               │   ├── Ticket.java  // Ticket model for ticket objects
│   │   │               │   ├── Customer.java  // Customer model
│   │   │               │   ├── Vendor.java  // Vendor model
│   │   │               │   └── Configuration.java  // Configuration settings model
│   │   │               ├── service/
│   │   │               │   ├── TicketService.java  // Service to manage tickets
│   │   │               │   ├── CustomerService.java  // Service for customer-related operations
│   │   │               │   └── VendorService.java  // Service for vendor-related operations
│   │   │               ├── repository/
│   │   │               │   ├── TicketRepository.java  // Repository for ticket data
│   │   │               │   ├── CustomerRepository.java  // Repository for customer data
│   │   │               │   └── VendorRepository.java  // Repository for vendor data
│   │   │               └── util/
│   │   │                   └── TicketPool.java  // Shared ticket pool with synchronized access
│   │   └── resources/
│   │       ├── application.properties  // Spring Boot configuration file (e.g., database, server settings)
│   │       └── static/
│   │           └── index.html  // Static HTML file, if needed for server testing (optional)
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── server/
│                       └── ServerApplicationTests.java  // Unit tests for server application
├── .gitignore
├── build.gradle  // Gradle build file
├── settings.gradle  // Gradle settings file
└── README.md  // Project documentation
```