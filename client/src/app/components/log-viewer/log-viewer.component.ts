import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-log-viewer',
  templateUrl: './log-viewer.component.html',
  styleUrls: ['./log-viewer.component.css']
})
export class LogViewerComponent implements OnInit, OnDestroy {
  @Input() logs: string[] = [];  // Accept logs as input from the parent component
  private logSubscription!: Subscription; // To manage periodic log updates

  constructor(private http: HttpClient) {}

  ngOnInit() {
    // Fetch logs every second (adjust interval as needed)
    this.logSubscription = interval(1000).subscribe(() => {
      this.fetchLogs();
    });
  }

  ngOnDestroy() {
    // Unsubscribe when component is destroyed to prevent memory leaks
    if (this.logSubscription) {
      this.logSubscription.unsubscribe();
    }
  }

  // Fetch the logs from the backend
  private fetchLogs() {
    this.http.get<string[]>('http://localhost:8080/tickets/logs').subscribe((logs) => {
      this.logs = logs;  // Update the logs list
    });
  }
}
