import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Ticketing System';

  // Array to store logs
  logs: string[] = [];

  // Method to add logs
  addLog(log: string): void {
    this.logs.push(log);

    // Ensure the log viewer starts at the bottom
    setTimeout(() => {
      const logViewer = document.querySelector('.log-viewer ul');
      if (logViewer) {
        logViewer.scrollTop = logViewer.scrollHeight;
      }
    }, 0);
  }
}
