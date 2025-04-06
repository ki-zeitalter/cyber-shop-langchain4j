import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, interval, Observable, Subscription, tap} from 'rxjs';
import {catchError, switchMap} from 'rxjs/operators';
import {Cart} from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService implements OnDestroy {
  private apiUrl = '/api/cart';
  private cartId: number | null = null;


  private cartSubject = new BehaviorSubject<Cart | null>(null);
  public cart$ = this.cartSubject.asObservable();

  private pollingSubscription: Subscription | null = null;
  private pollingInterval = 3000; // Alle 3 Sekunden aktualisieren

  constructor(private http: HttpClient) {
    this.createCart().subscribe(cart => {
      this.cartId = cart.id;
      this.cartSubject.next(cart);
      this.startPolling();
    });
  }


  ngOnDestroy(): void {
    this.stopPolling();
  }

  private startPolling(): void {
    if (this.pollingSubscription) {
      return;
    }

    this.pollingSubscription = interval(this.pollingInterval).pipe(
      switchMap(() => this.fetchCart()),
      catchError(error => {
        console.error('Fehler beim Abrufen des Warenkorbs:', error);
        return [];
      })
    ).subscribe();
  }

  private stopPolling(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
      this.pollingSubscription = null;
    }
  }

  private fetchCart(): Observable<Cart> {
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.get<Cart>(`${this.apiUrl}/${this.cartId}`).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  private createCart(): Observable<Cart> {
    return this.http.post<Cart>(this.apiUrl, {});
  }

  getCart(): Observable<Cart> {
    return this.fetchCart();
  }

  addToCart(productId: number, quantity: number): Observable<Cart> {
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
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }
    return this.http.delete<Cart>(`${this.apiUrl}/${this.cartId}/items/${productId}`).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  clearCart(): Observable<void> {
    if (!this.cartId) {
      throw new Error('Warenkorb wurde noch nicht erstellt');
    }

    // Warenkorb leeren und dann nach kurzer Verzögerung neu abrufen
    return this.http.delete<void>(`${this.apiUrl}/${this.cartId}`).pipe(
      tap(() => {
        // Zuerst mit leerem Warenkorb aktualisieren für schnelles UI-Feedback
        this.cartSubject.next({id: this.cartId!, items: [], total: 0});

        // Danach einmaligen Polling-Zyklus auslösen, um sicherzustellen, dass die Backend-Änderungen übernommen wurden
        setTimeout(() => this.fetchCart().subscribe(), 500);
      })
    );
  }

  // Funktion, um das Polling-Intervall anzupassen (optional)
  setPollingInterval(milliseconds: number): void {
    this.pollingInterval = milliseconds;
    if (this.pollingSubscription) {
      this.stopPolling();
      this.startPolling();
    }
  }

  // Funktion zum manuellen Auslösen einer Aktualisierung
  refreshCart(): void {
    this.fetchCart().subscribe();
  }

}
