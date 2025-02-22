import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectcityComponent } from './selectcity.component';

describe('SelectcityComponent', () => {
  let component: SelectcityComponent;
  let fixture: ComponentFixture<SelectcityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectcityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectcityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
