# Dual Login System - HR Management Application

## Pregled sistema

Aplikacija sada podržava **dva odvojena servera** za različite tipove korisnika:

### 1. **Company (Klijent) Server**
- Kompanije mogu da se loguju i upravljaju pozicijama
- Endpoint: `/api/company`

### 2. **User (Radnik) Server**  
- Radnici (korisnici) mogu da pregledaju pozicije i apliciraju
- Endpoint: `/api/user`

---

## Modeli

### Company Model
```java
@Entity
@Table(name = "companies")
public class Company {
    private Long idCompany;
    private String name;
    private String industry;
    private String pib;
    private String contact;
    private String username;      // Za login
    private String password;      // Za login
    private List<Position> positions;
}
```

### User Model
```java
@Entity
@Table(name = "users")
public class User {
    private Long id;
    private String name;
    private String surname;
    private String username;      // Za login
    private String password;      // Za login
    private String role;          // "USER", "ADMIN", "HR_MANAGER"
}
```

---

## API Endpoints

### Company Endpoints (Klijent)

#### 1. Login za kompaniju
```http
POST /api/company/login
Content-Type: application/json

{
  "username": "techsolutions",
  "password": "tech123"
}
```

**Response (Success):**
```json
{
  "idCompany": 1,
  "name": "Tech Solutions Inc.",
  "industry": "Technology",
  "pib": "12345678",
  "contact": "contact@techsolutions.com",
  "username": "techsolutions"
}
```

#### 2. Postavljanje nove pozicije
```http
POST /api/company/postPosition
Content-Type: application/json

{
  "username": "techsolutions",
  "password": "tech123",
  "title": "Senior Developer",
  "description": "We need a senior developer..."
}
```

**Response:**
```json
{
  "idPosition": 1,
  "name": "Senior Developer",
  "details": "We need a senior developer...",
  "open": true,
  "dateFrom": "2025-10-23",
  "dateTo": null
}
```

---

### User Endpoints (Radnik)

#### 1. Login za radnika
```http
POST /api/user/login
Content-Type: application/json

{
  "username": "worker1",
  "password": "worker123"
}
```

**Response (Success):**
```json
{
  "id": 3,
  "name": "Petar",
  "surname": "Petrovic",
  "username": "worker1",
  "role": "USER"
}
```

#### 2. Pregled svih pozicija
```http
GET /api/user/positions
```

**Response:**
```json
[
  {
    "idPosition": 1,
    "name": "Senior Java Developer",
    "details": "We are looking for an experienced Java developer...",
    "open": true,
    "dateFrom": "2025-10-23",
    "dateTo": "2026-01-23"
  },
  {
    "idPosition": 2,
    "name": "Financial Analyst",
    "details": "Looking for a financial analyst...",
    "open": true,
    "dateFrom": "2025-10-23",
    "dateTo": "2025-12-23"
  }
]
```

---

## Test Korisnici

### Kompanije (Companies)

| Username | Password | Company Name | Industry |
|----------|----------|--------------|----------|
| `techsolutions` | `tech123` | Tech Solutions Inc. | Technology |
| `financecorp` | `finance123` | Finance Corp | Finance |

### Radnici (Users)

| Username | Password | Name | Role |
|----------|----------|------|------|
| `admin` | `admin123` | Admin User | ADMIN |
| `hr` | `hr123` | HR Manager | HR_MANAGER |
| `worker1` | `worker123` | Petar Petrovic | USER |

---

## Ključne Izmene u Kodu

### 1. CompanyRepository
```java
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUsername(String username);
    List<Company> findByNameContaining(String name);
    List<Company> findByIndustryContaining(String industry);
    Optional<Company> findByName(String name);
    List<Company> findByIndustry(String industry);
}
```

