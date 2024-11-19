import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { WebSocketService } from '../../services/websocket.service'; // Import WebSocketService

@Component({
  selector: 'app-log-viewer',
  templateUrl: './log-viewer.component.html',
  styleUrls: ['./log-viewer.component.css']
})
export class LogViewerComponent implements OnInit, OnDestroy {
  @Input() logs: string[] = []; // Bind logs from the parent or other components
  private socket!: WebSocket;

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    // Connect to WebSocket for log streaming
    this.socket = this.webSocketService.connect('ws://localhost:8080/api/logs/stream');
    
    // Handle incoming messages (logs) from WebSocket
    this.socket.onmessage = (event) => {
      const logMessage = JSON.parse(event.data);
      this.logs.unshift(logMessage);  // Add new logs to the top of the list
    };

    // Handle WebSocket errors
    this.socket.onerror = (error) => {
      console.error('WebSocket Error:', error);
    };
  }

  ngOnDestroy() {
    // Close the WebSocket connection when the component is destroyed
    if (this.socket) {
      this.socket.close();
    }
  }
}
