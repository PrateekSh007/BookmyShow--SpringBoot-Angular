import { Component } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {
  seats: { id: string }[] = [];
  selectedSeat: string | null = null;

  constructor() {
    this.generateSeats();
  }

  // Generate seat IDs for a 10x10 layout
  generateSeats(): void {
    for (let row = 1; row <= 10; row++) {
      for (let col = 1; col <= 10; col++) {
        this.seats.push({ id: `${row}-${col}` });
      }
    }
  }

  // Select a seat
  selectSeat(seatId: string): void {
    this.selectedSeat = this.selectedSeat === seatId ? null : seatId;
  }

  // Book the selected seat
  bookSeat(): void {
    if (this.selectedSeat) {
      alert(`Seat ${this.selectedSeat} booked!`);
      console.log('Selected Seat ID:', this.selectedSeat);
      // You can send this.selectedSeat to your backend API here
    } else {
      alert('Please select a seat first!');
    }
  }
}
