# ⚡ Quick Start - Dual Login System

## Brza instalacija i pokretanje

### 1️⃣ Instaliraj frontend zavisnosti

```bash
cd frontend
npm install
```

### 2️⃣ Pokreni backend (iz root-a)

```bash
mvn spring-boot:run
```

### 3️⃣ Pokreni frontend (iz root-a)

```bash
cd frontend
npm start
```

### 4️⃣ Otvori browser

```
http://localhost:3000/login
```

---

## 🔑 Test nalozi

### Kompanija (Klijent):
```
Username: techsolutions
Password: tech123
```

### Radnik:
```
Username: worker1
Password: worker123
```

---

## ✨ Šta radi svaki tip korisnika?

| Kompanija (Klijent) | Radnik |
|---------------------|---------|
| ➕ Kači pozicije | 📊 Vidi sve kompanije |
| ✏️ Menja pozicije | 📋 Vidi sve pozicije |
| 🗑️ Briše pozicije | 👥 Upravlja kandidatima |
| 👁️ Pregleda svoje pozicije | 🤝 Upravlja intervjuima |

---

## 🎯 Brz test

1. Klikni **"Login za Klijenta"**
2. Unesi: `techsolutions` / `tech123`
3. Klikni **"Dodaj novu poziciju"**
4. Popuni i sačuvaj
5. **Logout**
6. Klikni **"Login za Radnika"**
7. Unesi: `worker1` / `worker123`
8. Idi na **"Positions"** - vidi poziciju koju je kompanija dodala!

---

## ❗ Problem?

**Backend ne radi?**
- Proveri da li je MySQL pokrenut
- Proveri `application.yml` za database credentials

**Frontend ne radi?**
- Uradi `npm install` u `frontend` folderu
- Proveri da li backend radi na port 8080

**Login ne radi?**
- Proveri konzolu backend-a - trebalo bi da vidiš "Company created: ..."
- Proveri da li je baza inicijalizovana

---

**Više detalja:** Pogledaj `DUAL_LOGIN_FRONTEND_GUIDE.md`

