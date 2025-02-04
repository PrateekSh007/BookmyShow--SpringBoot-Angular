import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  bookingData: any;
  cityName: string = '';
  cityId: string = '';

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.cityName = this.route.snapshot.paramMap.get('city') || 'Bengaluru';
    this.cityId = this.route.snapshot.paramMap.get('cityid') || '1';

    const storedData = localStorage.getItem('bookingData');
    if (storedData) {
      this.bookingData = JSON.parse(storedData);
      this.bookingData.timestamp = this.formatTimestamp(this.bookingData.timestamp);
    } else {
      this.redirectToHome();
    }
  }

  formatTimestamp(timestamp: string): string {
    const date = new Date(timestamp);
    return date.toLocaleString();
  }

  redirectToHome() {
    localStorage.removeItem('bookingData');
    this.router.navigate([`/home/${this.cityName}/${this.cityId}`]);
  }

  downloadTicket() {
    const ticketElement = document.querySelector('.ticket-card') as HTMLElement;

    html2canvas(ticketElement).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const imgWidth = 210; 
      const imgHeight = (canvas.height * imgWidth) / canvas.width;

      pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
      pdf.save('ticket.pdf');
    });
  }
}