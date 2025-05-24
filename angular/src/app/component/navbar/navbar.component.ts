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
  logout() {
    this. authService.logout();
    this.router.navigate(['/login']);
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