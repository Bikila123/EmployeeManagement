import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiServerUrl = environment.apiBaseUrl;
  constructor(
    private http:HttpClient
  ) { }

  getNumberOfUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiServerUrl}/AdminDashboard/getNumberOfUsers`);
  }
  getNumberOfLockedUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiServerUrl}/AdminDashboard/getNumberOfLockedUsers`);
  }
  getNumberOfActiveUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiServerUrl}/AdminDashboard/getNumberOfActiveUsers`);
  }
  getNumberOfOnlineUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiServerUrl}/AdminDashboard/getNumberOfOnlineUsers`);
  }
}
