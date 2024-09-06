import { TestBed } from '@angular/core/testing';

import { PageUserService } from '../page-manageuser/page-user.service';

describe('PageUserService', () => {
  let service: PageUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
