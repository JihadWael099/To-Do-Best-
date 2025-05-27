import { TestBed } from '@angular/core/testing';

import { ToDoDetailsService } from './to-do-details.service';

describe('ToDoDetailsService', () => {
  let service: ToDoDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToDoDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
