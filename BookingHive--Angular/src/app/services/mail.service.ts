import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MailService {
  private apiUrl = 'http://localhost:8099/email';

  constructor(private http: HttpClient) {}

  sendMail(seatNo: string, theatreId: number, showId: number): Observable<any> {

    const bookingDataString = localStorage.getItem('bookingData');
    if (!bookingDataString) {
      throw new Error('No booking data found in localStorage.');
    }
    const bookingData = JSON.parse(bookingDataString);

    const bookingTime = new Date(bookingData.timestamp).toLocaleString();


    const emailBody = `
      <div style="font-family: Arial, sans-serif; line-height: 1.6;">
        <h2 style="color: #2E86C1;">Booking Confirmation</h2>
        <p>Dear Customer,</p>
        <p>Thank you for your booking! Below are your booking details:</p>
        <ul>
          <li><strong>Movie Name:</strong> ${bookingData.movieName}</li>
          <li><strong>Theatre:</strong> ${bookingData.theatreName}</li>
          <li><strong>Seat Number:</strong> ${bookingData.seatNo}</li>
          <li><strong>Booking Time:</strong> ${bookingTime}</li>
        </ul>
        <p>We hope you enjoy your movie!</p>
        <p>Best regards,<br>BookingHive Team, Third Eye</p>
      </div>
    `;

    // Build the email model with subject and the formatted body
    const emailModel = {
      sub: "Booking Confirmation",
      body: emailBody
    };

    // Send the email model to the API endpoint
    return this.http.post(this.apiUrl, emailModel, { withCredentials: true, responseType: 'text' });
  }
  
}
