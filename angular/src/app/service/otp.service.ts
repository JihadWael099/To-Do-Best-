import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OtpService {
  private apiUrl = 'http://localhost:8081/api/v1/otp';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('register') || '';
    return new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });
  }

  sendOtpEmail(username: string): Observable<string> {
    if (!username) {
      throw new Error('Username is required');
    }
    const params = new HttpParams().set('username', username);
    const headers = this.getAuthHeaders();

    console.log('Sending OTP request to:', `${this.apiUrl}/send?username=${username}`);

    return this.http.get(`${this.apiUrl}/send`, { params, headers, responseType: 'text', observe: 'response' }).pipe(
      map(response => {
        console.log('Raw OTP Response:', {
          status: response.status,
          headers: response.headers,
          body: response.body
        });

        if (response.body == null) {
          throw new Error('Empty response body');
        }
        return response.body.trim();
      }),
      catchError(err => {
        console.error('OTP Request Error:', {
          status: err.status,
          statusText: err.statusText,
          error: err.error,
          url: err.url
        });
        return throwError(() => new Error('Failed to process OTP response: ' + (err.message || 'Unknown error')));
      })
    );
  }

  activateUser(username: string, otp: string): Observable<string> {
    if (!username || !otp) {
      throw new Error('Username and OTP are required');
    }
    const params = new HttpParams().set('username', username);
    const authHeaders = this.getAuthHeaders();

    const headers = authHeaders.set('otp', otp);

    const url = `http://localhost:8081/api/v1/user/activate`;

    console.log('Sending activation request to:', `${url}?username=${username}`);

    return this.http.get(url, { params, headers, responseType: 'text', observe: 'response' }).pipe(
      map(response => {
        console.log('Raw OTP Activate Response:', {
          status: response.status,
          headers: response.headers,
          body: response.body
        });

        if (response.body == null) {
          throw new Error('Empty response body');
        }
        return response.body.trim();
      }),
      catchError(err => {
        console.error('OTP Activate Request Error:', {
          status: err.status,
          statusText: err.statusText,
          error: err.error,
          url: err.url
        });
        return throwError(() => new Error('Failed to process OTP activate response: ' + (err.message || 'Unknown error')));
      })
    );
  }
}
