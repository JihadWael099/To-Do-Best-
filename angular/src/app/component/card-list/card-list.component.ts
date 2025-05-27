import { serverRoutes } from './../../app.routes.server';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardComponent } from '../card/card.component';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ToDoEntity } from '../../model/to-do-entity';
import { ToDoService } from '../../service/to-do.service';

@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [  CommonModule,CardComponent ,RouterModule ],
  templateUrl: './card-list.component.html',
  styleUrl: './card-list.component.css'
})
export class CardListComponent {
  toDo: ToDoEntity[] = [];
  allTodo: boolean = false;

  currentPage: number = 0;
  pageSize: number = 5;
  totalPages: number = 0;

 constructor(
  private apiService: ToDoService,
  private route: ActivatedRoute,
  private router: Router
) {}


  
}
