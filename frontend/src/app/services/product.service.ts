import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = '/api/products';
  
  // Testdaten für die Entwicklung
  private mockProducts: Product[] = [
    {
      id: 1,
      name: 'Laptop',
      description: 'Hochleistungs-Laptop mit 16GB RAM und 512GB SSD',
      imageUrl: 'https://via.placeholder.com/300x200?text=Laptop',
      price: 1299.99
    },
    {
      id: 2,
      name: 'Smartphone',
      description: 'Neuestes Smartphone-Modell mit 128GB Speicher',
      imageUrl: 'https://via.placeholder.com/300x200?text=Smartphone',
      price: 799.99
    },
    {
      id: 3,
      name: 'Bluetooth-Kopfhörer',
      description: 'Kabellose Kopfhörer mit Geräuschunterdrückung',
      imageUrl: 'https://via.placeholder.com/300x200?text=Kopfhörer',
      price: 149.99
    },
    {
      id: 4,
      name: 'Smartwatch',
      description: 'Wasserresistente Smartwatch mit Fitness-Tracking',
      imageUrl: 'https://via.placeholder.com/300x200?text=Smartwatch',
      price: 249.99
    },
    {
      id: 5,
      name: 'Tablet',
      description: '10-Zoll Tablet mit Retina-Display',
      imageUrl: 'https://via.placeholder.com/300x200?text=Tablet',
      price: 499.99
    }
  ];

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    // Für Entwicklungszwecke Mock-Daten zurückgeben
    // return of(this.mockProducts);
    
    // Für Produktionsumgebung:
    return this.http.get<Product[]>(this.apiUrl);
  }

  getProductById(id: number): Observable<Product> {
    // Für Entwicklungszwecke Mock-Daten zurückgeben
    // const product = this.mockProducts.find(p => p.id === id);
    // if (product) {
    //   return of(product);
    // }
    // throw new Error(`Produkt mit ID ${id} nicht gefunden`);
    
    // Für Produktionsumgebung:
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }
} 