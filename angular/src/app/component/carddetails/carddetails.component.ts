// import { Component, Input } from '@angular/core';
// import { ActivatedRoute } from '@angular/router';
// import { Movie } from '../../model/movie';
// import { ApiService } from '../../service/api.service';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';
// import { OmbdService } from '../../service/ombd.service';

// @Component({
//   selector: 'app-carddetails',
//   imports: [CommonModule, FormsModule],
//   templateUrl: './carddetails.component.html',
//   styleUrls: ['./carddetails.component.css']
// })
// export class CarddetailsComponent {

//   constructor(private activeRoute: ActivatedRoute, private apiService: ApiService,private ombd: OmbdService) { }

//   movieId!: string;
//   movie!: Movie;
//   isLoading = true;
//   error: string | null = null;
//   rating: number = 0;
//   isAdmin: boolean = false;
//   showRatingBox: boolean = false;
//   avgRating: number | null = null;
  
//   openRatingBox(): void {
//     this.showRatingBox = true;
//   }

  
//   closeRatingBox(): void {
//     this.showRatingBox = false;
//   }

 
//   submitRating(): void {
//     if (this.rating < 1 || this.rating >10) {
//       alert('Please provide a rating between 1 and 10');
//       return;
//     }

//     const ratingData = {
//         movies: {
//           imdbID: this.movieId
//         },
//         rating: this.rating 
//     };

    
//     this.apiService.rateMovie(ratingData).subscribe({
//       next: (response) => {
//         alert('Rating submitted successfully!');
//         this.closeRatingBox();
//         window.location.reload();
//       },
//       error: (error) => {
//         alert('Failed to submit rating. Please try again.');
//         console.error('Error:', error);
//       }
//     });
//   }

//   ngOnInit() {
    
//     const role = localStorage.getItem('role');
//     console.log(role);
//     this.isAdmin = role === 'ROLE_ADMIN';
    
//     this.movieId = this.activeRoute.snapshot.params['id'];
//     if (this.movieId!) {
//       this.getAvgRating();
//       this.apiService.getMovieByIdInternal(this.movieId).subscribe({
//         next: (movie) => {
//           this.movie = movie;
//           this.isLoading = false;
//         },
//         error: (err) => {
//           this.error = err.message || 'Failed to load movie details';
//           this.isLoading = false;
//           console.error('API Error:', err);
//         }
//       });
//     } else {
//       this.error = 'No movie ID provided';
//       this.isLoading = false;
//     }
//   }



//   getAvgRating(): void {
//     this.apiService.getAvgRating(this.movieId).subscribe({
//       next: (response) => {
//         if (response) {
//           this.avgRating = response;
//           console.log('Average Rating:', this.avgRating);
//         } else {
//           console.error('Invalid response:', response);
//           this.avgRating = null; 
//         }
//       },
//       error: (error) => {
//         console.error('Error fetching average rating:', error);
//         this.avgRating = null;
//       }
//     });
//   }

// }
