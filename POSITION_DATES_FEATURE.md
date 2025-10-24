# Funkcionalnost Datuma za Pozicije

## Pregled

Implementirana je funkcionalnost automatskog otvaranja i zatvaranja pozicija na osnovu datuma početka (Datum Od) i datuma kraja (Datum Do).

## Izmene Backend-a

### 1. Position Model (`src/main/java/com/hr/model/Position.java`)

#### Dodata polja:
- `dateFrom` (LocalDate) - datum od kada je pozicija otvorena
- `dateTo` (LocalDate) - datum do kada je pozicija otvorena

#### Dodate metode:

```java
// Proverava da li pozicija treba biti otvorena na osnovu datuma
public boolean isOpenByDate() {
    if (dateFrom == null || dateTo == null) {
        return open; // Ako nema datuma, koristi manuelni status
    }
    LocalDate now = LocalDate.now();
    return !now.isBefore(dateFrom) && !now.isAfter(dateTo);
}

// Automatski ažurira status otvoreno/zatvoreno na osnovu datuma
public void updateOpenStatusByDates() {
    if (dateFrom != null && dateTo != null) {
        this.open = isOpenByDate();
    }
}
```

### 2. Position Service (`src/main/java/com/hr/service/PositionService.java`)

#### Ažurirane metode:
- `createPosition()` - automatski postavlja status na osnovu datuma
- `createPositionForCompany()` - automatski postavlja status na osnovu datuma
- `updatePosition()` - ažurira dateFrom i dateTo i automatski postavlja status

#### Nova metoda:
```java
public void updateAllPositionStatusesByDates() {
    List<Position> allPositions = positionRepository.findAll();
    for (Position position : allPositions) {
        position.updateOpenStatusByDates();
        positionRepository.save(position);
    }
}
```

### 3. Position Controller (`src/main/java/com/hr/controller/PositionController.java`)

#### Novi endpoint:
```
PUT /api/positions/update-status-by-dates
```
Ažurira sve pozicije na osnovu njihovih datuma. Koristan za periodično izvršavanje.

## Izmene Frontend-a

### PositionList Component (`frontend/src/components/PositionList.js`)

#### Izmene u tabeli:
- Dodato: kolona "Datum Od" 
- Dodato: kolona "Datum Do"
- Prikazuje "N/A" ako datumi nisu postavljeni

#### Izmene u formi:
- Dodato: input polje za "Datum Od" (type="date")
- Dodato: input polje za "Datum Do" (type="date")
- Dodata poruka ispod polja koja objašnjava da će pozicija biti automatski zatvorena van ovih datuma

## Logika Rada

1. **Kreiranje nove pozicije:**
   - Korisnik unosi datume (opciono)
   - Ako su datumi uneti, sistem automatski postavlja status open/closed
   - Pozicija je otvorena samo ako je trenutni datum između dateFrom i dateTo

2. **Ažuriranje pozicije:**
   - Korisnik može promeniti datume
   - Sistem automatski ažurira status pozicije na osnovu novih datuma

3. **Automatski status:**
   - Pre `dateFrom` → zatvorena
   - Između `dateFrom` i `dateTo` → otvorena
   - Posle `dateTo` → zatvorena

4. **Bez datuma:**
   - Ako datumi nisu postavljeni, koristi se manuelni status (checkbox)

## Primeri iz DataInitializer-a

```java
// Pozicija otvorena 3 meseca
position1.setDateFrom(LocalDate.now());
position1.setDateTo(LocalDate.now().plusMonths(3));

// Pozicija koja je istekla (zatvorena)
position3.setDateFrom(LocalDate.now().minusMonths(1));
position3.setDateTo(LocalDate.now().minusDays(1));
```

## Napomene

- Datumi su opcioni - ako nisu postavljeni, pozicija se kontroliše manuelno
- Oba datuma (dateFrom i dateTo) moraju biti postavljeni da bi automatsko zatvaranje radilo
- Status se automatski ažurira pri kreiranju i ažuriranju pozicije
- Postoji endpoint za masovno ažuriranje svih pozicija odjednom

## Testiranje

1. Pokrenite backend i frontend
2. Kreirajte novu poziciju sa datumima
3. Postavite datum kraja na sutra - pozicija će biti otvorena
4. Postavite datum kraja na juče - pozicija će biti automatski zatvorena
5. Proverite u tabeli da se datumi pravilno prikazuju

