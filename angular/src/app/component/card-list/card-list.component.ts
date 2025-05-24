// import { serverRoutes } from './../../app.routes.server';
// import { Component } from '@angular/core';
// import { ApiService } from '../../service/api.service';
// import { Movie } from '../../model/movie';
// import { CardComponent } from "../card/card.component";
// import { ActivatedRoute, Router, RouterModule } from '@angular/router';
// import { SearchResponse } from '../../model/search-response';
// import { OmbdService } from '../../service/ombd.service';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-card-list',
//   standalone: true,
//   imports: [  CommonModule,CardComponent ,RouterModule ],
//   templateUrl: './card-list.component.html',
//   styleUrl: './card-list.component.css'
// })
// export class CardListComponent {
//   movies: Movie[] = [];
//   allMovies: boolean = false;

//   currentPage: number = 0;
//   pageSize: number = 5;
//   totalPages: number = 0;

//  constructor(
//   private apiService: ApiService,
//   private route: ActivatedRoute,
//   private ombd: OmbdService,
//   private router: Router
// ) {}

//   ngOnInit() {
//     this.route.queryParams.subscribe((params) => {
//       const searchTerm = params['search'];
//       const pageParam = +params['page'] || 0;
//       this.currentPage = pageParam;

//       if (searchTerm !== undefined) {
//         if (this.apiService.getRole() === 'ROLE_USER') {
//           this.apiService.searchMoviesByTitleInternal(searchTerm).subscribe({
//             next: (movies) => {
//               this.movies = movies;
//               if (this.movies.length === 0) {
//                 this.router.navigate(['/not-found']);
//               }
//             },
//             error: () => {
//               this.movies = [];
//               this.router.navigate(['/not-found']);
//             }
//           });
//         } else {
//           this.ombd.getMovieByTitle(searchTerm).subscribe({
//             next: (movies) => {
//               this.movies = [this.apiService.transformMovieResponse(movies)];
//               if (this.movies.length === 0) {
//                 this.router.navigate(['/not-found']);
//               }
//             },
//             error: () => {
//               this.movies = [];
//               this.router.navigate(['/not-found']);
//             }
//           });
//         }
//       } else {
//         this.fetchMovies(this.currentPage);
//       }
//     });
//   }

//   fetchMovies(page: number): void {
//     this.apiService.getAllForUser(page, this.pageSize).subscribe({
//       next: (response) => {
//         console.log(response);
//         this.movies = response.content;
//         this.totalPages = response.totalPages;
//         this.currentPage = response.number;
//         this.allMovies = true;
//       },
//       error: () => {
//         this.movies = [];
//         this.router.navigate(['/not-found']);
//       }
//     });
//   }
// trackByImdbID(index: number, movie: Movie): string {
//   return movie.imdbID;
// }

// pagesArray(): number[] {
//   return Array(this.totalPages).fill(0).map((x, i) => i);
// }
//   goToPage(page: number): void {
//     if (page >= 0 && page < this.totalPages) {
//       this.router.navigate([], {
//         queryParams: { page },
//         queryParamsHandling: 'merge'
//       });
//     }
//   }
// }
