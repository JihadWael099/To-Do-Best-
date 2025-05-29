import { AuthService } from './../../service/auth.service';
import { Component, inject } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
 
  router = inject(Router);
  authService=inject(AuthService);

  searchControl = new FormControl('');
  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
  search() {
    let searchString = '';
    if (this.searchControl.value) {
       searchString = this.searchControl.value.trim();
    } 
      this.router.navigate([], {
        queryParams: { search: searchString }
      });
    
  }
}