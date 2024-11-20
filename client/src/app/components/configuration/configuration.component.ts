import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StartService } from '../../services/app-start.service';  // Import the service

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css'],
})
export class ConfigurationComponent {
  totalTickets: number | null = null;
  ticketReleaseRate: number | null = null;
  customerRetrievalRate: number | null = null;
  maxTicketCapacity: number | null = null;

  constructor(private http: HttpClient, private startService: StartService) {}

  startSystem(): void {
    if (
      this.totalTickets &&
      this.ticketReleaseRate &&
      this.customerRetrievalRate &&
      this.maxTicketCapacity
    ) {
      const config = {
        totalTickets: this.totalTickets,
        ticketReleaseRate: this.ticketReleaseRate,
        customerRetrievalRate: this.customerRetrievalRate,
        maxTicketCapacity: this.maxTicketCapacity,
      };

      console.log('Sending configuration:', config); // Log the config before sending it

      // Change the URL to explicitly point to the backend server
      this.http.post<number>('http://localhost:8080/api/config/start', config).subscribe({
        next: (responseCode) => {
          // Handle the integer status code returned by the backend
          switch (responseCode) {
            case 1:
              alert('System started successfully!');
              this.startService.setStarted(true);  // Set the system as started
              break;
            case -1:
              alert('Error: Invalid configuration.');
              break;
            case -2:
              alert('Error: System terminate request.');
              break;
            default:
              alert('Unknown status code received.');
              break;
          }
        },
        error: (err) => {
          console.error('Error starting system:', err); // Log the error response
          alert('Error starting system');
        },
      });
    } else {
      alert('Please fill out all fields before starting the system.');
    }
  }

  endSystem(): void {
    // Change the URL to explicitly point to the backend server
    this.http.post<number>('http://localhost:8080/api/config/stop', {}).subscribe({
      next: (responseCode) => {
        // Handle the integer status code returned by the backend
        if (responseCode === 1) {
          console.log('System stopped successfully');
          alert('System stopped successfully!');
          this.startService.setStarted(false);  // Set the system as stopped
        } else {
          console.error('Error stopping system, received status code:', responseCode);
          alert('Error stopping system.');
        }
      },
      error: (err) => {
        console.error('Error stopping system:', err);
        alert('Error stopping system.');
      },
    });
  }
}
