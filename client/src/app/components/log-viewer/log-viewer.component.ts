import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';
import { StartService } from '../../services/app-start.service'; // Import StartService

@Component({
  selector: 'app-log-viewer',
  templateUrl: './log-viewer.component.html',
  styleUrls: ['./log-viewer.component.css']
})
export class LogViewerComponent implements OnInit, OnDestroy {
  logs: string[] = [];  // Array to hold the logs
  private logSubscription!: Subscription;

  constructor(private http: HttpClient, private cdRef: ChangeDetectorRef, private startService: StartService) {}

  ngOnInit() {
    // Subscribe to the hasStarted state
    this.startService.hasStarted$.subscribe((hasStarted) => {
      if (hasStarted) {
        // Start fetching logs if the system has started
        this.startLogFetching();
      } else {
        // Stop fetching logs if the system is stopped
        this.stopLogFetching();
      }
    });
  }

  ngOnDestroy() {
    // Unsubscribe to avoid memory leaks
    this.stopLogFetching();
  }

  // Method to start fetching logs every second
  private startLogFetching() {
    if (!this.logSubscription || this.logSubscription.closed) {
      this.logSubscription = interval(1000).subscribe(() => {
        this.fetchLatestLog();
      });
    }
  }

  // Method to stop fetching logs
  private stopLogFetching() {
    if (this.logSubscription && !this.logSubscription.closed) {
      this.logSubscription.unsubscribe();
    }
  }

  // Fetch the latest log from the backend
  private fetchLatestLog() {
    this.http.get('http://localhost:8080/tickets/latest-log', { responseType: 'text' }).subscribe(
      (log) => {
        console.log('Fetched latest log:', log);
        
        // Split log into customer and vendor parts
        const logParts = log.split('Vendor');  // Split by the word 'Vendor'

        // Add customer log part (always the first part)
        this.logs.push(logParts[0].trim());  // Trim to remove leading/trailing whitespace

        // Add vendor log part (only if it exists)
        if (logParts.length > 1) {
          this.logs.push('Vendor ' + logParts[1].trim());  // Add 'Vendor' back and trim
        }

        // If there are more than 20 logs, remove the oldest one
        if (this.logs.length > 20) {
          this.logs.shift();
        }

        // Trigger change detection manually to update the UI
        this.cdRef.detectChanges();

        // Scroll to the bottom of the log viewer
        setTimeout(() => {
          const logViewer = document.querySelector('.log-viewer');
          if (logViewer) {
            logViewer.scrollTop = logViewer.scrollHeight;
          }
        });
      },
      (error) => {
        console.error('Error fetching latest log:', error);
      }
    );
  }
}
