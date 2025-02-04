import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {
  signinForm!: FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.signinForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get f() {
    return this.signinForm.controls;
  }

  onSubmit() {
    if (this.signinForm.valid) {
      this.userService.loginUser(this.signinForm.value).subscribe({
        next: (response: any) => { 
          console.log('Login successful:', response);
          
          localStorage.setItem('accessToken', response.token);
          localStorage.setItem("signedIn", "true");
          localStorage.setItem("role", response.role);
          localStorage.setItem("userId", response.user_id);
          alert('Login successful! 🎉');
          this.router.navigate(['/home']); 
        },
        error: (error: any) => { 
          console.error('Login failed:', error);
          alert('Login failed. Please try again.');
        }
      });
    } else {
      alert('Please fill in the form correctly.');
    }
  }
}
