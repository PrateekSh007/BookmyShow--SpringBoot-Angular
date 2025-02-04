import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TheatreService } from '../../services/theatre.service';
import { DatePipe } from '@angular/common';
import { MovieService } from 'src/app/services/movie.service';
import { PriceService } from 'src/app/services/price.service';

@Component({
  selector: 'app-theatre',
  templateUrl: './theatre.component.html',
  styleUrls: ['./theatre.component.scss'],
  providers: [DatePipe]
})
export class TheatreComponent implements OnInit {
  theatres: any[] = [];
  theatreShows: any[] = [];
  cityId: number = 0;
  movieId: number = 0;
  movieDetails: any;

  constructor(
    private theatreService: TheatreService,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private movieService: MovieService,
    private priceService: PriceService
  ) {}

  ngOnInit(): void {
    this.cityId = +this.route.snapshot.paramMap.get('cityid')!;
    this.movieId = +this.route.snapshot.paramMap.get('id')!;

    this.fetchTheatres();
    this.fetchMovieDetails();
  }

  fetchTheatres() {
    this.theatreService.getTheatres(this.cityId, this.movieId).subscribe({
      next: (response) => {
        this.theatres = response;
        this.fetchAllShows();
      },
      error: (error) => {
        console.error('Error fetching theatres:', error);
      }
    });
  }

  fetchAllShows() {
    const theatreShowRequests = this.theatres.map(theatre => 
      this.theatreService.getShows(theatre.theatreId, this.movieId).toPromise()
        .then(shows => {
          return { theatre, shows };
        })
    );

    Promise.all(theatreShowRequests)
      .then(results => {
        this.theatreShows = results;
      })
      .catch(error => {
        console.error('Error fetching shows:', error);
      });
  }

  selectShow(showId: number, price: number) {
    console.log(`Show ID ${showId} selected.`);
    const currentUrl = this.router.url;
    const newUrl = `${currentUrl}/${this.theatreShows.find(ts => ts.shows.some(show => show.id === showId)).theatre.theatreId}/show/${showId}/seats`;
    this.priceService.setPrice(price);
    this.router.navigateByUrl(newUrl);
  }

  formatDate(startTime: string): string {
    return this.datePipe.transform(startTime, 'fullDate') + ' ' + this.datePipe.transform(startTime, 'shortTime');
  }

  fetchMovieDetails(): void {
    this.movieService.getMovies(this.cityId).subscribe((movies) => {
      this.movieDetails = movies.find(movie => movie.movieId == this.movieId) || {
        id: this.movieId,
        title: `Movie ${this.movieId}`,
        description: 'This is a movie about magic and adventure.',
        genre: 'Fiction',
        language: 'English',
        rating: 4.5,
        image: 'https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_FMjpg_UY2048_.jpg'
      };
    });
  }
}