import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from 'src/app/services/movie.service';
import { CityService } from 'src/app/city.service';
import { Location } from '@angular/common';
import { Observable } from 'rxjs';
import { tap } from 'rxjs';
interface City {
  id: number;
  cityName: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  private historyListener: any;
  movies: any[] = [];
  city: string = '';
  cityId: string = '';
  availableCities: City[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private movieService: MovieService,
    private cityService: CityService,
    private location: Location
  ) {}

  ngOnInit(): void {
    // history.pushState(null, '', location.href);
    // console.log('Pushed a new state');
    // window.onpopstate = () => {
    //   history.go(1);
    // };
  
    // Fetch cities from the API and then listen for route changes
    this.fetchCities().subscribe(() => {
      this.route.paramMap.subscribe(params => {
        this.city = params.get('city')?.toLowerCase() || '';
        this.cityId = params.get('cityid') || '';
  
        if (!this.isCityAvailable(this.city)) {
          this.router.navigate(['/select-city']);
          return;
        }
  
        // Fetch movies for the new city
        this.fetchMovies();
      });
    });
  }

  fetchCities(): Observable<City[]> {
    return this.cityService.getCities().pipe(
      tap((response) => {
        this.availableCities = response.map(city => ({
          id: city.id,
          cityName: city.cityName.toLowerCase()
        }));
        console.log('Available Cities:', this.availableCities); // Debug log
      })
    );
  }
  isCityAvailable(cityName: string): boolean {
    return this.availableCities.some(city => city.cityName === cityName);
  }

  fetchMovies(): void {
    // Fetch movies from the API
    this.movieService.getMovies(this.cityId).subscribe(
      (response) => {
        console.log('API Response:', response); // Debug log
        this.movies = response.map((movie: any) => ({
          id: movie.movieId,
          title: movie.movieName,
          genre: movie.genre,
          rating: movie.rating,
          language: movie.language || 'English', // Default to English
          description: movie.description || 'No description available.', // Default description
          image: movie.url || 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJiOWIMi9lW9I2C8SQC5Q2_DHt42KfKedtAw&s', // Default image
        }));
        console.log('Movies:', this.movies); // Debug log
      },
      (error) => {
        console.error('Error fetching movies:', error);
      }
    );
  }

  navigateToMovieDetails(movieId: number): void {
    this.router.navigate([`/home/${this.city}/${this.cityId}/movie/${movieId}`]);
  }
}