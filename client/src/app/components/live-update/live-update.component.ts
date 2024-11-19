import { Component, OnInit, OnDestroy, Output, EventEmitter, HostListener, ElementRef } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-live-update',
  templateUrl: './live-update.component.html',
  styleUrls: ['./live-update.component.css']
})
export class LiveUpdateComponent implements OnInit, OnDestroy {
  totalTickets: number = 20;
  ticketsAvailable: number = 20;
  timeElapsed: number = 0;
  liveUpdates: string[] = [];
  @Output() logEvent = new EventEmitter<string>();

  chartDimensions: [number, number] = [700, 200]; // Initial dimensions

  colorScheme: Color = {
    name: 'cool',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#A10A28']
  };

  chartData: any[] = [];
  private interval: any;

  constructor(private elementRef: ElementRef) {
    this.initializeChartData();
  }

  private initializeChartData() {
    this.chartData = [
      {
        name: 'Tickets Available',
        series: [{ name: '0', value: this.ticketsAvailable }]
      },
      {
        name: 'Total Tickets',
        series: [{ name: '0', value: this.totalTickets }]
      }
    ];
  }

  @HostListener('window:resize')
  onResize() {
    this.updateChartDimensions();
  }

  private updateChartDimensions() {
    const container = this.elementRef.nativeElement.querySelector('.chart-container');
    if (container) {
      this.chartDimensions = [
        container.clientWidth,
        container.clientHeight
      ];
    }
  }

  ngOnInit() {
    setTimeout(() => this.updateChartDimensions(), 0);
    
    this.interval = setInterval(() => {
      this.timeElapsed++;
      
      if (this.ticketsAvailable > 0) {
        this.ticketsAvailable--;
        this.logEvent.emit('*Ticket bought Successfully');
        
        // Create new array references for change detection
        this.chartData = this.chartData.map(series => ({
          ...series,
          series: [...series.series, {
            name: this.timeElapsed.toString(),
            value: series.name === 'Tickets Available' ? this.ticketsAvailable : this.totalTickets
          }]
        }));
      }
    }, 1000);
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }
}