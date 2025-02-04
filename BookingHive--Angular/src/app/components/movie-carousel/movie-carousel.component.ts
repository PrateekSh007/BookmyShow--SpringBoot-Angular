import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-movie-carousel',
  templateUrl: './movie-carousel.component.html',
  styleUrls: ['./movie-carousel.component.scss']
})
export class MovieCarouselComponent {
  @Input() movies: any[] = [];
  @Output() movieSelected = new EventEmitter<number>();
}
