import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddCityFormComponent } from './add-city-form/add-city-form.component';
import { AddTheatreFormComponent } from './add-theatre-form/add-theatre-form.component';



@NgModule({
  declarations: [
    AddCityFormComponent,
    AddTheatreFormComponent
  ],
  imports: [
    CommonModule
  ]
})
export class AdminModule { }
