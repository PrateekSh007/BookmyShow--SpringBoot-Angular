import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ads',
  templateUrl: './ads.component.html',
  styleUrls: ['./ads.component.scss']
})
export class AdsComponent implements OnInit {
  ads: string[] = [
    'https://cdn.celluloidjunkie.com/wp-content/uploads/2023/04/18063713/IMAX.png', // Path to INOX image
    'https://upload.wikimedia.org/wikipedia/commons/0/0e/PVR_INOX_Logo_After_Merger.png', // Path to Cinepolis image
    'https://smartads.in/resources/assets/uploads/product_group/cinema/cinepolis-cinemas.webp' // Path to Wave image
  ];
  currentIndex: number = 0;
  interval: any;

  ngOnInit(): void {
    this.startRotation();
  }

  startRotation(): void {
    this.interval = setInterval(() => {
      this.nextAd();
    }, 3000); // Change ad every 3 seconds
  }

  nextAd(): void {
    this.currentIndex = (this.currentIndex + 1) % this.ads.length;
  }

  goToAd(index: number): void {
    this.currentIndex = index;
    clearInterval(this.interval);
    this.startRotation();
  }
}