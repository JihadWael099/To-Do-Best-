import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { User } from '../../model/user';
import { OtpService } from '../../service/otp.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private otpService: OtpService
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(20)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  get formControls() {
    return this.registerForm.controls;
  }

  handleFormSubmit() {
    if (this.registerForm.invalid) {
      this.errorMessage = 'Please fill in all fields correctly.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    const registerData: User = {
      username: this.registerForm.value.username,
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
    };

    console.log('Registering user:', registerData);

   this.authService.register(registerData).subscribe({
  next: (token) => {
    this.authService.saveToken(token);  
    console.log('Registration successful, token saved, sending OTP for username:', registerData.username);
    
    this.otpService.sendOtpEmail(registerData.username).subscribe({
      next: (response) => {
        this.isLoading = false;
        if (response === 'OTP email sent successfully!') {
          this.router.navigate(['verify-otp'], { queryParams: { username: registerData.username } });
        } else {
          this.errorMessage = response || 'Unexpected OTP response';
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = 'Failed to send OTP: ' + (err.message || 'Unknown error');
        console.error('OTP Error:', err);
      }
    });
  },
  error: (err) => {
    this.isLoading = false;
    this.errorMessage = err.error?.message || 'Registration failed. Please try again.';
    console.error('Registration Error:', err);
  }
});

  }
}