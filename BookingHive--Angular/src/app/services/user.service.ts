import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private registerUrl = 'http://localhost:8099/users/register';
  private loginUrl = 'http://localhost:8099/users/login';
  private logoutUrl = 'http://localhost:8099/users/logout'; // Logout URL
  private analyticsUrl = 'http://localhost:8099/users/analytics';

  constructor(private http: HttpClient) {}

  registerUser(userData: any): Observable<any> {
    return this.http.post<any>(this.registerUrl, userData, { withCredentials: true });
  }

  loginUser(userData: any): Observable<any> {
    return this.http.post<any>(this.loginUrl, userData, { withCredentials: true });
  }

  logoutUser(): Observable<any> {
    return this.http.post(this.logoutUrl, {}, { withCredentials: true, responseType: 'text' }); // Expect text response
  }

  getUserAnalytics(): Observable<any> {
    return this.http.get(this.analyticsUrl, { withCredentials: true });
  }
}
