import { AuthService } from './../../service/auth.service';
import { routes } from './../../app.routes';
import { UserService } from './../../service/user.service';
import { Component } from '@angular/core';
import { Todo } from '../../model/todo';
import { ToDoService } from '../../service/to-do.service';
import { NavbarComponent } from "../navbar/navbar.component";
import { FormsModule } from '@angular/forms';
import { TaskItemComponent } from '../task-item/task-item.component';
import { TaskDetailsComponent } from '../task-details/task-details.component';
import { CommonModule } from '@angular/common';
import { LoginComponent } from '../login/login.component';
import { LoginDto } from '../../model/user-dto';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    FormsModule, 
   TaskItemComponent,
    TaskDetailsComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  todos: Todo[] = [];
  newTaskTitle = '';
  selectedTask: Todo | null = null;
  user: LoginDto| null = null;
  isLoggedIn = false;
  loading = false;
  constructor(private todoService: ToDoService,private route: ActivatedRoute,private userService:UserService,private router: Router,private authService:AuthService) { }
  
ngOnInit(): void {
  const tokenExists = this.authService.isLoggedIn();
  if (tokenExists) {
    this.loading = true; 
    this.userService.getUserByToken().subscribe({
      next: (user) => {
        this.user = user;
        this.isLoggedIn = true;

        this.route.queryParams.subscribe(params => {
          const searchQuery = params['search'] ?? '';

          this.loading = true; 

          if (searchQuery) {
            this.todoService.viewToDoByTitle(searchQuery).subscribe({
              next: (todos) => {
                this.todos = todos;
                this.loading = false; 
              },
              error: (err) => {
                console.error('Error fetching todos by title:', err);
                this.loading = false;
              }
            });
          } else {
           this.loadTodos();
           this.loading = false;
          }
        });
      },
      error: (err) => {
        console.error('Error fetching user:', err);
        this.isLoggedIn = false;
        this.loading = false;
      }
    });
  } else {
    this.isLoggedIn = false;
  }
}


 loadTodos(): void {
  const username = this.user?.username;

  if (!username) {
    console.error('Username is not available.');
    return;
  }

  this.todoService.getTodosByUsername(username).subscribe({
    next: (todos) => this.todos = todos,
    error: (err) => console.error('Error loading todos:', err)
  });
}

  addTask(): void {
    if (this.newTaskTitle.trim()) {
      const newTodo: Todo = {
        title: this.newTaskTitle,
        userId:this.user?.id
      };
      this.todoService.addTodo(newTodo).subscribe(todo => {
        this.todos.push(todo);
        this.newTaskTitle = '';
      });
    }
  }

  selectTask(task: Todo): void {
    this.selectedTask = {...task};
  }

  closeDetails(): void {
    this.selectedTask = null;
  }
 onTaskDeleted(deletedTaskId: number): void {
    this.todoService.deleteTodo(deletedTaskId).subscribe({
      next: (response) => {
        this.todos = this.todos.filter(task => task.id !== deletedTaskId);
        this.selectedTask = null;
        window.location.reload();
      },
      error: (err) => {
        console.error('Failed to delete task:', err);
      }
    });
  }

  onSearch(query: string): void {
    console.log('Search query:', query);
  }
}