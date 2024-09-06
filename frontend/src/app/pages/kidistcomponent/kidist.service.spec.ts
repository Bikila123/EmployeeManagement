import { TestBed } from '@angular/core/testing';

import { KidistService } from './kidist.service';

describe('KidistService', () => {
  let service: KidistService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KidistService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
