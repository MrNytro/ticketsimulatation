import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-live-update',
  templateUrl: './live-update.component.html',
  styleUrls: ['./live-update.component.css']
})
export class LiveUpdateComponent implements OnInit, OnDestroy {
  totalTickets: number = 20;  // Initial total tickets
  ticketsAvailable: number = 20; // Initial tickets available
  timeElapsed: number = 0;  // Time in seconds
  liveUpdates: string[] = [];

  // Chart Data for Tickets Available and Total Tickets
  chartData = [
    {
      name: 'Tickets Available',
      series: [{ name: '0', value: this.ticketsAvailable }]
    },
    {
      name: 'Total Tickets',
      series: [{ name: '0', value: this.totalTickets }]
    }
  ];

  private interval: any;

  ngOnInit() {
    // Simulate the ticket removal process every second
    this.interval = setInterval(() => {
      this.timeElapsed++; // Increment time
      if (this.ticketsAvailable > 0) {
        this.ticketsAvailable--;  // Remove 1 ticket every second
      }
      
      // Update chart data with the new value of tickets available and total tickets
      this.chartData[0].series.push({ name: `${this.timeElapsed}`, value: this.ticketsAvailable });
      this.chartData[1].series.push({ name: `${this.timeElapsed}`, value: this.totalTickets });
    }, 1000);  // Update every second
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval); // Clean up the interval when the component is destroyed
    }
  }

  addUpdate(message: string) {
    this.liveUpdates.push(message); // Add live updates to the log
  }
}
