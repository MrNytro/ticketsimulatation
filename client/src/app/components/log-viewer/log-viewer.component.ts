import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-log-viewer',
  templateUrl: './log-viewer.component.html',
  styleUrls: ['./log-viewer.component.css']
})
export class LogViewerComponent {
  @Input() logs: string[] = []; // Bind logs from the parent or other components
}
