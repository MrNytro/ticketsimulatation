import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StartService {
  private hasStartedSource = new BehaviorSubject<boolean>(false);  // Initialize as false
  hasStarted$ = this.hasStartedSource.asObservable();  // Observable to subscribe to the value

  // Method to update the hasStarted state
  setStarted(state: boolean) {
    this.hasStartedSource.next(state);
  }
}
