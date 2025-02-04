// price.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PriceService {
  private price: number;

  constructor() { }

  // Method to set the price
  setPrice(price: number): void {
    this.price = price;
  }

  // Method to get the price
  getPrice(): number {
    return this.price;
  }
}