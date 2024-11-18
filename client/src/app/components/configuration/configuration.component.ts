import { Component } from '@angular/core';

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
      console.log('Starting system with configuration:', config);
      // Call API to start the system
    } else {
      alert('Please fill out all fields before starting the system.');
    }
  }

  endSystem(): void {
    console.log('Ending system...');
    // Call API to end the system
  }
}
