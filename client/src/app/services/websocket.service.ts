import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private socket!: WebSocket;  // Use definite assignment operator
  private updatesSubject = new Subject<any>();

  // Connect to WebSocket and return the WebSocket instance
  connect(url: string): WebSocket {
    this.socket = new WebSocket(url);

    // On receiving a message from WebSocket
    this.socket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      this.updatesSubject.next(data);  // Emit the data to subscribers
    };

    // Error handling
    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    // Handle WebSocket close event
    this.socket.onclose = () => {
      console.log('WebSocket connection closed');
    };

    return this.socket;  // Return the WebSocket instance
  }

  // Get updates as an observable for subscribers
  getUpdates() {
    return this.updatesSubject.asObservable();
  }

  // Disconnect WebSocket
  disconnect() {
    if (this.socket) {
      this.socket.close();
    }
  }
}
