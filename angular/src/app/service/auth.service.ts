import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { Login } from '../model/login';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
   private apiUrl = 'http://localhost:8081/api/v1/auth';
  constructor(private http: HttpClient) {}
  login(data: Login): Observable<string> {
    return this.http.post(this.apiUrl + '/login', data, { responseType: 'text' });
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
  }
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
