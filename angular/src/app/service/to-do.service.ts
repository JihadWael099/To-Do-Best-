import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToDoEntity } from '../model/to-do-entity';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToDoService {

   private baseUrl = 'http://localhost:8081/api/v1/toDos';

  constructor(private http: HttpClient) {}

  private getHeaders(token: string): HttpHeaders {
    return new HttpHeaders({ Authorization: token });
  }

  addToDo(todo: ToDoEntity, token: string): Observable<ToDoEntity> {
    return this.http.post<ToDoEntity>(this.baseUrl, todo, {
      headers: this.getHeaders(token),
    });
  }

  removeToDo(id: number, token: string): Observable<string> {
    return this.http.delete<string>(`${this.baseUrl}/${id}`, {
      headers: this.getHeaders(token),
    });
  }

  viewToDoById(id: number, token: string): Observable<ToDoEntity> {
    return this.http.get<ToDoEntity>(`${this.baseUrl}/search/${id}`, {
      headers: this.getHeaders(token),
    });
  }

  viewToDoByTitle(title: string, token: string): Observable<ToDoEntity[]> {
    return this.http.get<ToDoEntity[]>(`${this.baseUrl}/title/${title}`, {
      headers: this.getHeaders(token),
    });
  }

  viewByUserId(userId: number, token: string): Observable<ToDoEntity[]> {
    const params = new HttpParams().set('id', userId.toString());
    return this.http.get<ToDoEntity[]>(`${this.baseUrl}/user`, {
      headers: this.getHeaders(token),
      params,
    });
  }

  updateTitle(title: string, id: number, token: string): Observable<ToDoEntity> {
    return this.http.put<ToDoEntity>(`${this.baseUrl}/title/${title}/id/${id}`, null, {
      headers: this.getHeaders(token),
    });
  }
}
