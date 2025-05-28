import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Todo } from '../../model/todo';
import { ToDoDetails } from '../../model/to-do-details';
import { Priority } from '../../model/priority';
import { Status } from '../../model/status';
import { ToDoService } from '../../service/to-do.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-task-details',
  imports: [FormsModule,CommonModule],
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css',
  standalone: true,
   providers: [ToDoService] 
})
export class TaskDetailsComponent {
@Input() task!: Todo;
  @Output() close = new EventEmitter<void>();
  @Output() saved = new EventEmitter<void>();
  @Output() deleted = new EventEmitter<number>();

  taskDetails: ToDoDetails = {
    description: '',
    priority: Priority.MEDIUM,
    createAt: new Date(),
    startAt: new Date(),
    finishAt: new Date(),
    status: Status.PENDING,
    entityId: this.task?.id || 0,
    userId: this.task?.userId || 0
  };

  priorities = Object.values(Priority);
  statuses = Object.values(Status);

  constructor(private todoService: ToDoService) {
    if (this.task?.details_id) {
      this.taskDetails = { ...this.task.details_id };
    }
  }

  saveChanges(): void {
    if (this.taskDetails.id) {
      this.todoService.updateTodoDetails(this.taskDetails).subscribe(() => {
        this.saved.emit();
      });
    } 
    else {

      this.taskDetails.entityId = this.task.id;
      this.taskDetails.userId = Number(localStorage.getItem("user")) || 0;
  
    }
  }

  deleteTask(): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.todoService.deleteTodo(0).subscribe(() => {
        this.deleted.emit(this.task.id);
      });
    }
  }
}
