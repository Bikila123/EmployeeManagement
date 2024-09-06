import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageManageuserComponent } from './page-manageuser.component';

describe('PageManageuserComponent', () => {
  let component: PageManageuserComponent;
  let fixture: ComponentFixture<PageManageuserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageManageuserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageManageuserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
