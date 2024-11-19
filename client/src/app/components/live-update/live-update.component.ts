import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { WebSocketService } from '../../services/websocket.service'; // Import WebSocketService
import { Color, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-live-update',
  templateUrl: './live-update.component.html',
  styleUrls: ['./live-update.component.css'],
})
export class LiveUpdateComponent implements OnInit, OnDestroy {
  totalTickets: number = 0;
  ticketsAvailable: number = 0;
  liveUpdates: string[] = [];
  chartDimensions: [number, number] = [700, 400];  // Fixed chart dimensions
  colorScheme: Color = {
    name: 'Custom Scheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };
  chartData = [
    { name: 'Event 1', value: 30 },
    { name: 'Event 2', value: 70 }
  ];

  @Output() logEvent = new EventEmitter<string>();
  private socket!: WebSocket;  // Use definite assignment assertion

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    // Connect to WebSocket for live updates
    this.socket = this.webSocketService.connect('ws://localhost:8080/api/live/updates');
    
    // Handle incoming messages from WebSocket
    this.socket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      this.totalTickets = data.totalTickets;
      this.ticketsAvailable = data.ticketsAvailable;
      this.liveUpdates = data.logs;
      this.chartData = data.chartData || this.chartData; // Update chart data if available
      this.emitLog('Live update received');
    };

    // Handle WebSocket errors
    this.socket.onerror = (error) => {
      console.error('WebSocket Error:', error);
      this.emitLog('WebSocket connection error');
    };
  }

  emitLog(message: string) {
    this.logEvent.emit(message);  // Emit the log event with a string message
  }

  ngOnDestroy() {
    // Close the WebSocket connection when the component is destroyed
    if (this.socket) {
      this.socket.close();
    }
  }
}
