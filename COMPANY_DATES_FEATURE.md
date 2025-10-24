# Datumi za Pozicije - Funkcionalnost za Kompanije

## Pregled

Kompanije sada mogu da postavljaju datum početka (Datum Od) i datum kraja (Datum Do) prilikom kreiranja i editovanja pozicija. Pozicije se automatski zatvaraju van ovih datuma.

## Izmene Backend-a

### 1. CompanyService (`src/main/java/com/hr/service/CompanyService.java`)

#### Ažurirana metoda `postPosition`:
```java
public Position postPosition(Company company, String title, String description, 
                            LocalDate dateFrom, LocalDate dateTo) {
    Position position = new Position();
    position.setName(title);
    position.setDetails(description);
    position.setCompany(company);
    position.setDateFrom(dateFrom);
    position.setDateTo(dateTo);
    // Automatski ažurira status na osnovu datuma
    position.updateOpenStatusByDates();
    return positionRepository.save(position);
}
```

#### Ažurirana metoda `updatePosition`:
```java
public Position updatePosition(Long positionId, String title, String description,
                              LocalDate dateFrom, LocalDate dateTo) {
    // ... update logic
    position.setDateFrom(dateFrom);
    position.setDateTo(dateTo);
    position.updateOpenStatusByDates();
    return positionRepository.save(position);
}
```

### 2. CompanyController (`src/main/java/com/hr/controller/CompanyController.java`)

#### Ažuriran endpoint `POST /api/company/postPosition`:
```java
@PostMapping("/postPosition")
public ResponseEntity<Position> postPosition(@RequestBody Map<String,String> body){
    // Parse dates if provided
    LocalDate dateFrom = body.get("dateFrom") != null && !body.get("dateFrom").isEmpty() 
        ? LocalDate.parse(body.get("dateFrom")) 
        : null;
    LocalDate dateTo = body.get("dateTo") != null && !body.get("dateTo").isEmpty() 
        ? LocalDate.parse(body.get("dateTo")) 
        : null;
    
    Position position = companyService.postPosition(company, 
        body.get("title"), body.get("description"), dateFrom, dateTo);
    return ResponseEntity.ok(position);
}
```

#### Ažuriran endpoint `PUT /api/company/positions/{id}`:
- Parsira i prosleđuje dateFrom i dateTo vrednosti
- Automatski ažurira status pozicije

## Izmene Frontend-a

### CompanyDashboard (`frontend/src/components/CompanyDashboard.js`)

#### Dodato u state:
```javascript
const [formData, setFormData] = useState({
    title: '',
    description: '',
    dateFrom: '',  // NOVO
    dateTo: ''     // NOVO
});
```

#### Prikazivanje u tabeli:
Tabela već prikazuje kolone:
- **Datum Od** - prikazuje dateFrom ili "N/A"
- **Datum Do** - prikazuje dateTo ili "N/A"

#### Forma za kreiranje/editovanje:
Dodati input polja:

```javascript
<Row>
    <Col md={6}>
        <Form.Group className="mb-3">
            <Form.Label>Datum od</Form.Label>
            <Form.Control
                type="date"
                value={formData.dateFrom}
                onChange={(e) => setFormData({ ...formData, dateFrom: e.target.value })}
            />
            <Form.Text className="text-muted">
                Pozicija će biti otvorena od ovog datuma
            </Form.Text>
        </Form.Group>
    </Col>
    <Col md={6}>
        <Form.Group className="mb-3">
            <Form.Label>Datum do</Form.Label>
            <Form.Control
                type="date"
                value={formData.dateTo}
                onChange={(e) => setFormData({ ...formData, dateTo: e.target.value })}
            />
            <Form.Text className="text-muted">
                Pozicija će biti automatski zatvorena nakon ovog datuma
            </Form.Text>
        </Form.Group>
    </Col>
</Row>
```

## Kako Funkcioniše

### Kreiranje Nove Pozicije
1. Kompanija klikne na "Dodaj novu poziciju"
2. Unese naziv i opis
3. **NOVO**: Opciono unese "Datum od" i "Datum do"
4. Klikne "Kreiraj poziciju"
5. Backend automatski postavlja status open/closed na osnovu datuma

### Editovanje Postojeće Pozicije
1. Kompanija klikne na edit dugme
2. Vidi postojeće datume (ako su postavljeni)
3. Može ih promeniti ili ostaviti praznim
4. Sačuva izmene
5. Status se automatski ažurira

### Automatski Status
- **Pre `dateFrom`**: Pozicija je zatvorena
- **Između `dateFrom` i `dateTo`**: Pozicija je otvorena
- **Posle `dateTo`**: Pozicija je zatvorena
- **Bez datuma**: Status se ne menja (koristi se manuelni status)

## Primer

```javascript
// Kompanija kreira poziciju
{
  title: "Senior Java Developer",
  description: "Looking for experienced Java developer...",
  dateFrom: "2025-10-24",   // Od danas
  dateTo: "2026-01-24"      // Za 3 meseca
}

// Rezultat:
// - Status: Otvorena (jer je trenutni datum između dateFrom i dateTo)
// - Automatski će biti zatvorena 25.01.2026
```

## API Format

### POST Request Body:
```json
{
  "username": "techsolutions",
  "password": "tech123",
  "title": "Senior Java Developer",
  "description": "Job description...",
  "dateFrom": "2025-10-24",    // Optional, ISO date format (YYYY-MM-DD)
  "dateTo": "2026-01-24"       // Optional, ISO date format (YYYY-MM-DD)
}
```

### PUT Request Body:
```json
{
  "title": "Senior Java Developer",
  "description": "Updated description...",
  "dateFrom": "2025-10-24",    // Optional
  "dateTo": "2026-01-24"       // Optional
}
```

## Testiranje

1. **Pokrenite aplikaciju:**
   ```bash
   # Backend
   mvn spring-boot:run
   
   # Frontend
   cd frontend
   npm start
   ```

2. **Ulogujte se kao kompanija:**
   - Username: `techsolutions`
   - Password: `tech123`

3. **Kreirajte poziciju sa datumima:**
   - Dodajte novu poziciju
   - Postavite datum početka na danas
   - Postavite datum kraja na datum u budućnosti
   - Proverite da je status "Otvorena"

4. **Proverite automatsko zatvaranje:**
   - Editujte poziciju
   - Postavite datum kraja na juče
   - Proverite da je status sada "Zatvorena"

## Napomene

- Datumi su **opcioni** - ako nisu postavljeni, pozicija radi kao pre
- Oba datuma treba postaviti da bi automatsko zatvaranje radilo
- Format datuma: YYYY-MM-DD (ISO format)
- Datumi se čuvaju kao LocalDate u bazi podataka
- Status se automatski ažurira pri svakom kreiranju/ažuriranju

## Kompatibilnost

✅ Kompatibilno sa postojećim pozicijama bez datuma
✅ Backend se uspešno kompajlira
✅ Frontend nema linter grešaka
✅ Postojeći test podaci u DataInitializer već imaju datume

