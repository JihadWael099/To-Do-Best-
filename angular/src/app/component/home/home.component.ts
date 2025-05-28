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

@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    NavbarComponent,
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
  currentUserId = 1;
  user: LoginDto| null = null;

  constructor(private todoService: ToDoService,private userService:UserService) { }

  ngOnInit(): void {
    this.userService.getUserByToken().subscribe({
      next: (user) => this.user = user,
      error: (err) => console.error('Error fetching user:', err)
    });
    this.loadTodos();
    localStorage.setItem("user", String(this.user?.id ?? ''));
  }

  loadTodos(): void {
    this.todoService.getTodos(this.currentUserId).subscribe(todos => {
      this.todos = todos;
    });
  }

  addTask(): void {
    if (this.newTaskTitle.trim()) {
      const newTodo: Todo = {
        title: this.newTaskTitle,
        userId:this.user?.id
      };

      console.log(this.user);
      console.log(newTodo);
      this.todoService.addTodo(newTodo).subscribe(todo => {
        this.todos.push(todo);
        this.newTaskTitle = '';
      });
    }
  }

  selectTask(task: Todo): void {
    this.selectedTask = task;
  }

  closeDetails(): void {
    this.selectedTask = null;
  }

  onTaskDeleted(deletedTaskId: number): void {
    this.todos = this.todos.filter(task => task.id !== deletedTaskId);
    this.selectedTask = null;
  }

  onSearch(query: string): void {
    console.log('Search query:', query);
  }
}