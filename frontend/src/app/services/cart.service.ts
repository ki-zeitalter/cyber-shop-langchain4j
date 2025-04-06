import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Cart } from '../models/cart.model';
import { CartItem } from '../models/cart-item.model';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = '/api/cart';
  private cartId: number | null = null;
  
  // Mock-Warenkorb für die Entwicklung
  private cart: Cart = {
    id: 1,
    items: [],
    total: 0
  };
  
  private cartSubject = new BehaviorSubject<Cart | null>(null);
  public cart$ = this.cartSubject.asObservable();

  constructor(private http: HttpClient) {
    // Für die Entwicklung: Leeren Warenkorb initialisieren
    // this.cartSubject.next(this.cart);
    
    // Für Produktionsumgebung:
    this.createCart().subscribe(cart => {
      this.cartId = cart.id;
      this.cartSubject.next(cart);
    });
  }

  private createCart(): Observable<Cart> {
    // Für Entwicklungszwecke:
    // return of(this.cart);
    
    // Für Produktionsumgebung:
    return this.http.post<Cart>(this.apiUrl, {});
  }

  getCart(): Observable<Cart> {
    // Für Entwicklungszwecke:
    // return of(this.cart);
    
    // Für Produktionsumgebung:
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.get<Cart>(`${this.apiUrl}/${this.cartId}`).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  addToCart(productId: number, quantity: number): Observable<Cart> {
    // Für Entwicklungszwecke: Produkt simulieren und zum Warenkorb hinzufügen
    // const mockProduct: Product = {
    //   id: productId,
    //   name: `Produkt ${productId}`,
    //   description: 'Produktbeschreibung',
    //   imageUrl: `https://via.placeholder.com/300x200?text=Produkt_${productId}`,
    //   price: 99.99
    // };
    
    // // Prüfen, ob das Produkt bereits im Warenkorb ist
    // const existingItemIndex = this.cart.items.findIndex(item => item.product.id === productId);
    
    // if (existingItemIndex >= 0) {
    //   // Wenn ja, Menge erhöhen
    //   this.cart.items[existingItemIndex].quantity += quantity;
    // } else {
    //   // Wenn nicht, neues Item hinzufügen
    //   this.cart.items.push({
    //     product: mockProduct,
    //     quantity
    //   });
    // }
    
    // // Gesamtsumme berechnen
    // this.recalculateTotal();
    
    // // Warenkorb-Observable aktualisieren
    // this.cartSubject.next(this.cart);
    
    // return of(this.cart);
    
    // Für Produktionsumgebung:
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.post<Cart>(`${this.apiUrl}/${this.cartId}/items`, {
      productId,
      quantity
    }).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  updateCartItemQuantity(productId: number, quantity: number): Observable<Cart> {
    // Für Entwicklungszwecke:
    // const itemIndex = this.cart.items.findIndex(item => item.product.id === productId);
    
    // if (itemIndex >= 0) {
    //   this.cart.items[itemIndex].quantity = quantity;
    //   this.recalculateTotal();
    //   this.cartSubject.next(this.cart);
    // }
    
    // return of(this.cart);
    
    // Für Produktionsumgebung:
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.put<Cart>(`${this.apiUrl}/${this.cartId}/items/${productId}`, {
      quantity
    }).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  removeFromCart(productId: number): Observable<Cart> {
    // Für Entwicklungszwecke:
    // this.cart.items = this.cart.items.filter(item => item.product.id !== productId);
    // this.recalculateTotal();
    // this.cartSubject.next(this.cart);
    
    // return of(this.cart);
    
    // Für Produktionsumgebung:
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.delete<Cart>(`${this.apiUrl}/${this.cartId}/items/${productId}`).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  clearCart(): Observable<void> {
    // Für Entwicklungszwecke:
    // this.cart.items = [];
    // this.cart.total = 0;
    // this.cartSubject.next(this.cart);
    
    // return of(undefined);
    
    // Für Produktionsumgebung:
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.delete<void>(`${this.apiUrl}/${this.cartId}`).pipe(
      tap(() => this.cartSubject.next({ id: this.cartId!, items: [], total: 0 }))
    );
  }
  
  // Hilfsmethode zur Berechnung der Gesamtsumme - nicht mehr benötigt für Backend-Modus
  // private recalculateTotal(): void {
  //   this.cart.total = this.cart.items.reduce(
  //     (sum, item) => sum + (item.product.price * item.quantity), 
  //     0
  //   );
  // }
} 