 
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
 
@Injectable({
  providedIn: 'root'
})
export class AdminService {
 
  private apiUrl = 'http://localhost:8099';
  private apiUrl1 = 'http://localhost:8099/analytics';
 
  constructor(private http: HttpClient) {}
 
  
  addCity(cityData: { cityName: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/city`, cityData);
  }
 
  
  addTheatre(theatreData: { thName: string; cityId: number }): Observable<any> {
    return this.http.post(`${this.apiUrl}/theatre`, theatreData);
  }
 
 
  addMovie(movieData: { movieName: string; genre: string; rating: number ; url: string;trailer:string; about:string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/movies`, movieData);
  }
  addShow(data: { price: number; startTime: string; theatreId: number; movieId: number }): Observable<any> {
    return this.http.post(`${this.apiUrl}/shows`, data);
}
getAnalytics(cityId: number, movieName: string): Observable<any> {
  return this.http.get(`${this.apiUrl1}?cityId=${cityId}&movieName=${movieName}`);
}
addCasts(data: { movieName: string; casts: { name: string; url: string }[] }): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/casts`, data);
}
getAllAnalytics(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/allanalytics`);
 
}
getCities(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/city`);
}
}