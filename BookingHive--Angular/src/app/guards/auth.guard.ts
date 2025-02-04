// src/app/guards/auth.guard.ts

import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

// Define the guard function
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);  
  const router = inject(Router);  
  // Check if the user is authenticated and has admin role (role = 1)
  if (localStorage.getItem('role')=='1') {
    return true;  
  } else {
   
    router.navigate(['/signin']);
    return false; 
  }
};
