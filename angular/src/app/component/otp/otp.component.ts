import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { OtpService } from '../../service/otp.service';

@Component({
  selector: 'app-otp',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent implements OnInit {
  otpForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private otpService: OtpService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.otpForm = this.fb.group({
    
      username: ['', Validators.required],
      otp: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const username = params['username'];
      if (username) {
        this.otpForm.patchValue({ username });
      } else {
        this.errorMessage = 'No username provided. Please register first.';
        this.otpForm.disable();
      }
    });
  }

  get formControls() {
    return this.otpForm.controls;
  }

handleFormSubmit() {
  if (this.otpForm.invalid) {
    this.errorMessage = 'Please fill in all fields correctly.';
    return;
  }

  this.isLoading = true;
  this.errorMessage = '';
  this.successMessage = '';

  const { username, otp } = this.otpForm.getRawValue();

  console.log('Activating user:', { username, otp });

  this.otpService.activateUser(username, otp).subscribe({
    next: (res: string) => {
      console.log('Activation Response:', res);
      this.isLoading = false;

      if (res === 'User is activated') {
        this.successMessage = 'Account activated successfully!';
        this.router.navigate(['/login']);
      } else if (res === 'Invalid OTP') {
        this.errorMessage = 'The OTP you entered is invalid.';
      } else if (res === 'OTP expired') {
        this.errorMessage = 'Your OTP has expired. Please request a new one.';
      } else {
        this.errorMessage = res || 'Activation failed';
      }
    },
    error: (err) => {
      this.isLoading = false;
      this.errorMessage = 'Failed to activate account: ' + (err.message || 'Unknown error');
      console.error('Activation Error:', err);
    }
  });
}


}
