import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageChangepasswordComponent } from './page-changepassword.component';

describe('PageChangepasswordComponent', () => {
  let component: PageChangepasswordComponent;
  let fixture: ComponentFixture<PageChangepasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageChangepasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageChangepasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
