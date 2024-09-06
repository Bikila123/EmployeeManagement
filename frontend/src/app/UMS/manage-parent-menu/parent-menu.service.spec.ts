import { TestBed } from '@angular/core/testing';

import { ParentMenuService } from './parent-menu.service';

describe('ParentMenuService', () => {
  let service: ParentMenuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParentMenuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
