import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Cart } from '../../models/cart.model';
import { CartItem } from '../../models/cart-item.model';
import { CartService } from '../../services/cart.service';
import { Subscription } from 'rxjs';
import { ChatComponent } from '../chat/chat.component';

@Component({
  selector: 'app-cart-sidebar',
  standalone: true,
  imports: [CommonModule, ChatComponent],
  templateUrl: './cart-sidebar.component.html',
  styleUrls: ['./cart-sidebar.component.scss']
})
export class CartSidebarComponent implements OnInit, OnDestroy {
  cart: Cart | null = null;
  private cartSubscription: Subscription | null = null;
  isChatPopoverOpen = false;
  isUpdating = false;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartSubscription = this.cartService.cart$.subscribe(cart => {
      this.isUpdating = true;
      this.cart = cart;
      setTimeout(() => this.isUpdating = false, 500);
    });
  }

  ngOnDestroy(): void {
    if (this.cartSubscription) {
      this.cartSubscription.unsubscribe();
    }
  }

  updateQuantity(productId: number, newQuantity: number): void {
    if (newQuantity > 0) {
      this.cartService.updateCartItemQuantity(productId, newQuantity).subscribe();
    }
  }

  removeItem(productId: number): void {
    this.cartService.removeFromCart(productId).subscribe();
  }

  clearCart(): void {
    this.cartService.clearCart().subscribe();
  }

  toggleChatPopover(): void {
    this.isChatPopoverOpen = !this.isChatPopoverOpen;
  }

  closeChatPopover(): void {
    this.isChatPopoverOpen = false;
  }
  
  refreshCart(): void {
    this.cartService.refreshCart();
  }
}
