import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private ws: WebSocket;
  private messagesSubject: Subject<string> = new Subject<string>();

  constructor() {
    this.ws = new WebSocket('ws://localhost:8080/socket');  // Adjust URL to your WebSocket endpoint
    this.ws.onmessage = (event) => {
      this.messagesSubject.next(event.data);
    };
  }

  getMessages() {
    return this.messagesSubject.asObservable();
  }

  sendMessage(message: string) {
    this.ws.send(message);
  }
}
