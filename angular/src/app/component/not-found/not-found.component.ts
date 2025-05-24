import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found',
  imports: [RouterLink,
    ReactiveFormsModule,],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.css'
})
export class NotFoundComponent {

}
