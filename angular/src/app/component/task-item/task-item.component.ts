import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Todo } from '../../model/todo';
import { Router } from '@angular/router';

@Component({
  selector: 'app-task-item',
  templateUrl: './task-item.component.html',
  styleUrls: ['./task-item.component.css'],

})
export class TaskItemComponent {
   constructor(private router: Router) {}
  @Input() task!: Todo;
  @Output() selected = new EventEmitter<Todo>();
  showDetails = false;

  toggleCompletion(): void {
   
    this.task.completed = !this.task.completed;
    
  }

  toggleDetails(): void {
    this.showDetails = !this.showDetails;
  
    if (this.showDetails) {
      this.selected.emit({ ...this.task });
    }
  }
}