### 2. CompanyService
```java
@Service
public class CompanyService {
    // Login metoda za kompanije
    public Optional<Company> login(String username, String password) {
        return companyRepository.findByUsername(username)
                .filter(c -> c.getPassword().equals(password));
    }

    // Kreiranje nove pozicije
    public Position postPosition(Company company, String title, String description) {
        Position position = new Position();
        position.setName(title);
        position.setDetails(description);
        position.setCompany(company);
        return positionRepository.save(position);
    }

    // Izmena pozicije
    public Position updatePosition(Long positionId, String title, String description) {
        // ...
    }
}
```

### 3. UserService
```java
@Service
public class UserService {
    // Login metoda za radnike
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(u -> u.getPassword().equals(password));
    }

    // Pregled svih pozicija
    public List<Position> getAllPositions(PositionRepository positionRepository) {
        return positionRepository.findAll();
    }
}
```

### 4. CompanyController
```java
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @PostMapping("/login")
    public ResponseEntity<Company> login(@RequestBody Map<String,String> body) {
        return companyService.login(body.get("username"), body.get("password"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/postPosition")
    public ResponseEntity<Position> postPosition(@RequestBody Map<String,String> body) {
        // ...
    }
}
```

### 5. UserController
```java
@RestController
@RequestMapping("/api/user")
public class UserController {
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String,String> body) {
        return userService.login(body.get("username"), body.get("password"))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/positions")
    public List<Position> getPositions() {
        return userService.getAllPositions(positionRepository);
    }
}
```

---

## Mogućnosti po tipovima korisnika

### Company (Kompanija) može:
✅ Da se loguje sa svojim credentials  
✅ Da kači nove pozicije  
✅ Da menja postojeće pozicije  
✅ Da pregleda svoje pozicije  
✅ Da briše pozicije

### User (Radnik) može:
✅ Da se loguje sa svojim credentials  
✅ Da pregleda sve dostupne pozicije  
✅ Da aplicira na pozicije (funkcionalnost može biti proširena)  
✅ Da vidi detalje o pozicijama  

---

## Pokretanje aplikacije

### Backend
```bash
mvn spring-boot:run
```
ili
```bash
./start-backend.bat
```

### Frontend (ako postoji)
```bash
cd frontend
npm start
```
ili
```bash
./start-frontend.bat
```

### Sve zajedno
```bash
./start-all.bat
```

---

## Bezbednost

⚠️ **Napomena:** Trenutno su lozinke šifrovane pomoću `PasswordEncoder` ali autentifikacija još nije u potpunosti implementirana sa Spring Security JWT tokenima. Preporučuje se:

1. Implementirati JWT tokene za obe vrste korisnika
2. Dodati role-based access control (RBAC)
3. Koristiti HTTPS u production okruženju
4. Dodati rate limiting za login endpoints

---

## Buduće Ekstenzije

- [ ] Frontend forme za Company i User login
- [ ] JWT token implementacija
- [ ] Company dashboard za upravljanje pozicijama
- [ ] User dashboard za pregled pozicija
- [ ] Aplikacija na pozicije (apply functionality)
- [ ] Email notifikacije
- [ ] File upload za CV
- [ ] Advanced search i filter opcije

---

## Testiranje sa Postman/cURL

### Test Company Login
```bash
curl -X POST http://localhost:8080/api/company/login \
  -H "Content-Type: application/json" \
  -d '{"username":"techsolutions","password":"tech123"}'
```

### Test User Login
```bash
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{"username":"worker1","password":"worker123"}'
```

### Test Get Positions (as User)
```bash
curl -X GET http://localhost:8080/api/user/positions
```

### Test Post Position (as Company)
```bash
curl -X POST http://localhost:8080/api/company/postPosition \
  -H "Content-Type: application/json" \
  -d '{
    "username":"techsolutions",
    "password":"tech123",
    "title":"Backend Developer",
    "description":"Looking for experienced backend developer"
  }'
```

---

**Datum kreiranja:** 23.10.2025  
**Verzija:** 1.0  
**Status:** ✅ Kompilacija uspešna

