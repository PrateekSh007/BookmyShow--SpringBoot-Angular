import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';
// import { HomeComponent } from './home/home.component';
import { HomeComponent } from './pages/home/home.component';

@Injectable({
  providedIn: 'root'
})
export class HomeGuard implements CanDeactivate<HomeComponent> {
  canDeactivate(component: HomeComponent): boolean {
    // // Prevent back navigation only when on the home page
    // history.pushState(null, '', window.location.href);
    // window.onpopstate = () => {
    //   history.go(1);
    // };
    return true;
  }
}