import { Component,OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { PriceService } from 'src/app/services/price.service';
@Component({
  selector: 'app-payment-dialog',
  templateUrl: './payment-dialog.component.html',
  styleUrls: ['./payment-dialog.component.scss']
})
export class PaymentDialogComponent implements OnInit {
  totalAmount: number ; // Example amount
  tempAmount:number;
  constructor(public dialogRef: MatDialogRef<PaymentDialogComponent>,private priceService:PriceService) {}
  ngOnInit(): void {
    
      this.totalAmount=this.priceService.getPrice();
      this.tempAmount=Number(localStorage.getItem('price'))||null;
      localStorage.setItem('price', this.totalAmount.toString());

  }
  confirmPayment() {
    localStorage.removeItem('price');
    this.dialogRef.close('confirmed');
  }

  cancel() {
    
    this.dialogRef.close('manualclose');
  }
}