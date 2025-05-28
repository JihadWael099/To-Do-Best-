import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Login } from '../../model/login';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  formValues: Login = {
    username: '',  
    password: ''
  };
  isLoading = false;      
  loginError = '';      
  
  constructor(private  authService: AuthService ,
    private router: Router 
  ) {}

  handleFormSubmit(form: NgForm) {
    if (form.valid) {
      this.onLogin();
    }
  }

  onLogin() {
    this.isLoading = true;
    this.loginError = ''; 
    this. authService.login(this.formValues).subscribe({
      next: (response) => {
        this.isLoading = false;
        console.log('Login success!', response);
        localStorage.setItem("authToken",response.token);
         localStorage.setItem("username",response.username);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.isLoading = false;
        this.loginError = 'Login failed. Check your credentials.'; 
        console.error('Login error:', err);
      }
    });
  }
}