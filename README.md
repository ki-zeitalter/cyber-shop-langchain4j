# Informationen zu diesem Projekt
Dieses Projekt ist eine reine Demoanwendung. Es ist kein voll funktionsfähiges Shop-System, sondern dient nur zu Demonstrationszwecken. Es ist nicht für den produktiven Einsatz gedacht.

# Spring Boot Shop-System

Dieses Projekt ist ein einfaches Shop-System, basierend auf Spring Boot mit REST-Unterstützung.

## Funktionen

- Produktkatalog aus JSON-Datei
- Warenkorb-Verwaltung (Produkte hinzufügen, entfernen, aktualisieren)
- Bestellungsverwaltung (Bestellung aufgeben, Bestellstatus aktualisieren)
- REST-API für Frontend-Integration

## Technologien

- Spring Boot 3.2.3
- Java 17
- Jackson für JSON-Verarbeitung

## Datenmodell

- **Product**: Name, Beschreibung, Bild-URL, Preis
- **Cart**: Warenkorb mit Produkten und Mengen
- **Order**: Bestellung mit Kundendaten, Produkten und Status

## Ausführen der Anwendung

Sie können die Anwendung mit Maven starten:

```bash
./mvnw spring-boot:run
```

## REST-Endpunkte

### Produkte
- `GET /api/products`: Alle Produkte abrufen
- `GET /api/products/{id}`: Ein bestimmtes Produkt abrufen

### Warenkorb
- `POST /api/cart`: Neuen Warenkorb erstellen
- `GET /api/cart/{cartId}`: Warenkorb abrufen
- `POST /api/cart/{cartId}/items`: Produkt zum Warenkorb hinzufügen
- `PUT /api/cart/{cartId}/items/{productId}`: Menge eines Produkts im Warenkorb aktualisieren
- `DELETE /api/cart/{cartId}/items/{productId}`: Produkt aus dem Warenkorb entfernen
- `DELETE /api/cart/{cartId}`: Warenkorb leeren

### Bestellungen
- `POST /api/orders`: Bestellung aufgeben
- `GET /api/orders/{orderId}`: Bestellung abrufen
- `GET /api/orders`: Alle Bestellungen abrufen
- `PUT /api/orders/{orderId}/status`: Bestellstatus aktualisieren

## Beispielanfragen

### Warenkorb erstellen

```bash
curl -X POST http://localhost:8080/api/cart
```

### Produkt zum Warenkorb hinzufügen

```bash
curl -X POST http://localhost:8080/api/cart/1/items \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'
```

### Bestellung aufgeben

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "cartId": 1,
    "customerName": "Max Mustermann",
    "customerEmail": "max@example.com",
    "shippingAddress": "Musterstraße 123, 12345 Berlin"
  }'
``` 
