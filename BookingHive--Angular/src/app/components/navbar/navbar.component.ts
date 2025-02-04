import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { CityService } from 'src/app/city.service';

interface City {
  id: number;
  cityName: string;
}

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  selectedCity: string = '';
  isLoggedIn = localStorage.getItem('signedIn') === 'true';
  authText = 'Sign In';
  isAdmin = localStorage.getItem('role') === '1';
  city: string = '';
  cityId: string = '';
  cities: City[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private cityService: CityService
  ) {}

  ngOnInit(): void {
    this.authText = this.isLoggedIn ? 'Sign Out' : 'Sign In';

    // Fetch cities from the API
    this.fetchCities();

    // Listen for changes in the route parameters
    this.route.paramMap.subscribe(params => {
      this.city = params.get('city')?.toLowerCase() || 'Select City';
      this.cityId = params.get('cityid') || '';

      
      this.selectedCity = this.city;
    });
  }

  fetchCities(): void {
    this.cityService.getCities().subscribe(
      (response) => {
        this.cities = response.map(city => ({
          id: city.id,
          cityName: city.cityName.toLowerCase()
        }));
        console.log('Available Cities:', this.cities); // Debug log
      },
      (error) => {
        console.error('Error fetching cities:', error);
      }
    );
  }

  onCityChange(selectedCity?: string) {
    const selectedCityObj = this.cities.find(city => city.cityName === this.selectedCity);

    if (selectedCityObj) {
      this.router.navigate([`/home/${selectedCityObj.cityName.toLowerCase()}/${selectedCityObj.id}`]);
    } else {
      this.router.navigate(['/home/bengaluru/1']);
    }
    // if (selectedCity) {
    //   this.selectedCity = selectedCity;
    // }
    // const selectedCityObj = this.cities.find(city => city.cityName === this.selectedCity);
    // if (selectedCityObj) {
    //   this.router.navigate([`/home/${selectedCityObj.cityName.toLowerCase()}/${selectedCityObj.id}`]);
    // } else {
    //   this.router.navigate(['/home/bengaluru/1']);
    // }

  }

  toggleAuth() {
    if (this.isLoggedIn) {
      this.userService.logoutUser().subscribe(
        (response) => {
          localStorage.removeItem('signedIn');
          localStorage.removeItem('accessToken');
          localStorage.removeItem('role');
          console.log(response);
          this.router.navigate(['/signin']);
          this.isLoggedIn = false;
          this.isAdmin = false;
          this.authText = 'Sign In';
          alert('Logged out!');
        },
        (error) => {
          console.error('Logout failed:', error);
          alert('Logout failed. Please try again.');
        }
      );
    } else {
      this.router.navigate(['/signin']);
    }
  }

  navigateToAdmin() {
    this.router.navigate(['/admin-dashboard']);
  }

  navigateToAnalytics() {
    this.router.navigate(['/user-analytics']);
  }

  navigateToHome() {
    this.router.navigate([`/home/${this.city}/${this.cityId}`]);
  }
}