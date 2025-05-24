
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  // @Input() movie!: Movie;
  // isAdmin: boolean = false;
  // @Input() fromDatabase: boolean = false; 
  // constructor(private router: Router, private api: ApiService) {}

  // ngOnInit(): void {
  //   const role = localStorage.getItem('role');
  //   console.log(role)
  //   this.isAdmin = role === 'ROLE_ADMIN';
  // }

  // handleDetailsItem(id: string): void {
  //   console.log(id);
  //   this.router.navigate(['/movie-details', id]);
  // }

  // handleAddItem(movie: Movie): void {
  //   this.api.addMovie(movie).subscribe({
  //     next: (response) => {
  //       console.log('Movie added successfully:', response);
  //       alert('Movie added successfully!');
  //       this.router.navigate(['/movies']);
  //     },
  //     error: (error) => {
  //       console.error('Error adding movie', error);
  //       alert('Movie is added before');
  //     }
  //   });
  // }

  // handleDeleteItem(id: string): void {
  //   this.api.removeMovie(id).subscribe({
  //     next: (response) => {
  //       console.log('Movie removed successfully:', response);
  //       // alert('Movie is remove');
  //       this.router.navigate(['/movies']);
  //     },
  //     error: (error) => {
  //       console.error('Error removie movie', error);
  //     }
  //   });
  // }
}
