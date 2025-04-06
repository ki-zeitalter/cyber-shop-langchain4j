import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from './services/product.service';
import { CartService } from './services/cart.service';
import { Product } from './models/product.model';
import { HeaderComponent } from './components/header/header.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { CartSidebarComponent } from './components/cart-sidebar/cart-sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    ProductCardComponent,
    ProductDetailComponent,
    CartSidebarComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  products: Product[] = [];
  selectedProduct: Product | null = null;
  showProductDetail = false;
  
  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}
  
  ngOnInit(): void {
    this.loadProducts();
  }
  
  loadProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
    });
  }
  
  onShowProductDetails(product: Product): void {
    this.selectedProduct = product;
    this.showProductDetail = true;
  }
  
  onCloseProductDetails(): void {
    this.showProductDetail = false;
  }
  
  onAddToCart(product: Product): void {
    this.cartService.addToCart(product.id, 1).subscribe();
  }
  
  onAddToCartWithQuantity(data: {product: Product, quantity: number}): void {
    this.cartService.addToCart(data.product.id, data.quantity).subscribe(() => {
      this.showProductDetail = false;
    });
  }
}
