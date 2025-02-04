import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MovieDetailsComponent } from './pages/movie-details/movie-details.component';
import { HomeComponent } from './pages/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { SigninComponent } from './components/signin/signin.component';
import { SelectcityComponent } from './components/selectcity/selectcity.component';
import { authGuard } from './guards/auth.guard'; 
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { SeatSelectionComponent } from './components/seat-selection/seat-selection.component';
import { BookingComponent } from './components/booking/booking.component';
import { TheatreComponent } from './components/theatre/theatre.component';
import { AdminAnalyticsComponent } from './pages/admin-analytics/admin-analytics.component';
import { HomeGuard } from './home-guard.guard';
import { UserAnalyticsComponent } from './components/user-analytics/user-analytics.component';
import { AuthGuard } from './guards/homeg.guard';
const routes: Routes = [
  // Default route, if no city is selected
  
  { path: '', redirectTo: '/select-city', pathMatch: 'full' },

  // City selection route
  { path: 'select-city', component: SelectcityComponent },
  { path: 'admin-dashboard', loadChildren: () => import('./pages/admin-dashboard/admin-dashboard.module').then(m => m.AdminDashboardModule), canActivate: [authGuard] },
  {
    path: 'admin-dashboard/admin-analytics',
    component: AdminAnalyticsComponent,
    canActivate: [authGuard],
  },
  // Home route with city name
  { path: 'home/:city/:cityid', component: HomeComponent }, 

  // Movie details route with city and movie ID
  { path: 'home/:city/:cityid/movie/:id', component: MovieDetailsComponent },

  // Sign-up route
  { path:'signup', component: SignupComponent },

  // Sign-in route
  { path:'signin', component: SigninComponent },
  { path:'home/:city/:cityid/movie/:id/theatre', component: TheatreComponent ,canActivate: [AuthGuard]},
   
  {path:'home/:city/:cityid/movie/:id/theatre/:theatreId/show/:showId/seats',component: SeatSelectionComponent,canActivate: [AuthGuard]},
  {path:'home/:city/:cityid/movie/:id/theatre/:theatreId/show/:showId/seats/booking', component:BookingComponent,canActivate: [AuthGuard]}, 
  { path: 'user-analytics', component: UserAnalyticsComponent }, 
  // Catch-all route for invalid city or movie ID
  { path: '**', redirectTo: '/select-city' }, 

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
