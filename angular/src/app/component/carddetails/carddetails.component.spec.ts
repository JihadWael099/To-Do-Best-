import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarddetailsComponent } from './carddetails.component';

describe('CarddetailsComponent', () => {
  let component: CarddetailsComponent;
  let fixture: ComponentFixture<CarddetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarddetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarddetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
