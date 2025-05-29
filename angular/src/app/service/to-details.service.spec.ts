import { TestBed } from '@angular/core/testing';

import { ToDetailsService } from './to-details.service';

describe('ToDetailsService', () => {
  let service: ToDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
