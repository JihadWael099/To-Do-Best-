import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToDoDetails } from '../model/to-do-details';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToDoDetailsService {
private baseUrl = 'http://localhost:8081/api/v1/toDoDetails';
  constructor(private http: HttpClient) {}
  private getHeaders(token: string): HttpHeaders {
    return new HttpHeaders({ Authorization: token });
  }
  addDetails(details: ToDoDetails, token: string): Observable<ToDoDetails> {
    return this.http.post<ToDoDetails>(this.baseUrl, details, {
      headers: this.getHeaders(token),
    });
  }
  updateDetails(details: ToDoDetails, id: number, token: string): Observable<ToDoDetails> {
    return this.http.put<ToDoDetails>(`${this.baseUrl}/${id}`, details, {
      headers: this.getHeaders(token),
    });
  }
  viewDetailsById(id: number, token: string): Observable<ToDoDetails> {
    return this.http.get<ToDoDetails>(`${this.baseUrl}/${id}`, {
      headers: this.getHeaders(token),
    });
  }
}
