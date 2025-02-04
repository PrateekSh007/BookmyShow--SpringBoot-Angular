import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SeatService {
  private apiUrl = 'http://localhost:8099/seat';
  private bookingApiUrl = 'http://localhost:8099/bookings';

  constructor(private http: HttpClient) {}

  getSeats(theatreId: number, showId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${theatreId}/${showId}`, { withCredentials: true });
  }

  blockSeat(seatNo: string, theatreId: number, showId: number): Observable<number> {
    const seatModel = { seatNo, theatreId, showId, occupied: false, blocked: true };
    return this.http.post<number>(this.apiUrl, seatModel, { withCredentials: true });
  }
  markSeatAsOccupied(seatId: number): Observable<string> {
    return this.http.put(`${this.apiUrl}/${seatId}/occupy`, {}, { withCredentials: true, responseType: 'text' });
  }

  deleteSeat(seatId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${seatId}`, { withCredentials: true });
  }

  booking(seatId: number, theatreId: number, movieId: number, userId: number): Observable<any> {
    const body = {
      status: 1, // 1 means "Booked"
      movieId: movieId,
      theatreId: theatreId,
      seatId: seatId
    };
    return this.http.post<any>(this.bookingApiUrl, body, { withCredentials: true });
  }
}