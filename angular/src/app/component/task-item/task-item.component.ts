import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Todo } from '../../model/todo';

@Component({
  selector: 'app-task-item',
  imports: [],
  templateUrl: './task-item.component.html',
  styleUrl: './task-item.component.css'
})
export class TaskItemComponent {
  @Input() task!: Todo;
  @Output() selected = new EventEmitter<Todo>();
  showDetails = false;

  toggleCompletion(): void {
    this.task.completed = !this.task.completed;
  }

  toggleDetails(): void {
    this.showDetails = !this.showDetails;
    if (this.showDetails) {
      this.selected.emit(this.task);
    }
  }

}
