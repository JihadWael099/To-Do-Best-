import { Component } from '@angular/core';

@Component({
  selector: 'app-card',
  imports: [],
  templateUrl: './card.component.html',
  styleUrl: './card.component.css'
})
export class CardComponent {
 title = 'Noteworthy technology acquisitions 2021';
  description = 'Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse chronological order.';
}
