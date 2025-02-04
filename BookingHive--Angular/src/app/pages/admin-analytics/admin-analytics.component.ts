import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';
 
@Component({
  selector: 'app-admin-analytics',
  templateUrl: './admin-analytics.component.html',
  styleUrls: ['./admin-analytics.component.scss'],
})
export class AdminAnalyticsComponent implements OnInit {
  
  analyticsData: any = null;
  cityId: number | null = null;
  movieName: string | null = null;
  isLoading: boolean = false;
 
  // Pie Chart Data
  pieChartData: any[] = [];
  view: [number, number] = [700, 400]; // Chart Size
 
  constructor(
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
 
  ngOnInit(): void {
    // Fetch query parameters for specific analytics
    this.route.queryParams.subscribe((params) => {
      this.cityId = +params['cityId']; // Convert to number
      this.movieName = params['movieName'];
 
      if (this.cityId && this.movieName) {
        this.fetchAnalytics();
      }
    });
 
    // Fetch pie chart analytics data
    this.fetchAllAnalytics();
  }
 
  // Fetch specific analytics based on city and movie
  fetchAnalytics(): void {
    if (this.cityId && this.movieName) {
      this.isLoading = true;
      this.adminService.getAnalytics(this.cityId, this.movieName).subscribe(
        (data) => {
          this.analyticsData = data;
          this.isLoading = false;
        },
        (error) => {
          console.error('Error fetching analytics data:', error);
          this.isLoading = false;
        }
      );
    }
  }
 
  // Fetch all analytics data for Pie Chart
  fetchAllAnalytics(): void {
    this.adminService.getAllAnalytics().subscribe(
      (data) => {
        this.pieChartData = data.map((item: any) => ({
          name: item.movieName,
          value: item.grossSale
        }));
      },
      (error) => {
        console.error('Error fetching pie chart data:', error);
      }
    );
  }
 
  // Handle form submission
  onSubmit(): void {
    if (this.cityId && this.movieName) {
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { cityId: this.cityId, movieName: this.movieName },
        queryParamsHandling: 'merge',
      });
    }
  }
}
 