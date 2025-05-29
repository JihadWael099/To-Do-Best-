import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { Login } from '../model/login';

import { HttpClient } from '@angular/common/http';
import { LoginResponse } from '../model/login-response';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
   private apiUrl = 'http://localhost:8081/api/v1/auth';
  constructor(private http: HttpClient,private router: Router) {}
 login(data: Login): Observable<LoginResponse> {
  return this.http.post<LoginResponse>(`${this.apiUrl}/login`, data);
}
  register(user: User): Observable<string> {
    return this.http.post(this.apiUrl + '/register', user, { responseType: 'text' });
  }
  saveToken(token: string): void {
    localStorage.setItem('authToken', token);
  }
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }
  logout(): void {
    localStorage.removeItem('authToken');
 window.location.reload();

  }
  isLoggedIn(): boolean {
  return !!this.getToken();
  }
}
