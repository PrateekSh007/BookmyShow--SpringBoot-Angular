import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MovieCarouselComponent } from './components/movie-carousel/movie-carousel.component'; // Import the carousel
import { RecommendedMoviesComponent } from './components/recommended-movies/recommended-movies.component';
import { HomeComponent } from './pages/home/home.component';
import { MovieDetailsComponent } from './pages/movie-details/movie-details.component';
import { SignupComponent } from './components/signup/signup.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { SigninComponent } from './components/signin/signin.component';
import { SelectcityComponent } from './components/selectcity/selectcity.component';
import { SeatSelectionComponent } from './components/seat-selection/seat-selection.component';
import { PaymentDialogComponent } from './components/payment-dialog/payment-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserComponent } from './components/user/user.component';
import { TheatreComponent } from './components/theatre/theatre.component';
import { CommonModule } from '@angular/common';
import { AdminAnalyticsComponent } from './pages/admin-analytics/admin-analytics.component';
import { FooterComponent } from './components/footer/footer.component';
import { Navbar2Component } from './components/navbar2/navbar2.component';
import { AdsComponent } from './components/ads/ads.component';
import { BookingComponent } from './components/booking/booking.component';
import { Navbar3Component } from './components/navbar3/navbar3.component';
import { UserAnalyticsComponent } from './components/user-analytics/user-analytics.component';
// import { NavbarComponent } from './components/navbar/navbar.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';

// import { QRCodeComponent } from 'angularx-qrcode';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MovieCarouselComponent,   
    RecommendedMoviesComponent,
    HomeComponent,
    MovieDetailsComponent,
    SignupComponent,
    SigninComponent,
    SelectcityComponent,
    SeatSelectionComponent,
    PaymentDialogComponent,
    UserComponent,
    TheatreComponent,
    AdminAnalyticsComponent,
    FooterComponent,
    Navbar2Component,
    AdsComponent,
    BookingComponent,
    Navbar3Component,
    UserAnalyticsComponent

    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule ,
    ReactiveFormsModule,
    FormsModule,
    MatDialogModule,
    BrowserAnimationsModule,
    CommonModule,
    MatSelectModule,
    MatFormFieldModule,
    NgxChartsModule,
    // QRCodeComponent

    
  ],
 
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
