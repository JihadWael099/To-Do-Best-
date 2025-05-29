import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDto } from '../model/user-dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8081/api/v1/user';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(extraHeaders?: { [header: string]: string }): HttpHeaders {
    const token = localStorage.getItem('authToken') || '';
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    if (extraHeaders) {
      for (const key of Object.keys(extraHeaders)) {
        headers = headers.set(key, extraHeaders[key]);
      }
    }
    return headers;
  }

  getUserByToken(): Observable<LoginDto> {
    const headers = this.getAuthHeaders();
    return this.http.get<LoginDto>(this.apiUrl, { headers });
  }

  changePassword(username: string, oldPassword: string, newPassword: string, otp: string): Observable<string> {
    const url = `${this.apiUrl}/changePassword`;
    const headers = this.getAuthHeaders({ 'otp': otp });
    const body = { username, oldPassword, newPassword };
    return this.http.post(url, body, { headers, responseType: 'text' });
  }

  forgetPassword(username: string): Observable<string> {
    const url = `${this.apiUrl}/forget-password`;
    const headers = this.getAuthHeaders();
    const params = new HttpParams().set('username', username);
    return this.http.post(url, null, { headers, params, responseType: 'text' });
  }

  resetPassword(username: string, otpCode: string, newPassword: string): Observable<string> {
    const url = `${this.apiUrl}/reset-password`;
    const headers = this.getAuthHeaders();
    const params = new HttpParams()
      .set('username', username)
      .set('otpCode', otpCode)
      .set('newPassword', newPassword);

    return this.http.post(url, null, { headers, params, responseType: 'text' });
  }
}
