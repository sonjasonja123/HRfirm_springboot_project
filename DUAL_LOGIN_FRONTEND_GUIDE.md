# Frontend Dual Login System - Uputstvo za pokretanje

## 📋 Pregled

Aplikacija sada ima **dva odvojena sistema za prijavu**:
1. **Login za Kompanije** - Kompanije mogu da upravljaju svojim pozicijama
2. **Login za Radnike** - Radnici mogu da pregledaju sve funkcionalnosti (kompanije, pozicije, kandidate, intervjue)

---

## 🚀 Pokretanje aplikacije

### 1. Instalacija zavisnosti (prvi put)

```bash
cd frontend
npm install
```

Ovo će instalirati sve potrebne pakete uključujući:
- `react-icons` - Za ikone
- `react-router-dom` - Za rutiranje
- `react-bootstrap` - Za UI komponente
- `axios` - Za API pozive
- `react-toastify` - Za notifikacije

### 2. Pokretanje Backend servera

U root direktorijumu projekta:

```bash
mvn spring-boot:run
```

ili koristite script:

```bash
./start-backend.bat
```

Backend će biti pokrenut na: `http://localhost:8080`

### 3. Pokretanje Frontend servera

U `frontend` direktorijumu:

```bash
npm start
```

ili koristite script iz root-a:

```bash
./start-frontend.bat
```

Frontend će biti pokrenut na: `http://localhost:3000`

---

## 🎯 Tok aplikacije

### Početna stranica (`/login`)

Kada pokrenete aplikaciju, prikazaće se **početna stranica za izbor** sa dva dugmeta:

1. **Login za Klijenta** (Kompanija) - Plavo dugme sa ikonom firme
2. **Login za Radnika** - Zeleno dugme sa ikonom korisnika

### Za Kompaniju:

1. Kliknite na **"Login za Klijenta"**
2. Unesite kredencijale:
   - Username: `techsolutions`
   - Password: `tech123`
   
   ili
   
   - Username: `financecorp`
   - Password: `finance123`

3. Nakon uspešne prijave, preusmeriće vas na **Company Dashboard** (`/company/dashboard`)

**Mogućnosti kompanije:**
- ✅ Pregled svih svojih pozicija
- ✅ Dodavanje novih pozicija
- ✅ Izmena postojećih pozicija
- ✅ Brisanje pozicija

### Za Radnika:

1. Kliknite na **"Login za Radnika"**
2. Unesite kredencijale:
   - Username: `worker1`
   - Password: `worker123`
   
   ili
   
   - Username: `hr`
   - Password: `hr123`
   
   ili
   
   - Username: `admin`
   - Password: `admin123`

3. Nakon uspešne prijave, preusmeriće vas na **Worker Dashboard** (`/`)

**Mogućnosti radnika:**
- ✅ Dashboard sa statistikama
- ✅ Pregled svih kompanija
- ✅ Pregled svih pozicija
- ✅ Upravljanje kandidatima
- ✅ Upravljanje intervjuima

---

## 📁 Struktura novih komponenti

### Nove komponente:

```
frontend/src/components/
├── LoginSelection.js     # Početna stranica za izbor tipa logina
├── CompanyLogin.js       # Login forma za kompanije
├── WorkerLogin.js        # Login forma za radnike
└── CompanyDashboard.js   # Dashboard za kompanije
```

### Ažurirane komponente:

```
frontend/src/components/
├── Navbar.js            # Ažuriran sa različitim opcijama za kompanije i radnike
├── ProtectedRoute.js    # Ažuriran sa provjerom tipa korisnika
└── App.js               # Ažuriran sa novim rutama
```

### Ažurirani servisi:

```
frontend/src/services/
└── authService.js       # Dodate metode loginCompany() i loginWorker()
```

---

## 🔐 Autentifikacija

### Kako radi:

1. **Kompanija se prijavljuje:**
   - Poziva se `/api/company/login`
   - Čuva se tip korisnika: `company`
   - Redirect na `/company/dashboard`

2. **Radnik se prijavljuje:**
   - Poziva se `/api/user/login`
   - Čuva se tip korisnika: `worker`
   - Redirect na `/` (worker dashboard)

### LocalStorage podaci:

```javascript
{
  "jwtToken": "company-1" ili "worker-3",
  "userType": "company" ili "worker",
  "userData": { ... podaci o korisniku ... }
}
```

---

## 🎨 UI/UX Features

### Početna stranica:
- Atraktivna pozadinska slika
- Dva velika, interaktivna kartice
- Hover efekti (skaliranje pri prelazku mišem)
- Ikone za vizuelnu identifikaciju (🏢 Kompanija, 👤 Radnik)

### Login forme:
- Odvojene forme sa različitim bojama (plava za kompaniju, zelena za radnika)
- "Nazad na izbor" dugme za povratak
- Test kredencijali prikazani ispod forme
- Validacija polja

