import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSidebarSearchComponent } from './user-sidebar-search.component';

describe('UserSidebarSearchComponent', () => {
  let component: UserSidebarSearchComponent;
  let fixture: ComponentFixture<UserSidebarSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserSidebarSearchComponent]
    });
    fixture = TestBed.createComponent(UserSidebarSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
