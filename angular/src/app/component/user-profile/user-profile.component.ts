import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
})
export class UserProfileComponent {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private router = inject(Router);

  user: any = null;
  loadingUser = true;
  errorLoadingUser: string | null = null;

  showChangePasswordForm = false;
  otpSent = false;

  changePasswordForm: FormGroup = this.fb.group({
    oldPassword: ['', Validators.required],
    newPassword: ['', [Validators.required, Validators.minLength(6)]],
    otp: ['', Validators.required]
  });

  changePasswordMessage = '';
  changePasswordError = '';

  constructor() {
    this.userService.getUserByToken().subscribe({
      next: (data) => {
        this.user = data;
        this.loadingUser = false;
      },
      error: () => {
        this.errorLoadingUser = 'Failed to load user profile';
        this.loadingUser = false;
      }
    });
  }

  toggleChangePasswordForm(): void {
    this.showChangePasswordForm = !this.showChangePasswordForm;
  }

  sendOtp(): void {
    if (!this.user?.username) return;

    this.userService.forgetPassword(this.user.username).subscribe({
      next: () => {
        this.otpSent = true;
        this.changePasswordMessage = 'OTP sent to your email.';
        this.changePasswordError = '';
      },
      error: () => {
        this.changePasswordError = 'Failed to send OTP.';
        this.changePasswordMessage = '';
      }
    });
  }

  onChangePassword(): void {
    if (this.changePasswordForm.invalid || !this.user?.username) return;

    const { oldPassword, newPassword, otp } = this.changePasswordForm.value;

    this.userService.changePassword(this.user.username, oldPassword, newPassword, otp)
      .subscribe({
        next: () => {
          this.changePasswordMessage = 'Password changed successfully.';
          this.changePasswordError = '';

          localStorage.removeItem('authToken');
          setTimeout(() => this.router.navigate(['/login']), 2000);
        },
        error: () => {
          this.changePasswordError = 'Failed to change password.';
          this.changePasswordMessage = '';
        }
      });
  }
}
