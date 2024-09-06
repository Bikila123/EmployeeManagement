import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageParentMenuComponent } from './manage-parent-menu.component';

describe('ManageParentMenuComponent', () => {
  let component: ManageParentMenuComponent;
  let fixture: ComponentFixture<ManageParentMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageParentMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageParentMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
