import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subscription, interval } from 'rxjs';
import { TicketingConfiguration } from '../../models/ticketing-configuration.model';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { StartService } from '../../services/app-start.service';
import { curveLinear } from 'd3-shape';

@Component({
  selector: 'app-live-update',
  templateUrl: './live-update.component.html',
  styleUrls: ['./live-update.component.css'],
})
export class LiveUpdateComponent implements OnInit, OnDestroy {
  @Output() logEvent = new EventEmitter<string>();

  totalTickets: number = 0;
  ticketsAvailable: number = 0;
  chartDimensions: [number, number] = [700, 400];
  curve = curveLinear;

  timeSeriesChartData: any[] = [
    { 
      name: 'Total Tickets', 
      series: [] 
    },
    { 
      name: 'Available Tickets', 
      series: [] 
    }
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
    this.startService.hasStarted$.subscribe((hasStarted) => {
      if (hasStarted) {
        this.startLiveUpdates();
      } else {
        this.stopLiveUpdates();
      }
    });
  }

  startLiveUpdates() {
    if (!this.dataSubscription || this.dataSubscription.closed) {
      this.dataSubscription = interval(1000).subscribe(() => {
        this.fetchLiveUpdates();
      });
    }
  }

  stopLiveUpdates() {
    if (this.dataSubscription && !this.dataSubscription.closed) {
      this.dataSubscription.unsubscribe();
    }
  }

  fetchLiveUpdates() {
    this.http.get<TicketingConfiguration>('http://localhost:8080/tickets/live-updates').subscribe(
      (data) => {
        this.totalTickets = data.totalTickets;
        this.ticketsAvailable = data.availableTickets;
        
        const currentTime = new Date().toLocaleTimeString();

        this.timeSeriesChartData[0].series.push({
          name: currentTime,
          value: data.totalTickets
        });

        this.timeSeriesChartData[1].series.push({
          name: currentTime,
          value: data.availableTickets
        });

        const maxDataPoints = 20;
        this.timeSeriesChartData.forEach(series => {
          if (series.series.length > maxDataPoints) {
            series.series.shift();
          }
        });

        this.timeSeriesChartData = [...this.timeSeriesChartData];

        console.log(`Update received: ${JSON.stringify(data)}`);
      },
      (error) => {
        console.error('Error fetching live updates:', error);
      }
    );
  }

  ngOnDestroy() {
    this.stopLiveUpdates();
  }
}