import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  private socket: WebSocket | undefined;
  private updatesSubject = new Subject<any>();

  connect() {
    this.socket = new WebSocket('ws://localhost:8080/ws');
    this.socket.onmessage = (event) => {
      this.updatesSubject.next(JSON.parse(event.data));
    };
  }

  getUpdates() {
    return this.updatesSubject.asObservable();
  }

  disconnect() {
    if (this.socket) {
      this.socket.close();
    }
  }
}
