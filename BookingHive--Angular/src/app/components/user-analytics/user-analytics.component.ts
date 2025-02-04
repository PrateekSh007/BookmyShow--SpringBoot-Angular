import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-analytics',
  templateUrl: './user-analytics.component.html',
  styleUrls: ['./user-analytics.component.scss']
})
export class UserAnalyticsComponent implements OnInit {
  analytics: any; // You can strongly type this if you have an interface or class (e.g., UserAnalytics)
  error: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getAnalytics();
  }

  getAnalytics(): void {
    this.userService.getUserAnalytics().subscribe({
      next: (res) => {
        this.analytics = res;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to fetch analytics.';
      }
    });
  }
}
