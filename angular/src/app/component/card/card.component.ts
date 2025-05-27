import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ToDoEntity } from '../../model/to-do-entity';
import { ToDoService } from './../../service/to-do.service';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  @Input() toDo!: ToDoEntity;
  constructor(private router: Router, private api: ToDoService) {}

  handleDetailsItem(id: number): void {
    console.log('Navigating to details for ID:', id);
    this.router.navigate(['/todo-details', id]);
  }

  handleAddItem(todo: ToDoEntity): void {
    const token = localStorage.getItem('token') || '';
    this.api.addToDo(todo, `Bearer ${token}`).subscribe({
      next: (response) => {
        console.log('Todo added successfully:', response);
        alert('Todo added successfully!');
        this.router.navigate(['/todos']);
      },
      error: (error) => {
        console.error('Error adding todo', error);
        alert('Todo could not be added.');
      }
    });
  }

  
  handleDeleteItem(id: number): void {
    const token = localStorage.getItem('token') || '';
    this.api.removeToDo(id, `Bearer ${token}`).subscribe({
      next: (response) => {
        console.log('Todo removed successfully:', response);
        this.router.navigate(['/todos']);
      },
      error: (error) => {
        console.error('Error removing todo', error);
        alert('Failed to remove todo.');
      }
    });
  }
}
