import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TheatreService {
  private baseUrl = 'http://localhost:8099';

  constructor(private http: HttpClient) {}

  getTheatres(cityId: number, movieId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/theatre/theatres?cityId=${cityId}&movieId=${movieId}`);
  }

  getShows(theatreId: number, movieId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/shows/shows?theatreId=${theatreId}&movieId=${movieId}`);
  }
}