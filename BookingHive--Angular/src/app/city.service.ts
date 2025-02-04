import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface City {
  id: number;
  cityName: string;
}

@Injectable({
  providedIn: 'root'
})
export class CityService {

  private apiUrl = 'http://localhost:8099/city';

  constructor(private http: HttpClient) { }

  getCities(): Observable<City[]> {
    return this.http.get<City[]>(this.apiUrl);
  }
}