// import { Component, OnInit } from '@angular/core';
// import { Router } from '@angular/router';
// import { CityService } from 'src/app/city.service';

// interface City {
//   id: number;
//   cityName: string;
// }

// @Component({
//   selector: 'app-selectcity',
//   templateUrl: './selectcity.component.html',
//   styleUrls: ['./selectcity.component.scss']
// })
// export class SelectcityComponent implements OnInit {
//   cities: City[] = [];
//   selectedCityName: string = '';
//   selectedCityId: number | null = null;

//   constructor(private router: Router, private cityService: CityService) {}

//   ngOnInit(): void {
//     this.fetchCities();
//     const savedCityName = localStorage.getItem('selectedCityName');
//     const savedCityId = localStorage.getItem('selectedCityId');
//     if (savedCityName && savedCityId) {
//       this.selectedCityName = savedCityName;
//       this.selectedCityId = parseInt(savedCityId, 10);
//       this.router.navigate([`/home/${savedCityName.toLowerCase()}`]);
//     }
//   }

//   fetchCities(): void {
//     this.cityService.getCities().subscribe(
//       (response) => {
//         this.cities = response;
//       },
//       (error) => {
//         console.error('Error fetching cities:', error);
//       }
//     );
//   }

//   onCityChange(): void {
//     const selectedCity = this.cities.find(city => city.cityName === this.selectedCityName);
   
//     if (selectedCity) {
//       this.selectedCityId = selectedCity.id;
//       // localStorage.setItem('selectedCity', this.selectedCityName);
//       // localStorage.setItem('cityId', String(this.selectedCityId));
//       this.router.navigate([`/home/${this.selectedCityName.toLowerCase()}/${this.selectedCityId}`]);
//     }
//   }
// }



import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CityService } from 'src/app/city.service';

interface City {
  id: number;
  cityName: string;

}

@Component({
  selector: 'app-selectcity',
  templateUrl: './selectcity.component.html',
  styleUrls: ['./selectcity.component.scss'],
})
export class SelectcityComponent implements OnInit {
  cities: City[] = [];
  filteredCities: City[] = [];
  searchQuery: string = '';

  constructor(private router: Router, private cityService: CityService) {}

  ngOnInit(): void {
    this.fetchCities();
  }

  fetchCities(): void {
    this.cityService.getCities().subscribe(
      (response) => {
        this.cities = response;
        this.filteredCities = [...this.cities];
      },
      (error) => {
        console.error('Error fetching cities:', error);
      }
    );
  }

  filterCities(): void {
    const query = this.searchQuery.toLowerCase();
    this.filteredCities = this.cities.filter((city) =>
      city.cityName.toLowerCase().includes(query)
    );
  }

  selectCity(city: City): void {
  
    this.router.navigate([`/home/${city.cityName.toLowerCase()}/${city.id}`]);
  }
}