### Navbar:
- Dinamički prikazuje opcije zavisno od tipa korisnika
- Prikazuje ime trenutno ulogovanog korisnika
- Logout dugme

### Company Dashboard:
- Tabela sa svim pozicijama kompanije
- Modal forma za dodavanje/izmenu pozicija
- Akcije: Izmeni, Obriši
- Status badge (Otvorena/Zatvorena pozicija)
- Pregledno prikazan detalji pozicija

---

## 🔧 API Endpoints

### Company Endpoints:

```
POST   /api/company/login                    # Login za kompaniju
POST   /api/company/postPosition             # Kreiranje nove pozicije
GET    /api/company/positions?username=...&password=...  # Dohvatanje pozicija kompanije
PUT    /api/company/positions/:id            # Izmena pozicije
DELETE /api/positions/:id                    # Brisanje pozicije
```

### Worker Endpoints:

```
POST   /api/user/login                       # Login za radnika
GET    /api/user/positions                   # Dohvatanje svih pozicija
GET    /api/companies                        # Dohvatanje svih kompanija
GET    /api/candidates                       # Dohvatanje svih kandidata
GET    /api/interviews                       # Dohvatanje svih intervjua
... (sve ostale worker funkcionalnosti)
```

---

## 🐛 Debugging

### Backend logovi:

Pratite konzolu gde je pokrenut Spring Boot server. Trebali biste videti:

```
Admin user created: username=admin, password=admin123
HR user created: username=hr, password=hr123
Worker user created: username=worker1, password=worker123
Company created: username=techsolutions, password=tech123
Company created: username=financecorp, password=finance123
Sample data initialized successfully!
```

### Frontend logovi:

Otvorite browser Developer Tools (F12) i pratite Console tab za debug poruke.

### Česte greške:

1. **"Cannot read property of undefined"** - Proverite da li su sve zavisnosti instalirane (`npm install`)

2. **CORS greška** - Proverite da li backend ima `@CrossOrigin(origins = "http://localhost:3000")`

3. **401 Unauthorized** - Proverite da li su kredencijali ispravni

4. **Pozicije se ne učitavaju** - Proverite da li kompanija ima pozicije u bazi

---

## 📦 Dependencies

Sve potrebne zavisnosti su u `package.json`:

```json
{
  "react-icons": "^4.12.0",
  "react-router-dom": "^6.8.1",
  "react-bootstrap": "^2.7.2",
  "axios": "^1.3.4",
  "react-toastify": "^9.1.1"
}
```

---

## ✅ Checklist pre pokretanja

- [ ] Backend je pokrenut (`mvn spring-boot:run`)
- [ ] MySQL baza je pokrenuta i dostupna
- [ ] Frontend dependencies su instalirane (`npm install`)
- [ ] Frontend je pokrenut (`npm start`)
- [ ] Browser je otvoren na `http://localhost:3000`
- [ ] Vidite početnu stranicu sa dva dugmeta

---

## 🎉 Testiranje

### Test scenario 1: Kompanija

1. Idi na `http://localhost:3000/login`
2. Klikni "Login za Klijenta"
3. Unesi: `techsolutions` / `tech123`
4. Trebalo bi da vidiš Company Dashboard sa postojećim pozicijama
5. Klikni "Dodaj novu poziciju"
6. Popuni formu i sačuvaj
7. Nova pozicija bi trebalo da se pojavi u tabeli
8. Klikni "Izmeni" na poziciji i promeni podatke
9. Klikni "Logout"

### Test scenario 2: Radnik

1. Idi na `http://localhost:3000/login`
2. Klikni "Login za Radnika"
3. Unesi: `worker1` / `worker123`
4. Trebalo bi da vidiš Worker Dashboard
5. Klikni na "Companies" u navbar-u
6. Trebalo bi da vidiš listu kompanija
7. Klikni na "Positions" - vidi sve pozicije (uključujući one koje je kompanija dodala)
8. Testiraj ostale funkcionalnosti (Candidates, Interviews)
9. Klikni "Logout"

---

## 📝 Napomene

- **Lozinke nisu bezbedne** - U proizvodnji koristite JWT tokene i enkripciju
- **API pozivi za pozicije** - Trenutno šaljemo username i password u query parametrima (privremeno rešenje)
- **Navbar se ne prikazuje** na login stranicama
- **Protected Routes** automatski proveravaju tip korisnika i preusmeravaju na odgovarajuću stranicu

---

## 🔮 Buduće ekstenzije

- [ ] JWT token implementacija
- [ ] Refresh token mehanizam
- [ ] Password reset funkcionalnost
- [ ] Email verifikacija
- [ ] Two-factor authentication
- [ ] Real-time notifikacije
- [ ] File upload za CV
- [ ] Advanced filtering i search
- [ ] Export data u PDF/Excel

---

**Verzija:** 1.0  
**Datum:** 23.10.2025  
**Status:** ✅ Sve funkcioniše

