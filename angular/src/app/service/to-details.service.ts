import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToDoDetails } from '../model/to-do-details';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToDetailsService {

    private apiUrl = 'http://localhost:8080/api/v1/toDoDetails'; 

  constructor(private http: HttpClient) {}

  addDetails(details: ToDoDetails): Observable<ToDoDetails> {
    const token = localStorage.getItem('authToken'); 
    const headers = new HttpHeaders({
      'Authorization': `${token}`
    });
    return this.http.post<ToDoDetails>(`${this.apiUrl}`, details, { headers });
  }
  viewDetails(id: number): Observable<ToDoDetails> {
    const token = localStorage.getItem('authToken'); 
    const headers = new HttpHeaders({
      'Authorization': `${token}`
    });
    return this.http.get<ToDoDetails>(`${this.apiUrl}/${id}`, {
      headers
    });
  }
}
