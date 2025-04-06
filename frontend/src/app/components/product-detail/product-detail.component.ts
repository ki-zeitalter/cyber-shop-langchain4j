import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Product } from '../../models/product.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent {
  @Input() product: Product | null = null;
  @Input() visible = false;
  @Output() close = new EventEmitter<void>();
  @Output() addToCart = new EventEmitter<{product: Product, quantity: number}>();
  
  quantity = 1;

  onClose(event?: Event): void {
    if (event) {
      event.stopPropagation();
    }
    this.close.emit();
  }

  onAddToCart(): void {
    if (this.product) {
      this.addToCart.emit({
        product: this.product,
        quantity: this.quantity
      });
    }
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }
}
