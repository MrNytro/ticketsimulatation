import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_BASE_URL } from '../app.config';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  sendConfiguration(config: any) {
    return this.http.post(`${API_BASE_URL}/configure`, config);
  }

  startSystem() {
    return this.http.post(`${API_BASE_URL}/start`, {});
  }

  endSystem() {
    return this.http.post(`${API_BASE_URL}/stop`, {});
  }
}
