import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private http: HttpClient, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const url = 'http://localhost:8099/jwt/authenticate';
    const credentials = { credentials: true }; // Adjust this according to your actual API requirements

    return this.http.get(url, { 
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      withCredentials: true,
      responseType: 'text' // Specify responseType as 'text'
    }).pipe(
      map((response: any) => {
        // Handle the text response and check for expected content
        
          return true;
        
      }),
      catchError((error) => {
        if (error.status === 401) {
          // If the response is unauthorized (401), navigate to /signin
          this.router.navigate(['/signin']);
          return of(false);
        } else {
          // Handle other errors
          console.error('API call failed', error);
          this.router.navigate(['/signin']);
          return of(false);
        }
      })
    );
  }
}