import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = 'http://localhost:8099/casts'; // API URL for the casts

  constructor(private http: HttpClient) {}

  // Fetch movie details based on cityId (unchanged from your existing code)
  getMovies(cityId: any): Observable<any[]> {
    const url = `http://localhost:8099/theatre/movies/${cityId}`; 
    console.log(`Fetching movies for cityId: ${cityId}`);
    return this.http.get<any[]>(url);
  }

  // Fetch cast details based on movieId
  getCasts(movieId: number): Observable<any> {
    const url = `${this.apiUrl}/${movieId}`;  // API to fetch casts based on movieId
    console.log(`Fetching cast details for movieId: ${movieId}`);
    return this.http.get<any>(url);
  }
}



