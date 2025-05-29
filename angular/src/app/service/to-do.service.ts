import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Todo } from '../model/todo';
import { ToDoDetails } from '../model/to-do-details';

@Injectable({
  providedIn: 'root'
})
export class ToDoService {

  private apiUrl = 'http://localhost:8080/api/v1/toDos'; 
    constructor(private http: HttpClient) { }

    private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken') || '';
    return new HttpHeaders({
      'Authorization': `${token}`
    });
  }
 
getTodosByUsername(username: string): Observable<Todo[]> {
  const headers = this.getAuthHeaders();
  const params = new HttpParams().set('username', username);
  return this.http.get<Todo[]>(`${this.apiUrl}/user`, { headers, params });
}
  addTodo(todo: Todo): Observable<Todo> {
    const headers = this.getAuthHeaders();
    return this.http.post<Todo>(this.apiUrl, todo, { headers });
  }

  updateTodo(todo: Todo): Observable<Todo> {
    const headers = this.getAuthHeaders();
    return this.http.put<Todo>(`${this.apiUrl}/${todo.id}`, todo, { headers });
  }

 deleteTodo(id: number): Observable<string> {
  const headers = this.getAuthHeaders();
  return this.http.delete<string>(`${this.apiUrl}/${id}`, { headers, responseType: 'text' as 'json' });
}


  updateTodoDetails(details: ToDoDetails): Observable<ToDoDetails> {
    const headers = this.getAuthHeaders();
    return this.http.put<ToDoDetails>(`${this.apiUrl}/details/${details.id}`, details, { headers });
  }
  viewToDoByTitle(title: string): Observable<Todo[]> {
  const headers = this.getAuthHeaders();
  return this.http.get<Todo[]>(`${this.apiUrl}/title/${encodeURIComponent(title)}`, { headers });
}
}
