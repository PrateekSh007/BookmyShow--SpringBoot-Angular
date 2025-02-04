

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SeatService } from 'src/app/services/seat.service';
import { MatDialog } from '@angular/material/dialog';
import { PaymentDialogComponent } from '../payment-dialog/payment-dialog.component';
import { PriceService } from 'src/app/services/price.service';
import { MailService } from 'src/app/services/mail.service';
interface Seat {
  seatNo: string;
  occupied: boolean;
  blocked: boolean;
}

@Component({
  selector: 'app-seat-selection',
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.scss']
})
export class SeatSelectionComponent implements OnInit {
  timeout: any;
  bookingSuccess: boolean = false; 
  seats: Seat[][] = [];
  selectedSeat: string | null = null;
  theatreId: number = 0;
  showId: number = 0;
  cityId: number = 0;
  movieId: number = 0;
  isDialogOpen: boolean = false;
  blockDuration: number = 20 * 1000; // 20 seconds in milliseconds for testing
  price: number;

  constructor(
    private seatService: SeatService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private priceService: PriceService,
    private mailService: MailService
  ) {}

  ngOnInit() {
    if (!localStorage.getItem('signedIn')) {
      this.router.navigate(['/login']);
    } else {
      // Extract IDs from the URL
      this.cityId = +this.route.snapshot.paramMap.get('cityid')!;
      this.movieId = +this.route.snapshot.paramMap.get('id')!;
      this.theatreId = +this.route.snapshot.paramMap.get('theatreId')!;
      this.showId = +this.route.snapshot.paramMap.get('showId')!;
      
      this.fetchSeats();
    }
    this.price = this.priceService.getPrice();
  }

  fetchSeats() {
    this.seatService.getSeats(this.theatreId, this.showId).subscribe({
      next: (seatData: Seat[]) => {
        this.generateSeats(seatData);
        console.log(seatData);
      },
      error: (error) => {
        console.error('Error fetching seats:', error);
        alert('Error fetching seat data');
      }
    });
  }

  generateSeats(apiSeats: Seat[]) {
    const rows = 'ABCDEFGHIJ';
    this.seats = [];

    for (let i = 0; i < 10; i++) {
      const row: Seat[] = [];
      for (let j = 1; j <= 10; j++) {
        const seatNo = `${j}${rows[i]}`;
        let apiSeat = null;
        if (apiSeats !== null) {
          apiSeat = apiSeats.find((seat) => seat.seatNo === seatNo);
        }

        row.push({
          seatNo: seatNo,
          occupied: apiSeat ? apiSeat.occupied : false,
          blocked: apiSeat ? apiSeat.blocked : false,
        });
      }
      this.seats.push(row);
    }
  }

  selectSeat(seat: Seat) {
    if (!seat.occupied && !seat.blocked) {
      this.selectedSeat = this.selectedSeat === seat.seatNo ? null : seat.seatNo;
    }
  }

  bookSeat() {
    if (this.selectedSeat && !this.isDialogOpen) {
      this.isDialogOpen = true;

      this.seatService.blockSeat(this.selectedSeat, this.theatreId, this.showId).subscribe({
        next: (response) => {
          const seatId = response;
          console.log('Blocked seat ID:', seatId);
          const dialogRef = this.dialog.open(PaymentDialogComponent, {
            position: { left: '200px', bottom: '300px' },
            panelClass: 'centered-dialog',
          });

          dialogRef.afterClosed().subscribe({
            next: (result) => {
              this.isDialogOpen = false;
              if (result === 'confirmed') {
                this.seatService.markSeatAsOccupied(seatId).subscribe({
                  next: () => {
                    alert('Seat booked successfully!');
                    this.bookingSuccess = true;
                    this.seatService.booking(
                      seatId,
                      this.theatreId,
                      this.movieId,
                      Number(localStorage.getItem('userId'))
                    ).subscribe({
                      next: (bookingResponse) => {
                        console.log('Booking Response:', bookingResponse);

                        // Save booking data to localStorage
                        localStorage.setItem('bookingData', JSON.stringify(bookingResponse));
                        this.mailService.sendMail(bookingResponse.seatNo, this.theatreId, this.showId)
                          .subscribe({
                            next: (emailResponse) => {
                              console.log('Email sent successfully', emailResponse);
                            },
                            error: (emailError) => {
                              console.error('Error sending email', emailError);
                            }
                          });

                        const currentUrl = this.router.url;
                        const newUrl = `${currentUrl}/booking`;
                        this.router.navigateByUrl(newUrl);
                      },
                      error: (error) => {
                        console.error('Error booking seat:', error);
                        alert('Error booking seat');
                      }
                    });
                    this.fetchSeats();
                  },
                  error: (error) => {
                    console.error('Error marking seat as occupied:', error);
                    alert('Error marking seat as occupied');
                  }
                });
              } else {
                if (result === 'manualclose') {
                  this.timeout && clearTimeout(this.timeout);
                  this.seatService.deleteSeat(seatId).subscribe({
                    next: () => {
                      alert('Seat booking cancelled');
                      this.fetchSeats();
                    },
                    error: (error) => {
                      console.error('Error deleting seat:', error);
                      alert('Error deleting seat');
                    }
                  });
                }
              }
            },
            error: (error) => {
              this.isDialogOpen = false;
              console.error('Error opening dialog:', error);
              alert('Error opening payment dialog');
            }
          });

      
          this.timeout = setTimeout(() => {
            if (!this.bookingSuccess) {
              this.unblockSeat(seatId);
            }
          }, this.blockDuration);
        },
        error: (error) => {
          this.isDialogOpen = false;
          console.error('Error blocking seat:', error);
          if (error.status === 409) {
            alert('Sorry, the seat is already blocked or occupied. Please try another seat.');
            this.fetchSeats(); 
          } else {
            alert('Error blocking seat');
          }
        }
      });
    } else {
      alert('Please select a seat first!');
    }
  }

  unblockSeat(seatId: number) {
    this.seatService.deleteSeat(seatId).subscribe({
      next: () => {
        alert('The seat has been unblocked due to timeout.');
        this.fetchSeats();
        this.dialog.closeAll();
      },
      error: (error) => {
        console.error('Error unblocking seat:', error);
        alert('Error unblocking seat');
      }
    });
  }
}
