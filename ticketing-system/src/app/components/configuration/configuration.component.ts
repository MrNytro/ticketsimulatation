import { Component } from '@angular/core';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent {
  totalTickets: number = 100;
  releaseRate: number = 10;
  maxCapacity: number = 50;
  customers: { name: string; isVIP: boolean }[] = [];

  addCustomer(name: string, isVIP: boolean, customerNameInput: HTMLInputElement, isVIPInput: HTMLInputElement) {
    this.customers.push({ name, isVIP });

    // Clear input fields after adding the customer
    customerNameInput.value = '';
    isVIPInput.checked = false;
  }

  removeCustomer(index: number) {
    this.customers.splice(index, 1);
  }

  startSystem() {
    console.log('System started with configuration:', {
      totalTickets: this.totalTickets,
      releaseRate: this.releaseRate,
      maxCapacity: this.maxCapacity,
      customers: this.customers
    });
  }

  stopSystem() {
    console.log('System stopped');
  }
}
