import { LoginResponse } from './../model/login-response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDto } from '../model/user-dto';
import { ChangePassDto } from '../model/change-pass-dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8081/api/v1/user';

  constructor(private http: HttpClient) { }
  

  getUserByToken(): Observable<LoginDto> {
     const headers = this.getAuthHeaders();
    return this.http.get<LoginDto>(this.apiUrl, { headers });
  }

  
    private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken') || '';
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }
}
