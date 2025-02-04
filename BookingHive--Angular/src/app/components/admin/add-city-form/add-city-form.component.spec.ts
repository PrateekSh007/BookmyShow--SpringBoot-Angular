import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCityFormComponent } from './add-city-form.component';

describe('AddCityFormComponent', () => {
  let component: AddCityFormComponent;
  let fixture: ComponentFixture<AddCityFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCityFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCityFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
