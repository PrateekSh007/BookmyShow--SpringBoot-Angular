import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTheatreFormComponent } from './add-theatre-form.component';

describe('AddTheatreFormComponent', () => {
  let component: AddTheatreFormComponent;
  let fixture: ComponentFixture<AddTheatreFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddTheatreFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddTheatreFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
