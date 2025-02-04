import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.scss']
})
export class MovieDetailsComponent implements OnInit {
  movieId: number = 0;
  movieDetails: any;
  city: string = '';
  cityid: number;
  casts: any[] = []; 

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private router: Router
  ) {}

  ngOnInit(): void {
   
    this.city = this.route.snapshot.paramMap.get('city')?.toLowerCase() || '';
    this.movieId = +this.route.snapshot.paramMap.get('id')!;
    this.cityid = +this.route.snapshot.paramMap.get('cityid')!;
    
  
    this.fetchMovieDetails();
  }

  fetchMovieDetails(): void {
    this.movieService.getMovies(this.cityid).subscribe((movies) => {
      console.log(movies);
      this.movieDetails = movies.find(movie => movie.movieId == this.movieId) || {
        id: this.movieId,
        title: `Movie ${this.movieId}`,
        description: 'This is a movie about magic and adventure.',
        genre: 'Fiction',
        language: 'English',
        rating: 4.5,
        image: 'https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_FMjpg_UY2048_.jpg'
      };
      console.log('Movie details:', this.movieDetails);
      
     
      this.fetchCastDetails();
    });
  }

  fetchCastDetails(): void {
    this.movieService.getCasts(this.movieId).subscribe((castData) => {
      console.log('Cast details:', castData);
      this.casts = castData.casts || []; 
    });
  }

  bookTickets(): void {
    const signedIn = localStorage.getItem('signedIn');  

    if (signedIn && signedIn === 'true') {
    
      const currentUrl = this.router.url;
      const newUrl = `${currentUrl}/theatre`;
      this.router.navigateByUrl(newUrl);
    } else {
    
      this.router.navigate(['/signin']);
    }
  }

  playTrailer(url: any): void {
    window.open(url, '_blank');
  }
}
