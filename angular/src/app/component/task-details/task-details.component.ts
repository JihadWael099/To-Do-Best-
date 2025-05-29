import { config } from './../../app.config.server';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common';

import { Todo } from '../../model/todo';
import { ToDoDetails } from './../../model/to-do-details';
import { Priority } from '../../model/priority';
import { Status } from '../../model/status';

import { ToDoService } from '../../service/to-do.service';
import { ToDetailsService } from './../../service/to-details.service';

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css'],
})
export class TaskDetailsComponent {
  private _task!: Todo;

  @Input() 
  set task(value: Todo) {
    this._task = value;
    if (value) {
      this.initializeTaskDetails();
    }
  }
  get task(): Todo {
    return this._task;
  }

  @Output() close = new EventEmitter<void>();
  @Output() saved = new EventEmitter<void>();
  @Output() deleted = new EventEmitter<number>();

  taskDetails!: ToDoDetails;

  priorities = Object.values(Priority);
  statuses = Object.values(Status);

  constructor(private toDetailsService: ToDetailsService) {}

  private getValidUserId(): number {
    try {
      const userData = localStorage.getItem("user");
      if (userData && userData.startsWith('{')) {
        const user = JSON.parse(userData);
        return user.id || 0;
      }
      const userId = parseInt(userData ?? '0', 10);
      return isNaN(userId) ? 0 : userId;
    } catch (e) {
      console.error('Error parsing user data:', e);
      return 0;
    }
  }

  private setDefaultTaskDetails() {
    this.taskDetails = {
      description: '',
      priority: Priority.MEDIUM,
      createAt: new Date(),
      startAt: new Date(),
      finishAt: new Date(),
      status: Status.PENDING,
      entityId: this.task.id,
    };
  }

  private initializeTaskDetails(): void {
    if (!this.task) {
      this.setDefaultTaskDetails();
      return;
    }

    this.toDetailsService.viewDetails(this.task.id ?? -1).subscribe({
      next: (details) => {
        if (details) {
          this.taskDetails = details;
        } else {
          this.setDefaultTaskDetails();
        }
      },
      error: (err) => {
        console.error('Failed to fetch task details:', err);
        this.setDefaultTaskDetails();
      }
    });
  }
confirmDelete(): void {
   if (confirm('Are you sure you want to delete this task?')) {
    if (this.task && this.task.id != null) {
      this.deleted.emit(this.task.id);
    } else {
      console.error('Task ID is missing. Cannot delete.');
    }
  }
}
  saveChanges(): void {
    if (!this.taskDetails) {
      console.error('Task details are not initialized');
      return;
    }

    if (this.task) {
      this.taskDetails.entityId = this.task.id;
    }
    this.toDetailsService.addDetails(this.taskDetails).subscribe({
      next: (response) => {
        console.log('Save successful:', response);
        this.saved.emit();
        this.close.emit();
      },
      error: (err) => {
        console.error('Save failed:', err);
      }
    });
  }
}
