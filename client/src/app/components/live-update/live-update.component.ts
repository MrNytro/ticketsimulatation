import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subscription, interval } from 'rxjs';
import { TicketingConfiguration } from '../../models/ticketing-configuration.model'; // Import the model
import { Color, ScaleType } from '@swimlane/ngx-charts'; // Import for chart functionality
import { StartService } from '../../services/app-start.service'; // Import the StartService

@Component({
  selector: 'app-live-update',
  templateUrl: './live-update.component.html',
  styleUrls: ['./live-update.component.css'],
})
export class LiveUpdateComponent implements OnInit, OnDestroy {
  // Output event emitter to send log events to parent component
  @Output() logEvent = new EventEmitter<string>();

  totalTickets: number = 0;
  ticketsAvailable: number = 0;
  liveUpdates: string[] = [];
  chartDimensions: [number, number] = [700, 400]; // Fixed chart dimensions
  chartData = [
    { name: 'Event 1', value: 30 },
    { name: 'Event 2', value: 70 },
  ];

  colorScheme: Color = {
    name: 'Custom Scheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA'],
  };

  private dataSubscription!: Subscription;

  constructor(private http: HttpClient, private startService: StartService) {}

  ngOnInit() {
    // Subscribe to hasStarted state
    this.startService.hasStarted$.subscribe((hasStarted) => {
      if (hasStarted) {
        // Only start fetching live updates if the system has started
        this.startLiveUpdates();
      } else {
        // Stop fetching live updates if the system is stopped
        this.stopLiveUpdates();
      }
    });
  }

  // Method to start fetching live updates (API calls)
  startLiveUpdates() {
    if (!this.dataSubscription || this.dataSubscription.closed) {
      this.dataSubscription = interval(1000).subscribe(() => {
        this.fetchLiveUpdates();
      });
    }
  }

  // Method to stop fetching live updates
  stopLiveUpdates() {
    if (this.dataSubscription && !this.dataSubscription.closed) {
      this.dataSubscription.unsubscribe();
    }
  }

  // Method to fetch live updates from the backend API
  fetchLiveUpdates() {
    this.http.get<TicketingConfiguration>('http://localhost:8080/tickets/live-updates').subscribe(
      (data) => {
        this.totalTickets = data.totalTickets;
        this.ticketsAvailable = data.availableTickets;
        
        // Update chart data
        this.chartData = [
          { name: 'Total Tickets', value: data.totalTickets },
          { name: 'Available Tickets', value: data.availableTickets },
        ];

        // Emit log update to parent component
        this.logEvent.emit(`Update received: ${JSON.stringify(data)}`);

        // Log updates for the UI
        this.liveUpdates.push(`Update received: ${JSON.stringify(data)}`);
      },
      (error) => {
        console.error('Error fetching live updates:', error);
        this.logEvent.emit('Error fetching live updates'); // Emit error to parent
        this.liveUpdates.push('Error fetching live updates');
      }
    );
  }

  ngOnDestroy() {
    // Unsubscribe to stop the periodic API calls when the component is destroyed
    this.stopLiveUpdates();
  }
}
