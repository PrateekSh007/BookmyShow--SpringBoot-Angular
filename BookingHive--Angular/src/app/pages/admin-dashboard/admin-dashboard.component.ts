import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { Router } from '@angular/router';
 
@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
 
  cityData: { cityName: string } = { cityName: '' };
  theatreData: { thName: string; cityId: number } = { thName: '', cityId: 0 };
  movieData: { movieName: string; genre: string; rating: number, url: string ,trailer:string, about:string} = { movieName: '', genre: '', rating: null, url: '',about:'',trailer:'' };
  showData: { price: number; startTime: string; theatreId: number; movieId: number } = {
    price: null,
    startTime: '',
    theatreId: null,
    movieId: null,
  };
  castsData: { movieName: string; casts: { name: string; url: string }[] } = { movieName: '', casts: [{ name: '', url: '' }] };
  cities: { id: number, cityName: string }[] = [];  // Initialize cities array
  
  constructor(private adminService: AdminService, private router: Router) {}
 
  ngOnInit(): void {
    this.fetchCities();  // Fetch cities when component initializes
  }
 
  fetchCities(): void {
    this.adminService.getCities().subscribe({
      next: (response) => {
        console.log('Cities fetched:', response);  // Log to check if cities are fetched
        this.cities = response;  // Assign the response to cities array
      },
      error: (error) => {
        console.error('Error fetching cities:', error);
      }
    });
  }
 
  addNewCast() {
    this.castsData.casts.push({ name: '', url: '' });
  }
 
  removeCast(index: number) {
    this.castsData.casts.splice(index, 1);
  }
 
  addCity(data: { cityName: string }) {
    console.log('Adding City:', data);
    this.adminService.addCity(data).subscribe({
      next: (response) => {
        console.log('City added successfully:', response);
        this.cityData = { cityName: '' };
        alert('City added successfully');
      },
      error: (error) => {
        console.error('Error adding city:', error);
      }
    });
  }
 
  addTheatre(data: { thName: string, cityId: number }) {
    if (data.cityId === 0) {
      console.error('Please select a valid city');
      return;
    }
    console.log('Adding Theatre:', data);
    this.adminService.addTheatre(data).subscribe(
      response => {
        console.log('Theatre added successfully:', response);
        alert('Theatre added successfully');
        this.theatreData = { thName: '', cityId: 0 };
      },
      error => {
        console.error('Error adding theatre:', error);
      }
    );
  }
 
  addMovie(data: { movieName: string; genre: string; rating: number; url: string,trailer:string, about:string }) {
    console.log('Adding Movie:', data);
    this.adminService.addMovie(data).subscribe(
      response => {
        console.log('Movie added successfully:', response);
        alert('Movie added successfully');
        this.movieData = { movieName: '', genre: '', rating: 0, url: '',trailer:'',about:'' };
      },
      error => {
        console.error('Error adding movie:', error);
      }
    );
  }
 
  addShow(data: { price: number; startTime: string; theatreId: number; movieId: number }) {
    const formattedData = {
      ...data,
      startTime: this.formatStartTime(data.startTime),
    };
 
    console.log('Adding Show:', formattedData);
    this.adminService.addShow(formattedData).subscribe(
      (response) => {
        console.log('Show added successfully:', response);
        alert('Show added successfully');
        this.showData = { price: 0, startTime: '', theatreId: 0, movieId: 0 }; // Reset form after successful add
      },
      (error) => {
        console.error('Error adding show:', error);
      }
    );
  }
 
  addCasts(data: { movieName: string; casts: { name: string; url: string }[] }) {
    console.log('Adding Casts:', data);
    this.adminService.addCasts(data).subscribe(
      response => {
        console.log('Casts added successfully:', response);
        alert('Casts added successfully');
        this.castsData = { movieName: '', casts: [{ name: '', url: '' }] }; // Reset form after successful add
      },
      error => {
        console.error('Error adding casts:', error);
      }
    );
  }
 
  formatStartTime(startTime: string): string {
    return startTime ? `${startTime}:00` : '';
  }
 
  redirect() {
    this.router.navigate(['/admin-dashboard/admin-analytics']);
  }
}