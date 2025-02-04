import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  isAuthenticated(): boolean {
    return true;
  }


  getUserRole(): number {
    const userRole = localStorage.getItem('role');  
    return userRole ? parseInt(userRole, 10) : 0;  
  }
}
