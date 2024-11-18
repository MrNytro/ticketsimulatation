import { Component } from '@angular/core';

@Component({
  selector: 'app-live-update',
  templateUrl: './live-update.component.html',
  styleUrls: ['./live-update.component.css']
})
export class LiveUpdateComponent {
  liveUpdates: string[] = [];

  addUpdate(message: string) {
    this.liveUpdates.push(message);
  }
}
