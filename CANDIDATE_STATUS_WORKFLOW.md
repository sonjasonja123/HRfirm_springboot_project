# Candidate Status Workflow - Dokumentacija

## Pregled

Status kandidata sada može da se menja **SAMO iz Interview kartice** korišćenjem Hire ili Reject dugmadi. Kandidati se više ne mogu direktno zapošljavati ili odbijati iz Candidates kartice.

## 🔄 Tok Rada (Workflow)

### 1. Dodavanje Novog Kandidata
1. Idi na **Candidates** karticu
2. Klikni "Add Candidate"
3. Popuni formu:
   - **Name** - Ime kandidata
   - **Surname** - Prezime kandidata
   - **Email** - Email adresa
   - **Phone** - Broj telefona
   - **Status** - Automatski postavljen na "Active" (read-only)
4. Klikni "Create"
5. ✅ Kandidat je kreiran sa statusom **Active**

### 2. Editovanje Kandidata
1. Idi na **Candidates** karticu
2. Klikni "Edit" pored kandidata
3. Možeš editovati:
   - ✅ Name (Ime)
   - ✅ Surname (Prezime)
   - ✅ Email
   - ✅ Phone (Telefon)
   - ❌ **Status** - NE MOŽE SE EDITOVATI (prikazano kao read-only)
4. Klikni "Update"
5. ✅ Kandidat je ažuriran (status ostaje isti)

### 3. Zakazivanje Intervjua
1. Idi na **Interviews** karticu
2. Klikni "Add Interview"
3. Izaberi kandidata sa statusom "Active"
4. Izaberi otvorenu poziciju
5. Postavi datum i vreme
6. Klikni "Create"
7. ✅ Intervju je zakazan

### 4. Završavanje Intervjua i Promena Statusa

#### Opcija A: Zapošljavanje Kandidata (Hire)
1. Idi na **Interviews** karticu
2. Označi intervju kao "Completed"
3. Klikni **"Hire"** dugme
4. Potvrdi akciju
5. ✅ Automatski:
   - Status kandidata → **Hired**
   - Status pozicije → **Closed** (zatvorena)
   - Status intervjua → **Completed**

#### Opcija B: Odbijanje Kandidata (Reject)
1. Idi na **Interviews** karticu
2. Označi intervju kao "Completed"
3. Klikni **"Reject"** dugme
4. Potvrdi akciju
5. ✅ Automatski:
   - Status kandidata → **Rejected**
   - Status intervjua → **Canceled**
   - Pozicija ostaje **otvorena**

## 📊 Statusи Kandidata

| Status | Opis | Kako se dobija |
|--------|------|----------------|
| **Active** | Kandidat je aktivan, spreman za intervjue | Default status pri kreiranju |
| **Hired** | Kandidat je zaposlen | Klikom na "Hire" u Interview kartici |
| **Rejected** | Kandidat je odbijen | Klikom na "Reject" u Interview kartici |

## 🚫 Šta Je Uklonjeno iz Candidates Kartice

### Uklonjeno:
❌ **Hire dugme** - Ne postoji više u Candidates kartici  
❌ **Reject dugme** - Ne postoji više u Candidates kartici  
❌ **Editovanje statusa** - Status polje je read-only

### Zadržano:
✅ **Edit dugme** - Za editovanje imena, prezimena, email-a, telefona  
✅ **Delete dugme** - Za brisanje kandidata  
✅ **Pregled statusa** - Status se prikazuje, ali ne može se menjati

## 🎯 Interview Kartica - Akcije

### Dugmad u Interview Kartici:

1. **Edit** - Edituj detalje intervjua
2. **Complete** - Označi intervju kao završen
3. **Cancel** - Otkaži intervju
4. **Add Round** - Dodaj rundu intervjua
5. **Hire** - Zaposli kandidata (enabled samo ako je intervju Completed)
6. **Reject** - Odbij kandidata (enabled samo ako je intervju Completed)
7. **Delete** - Obriši intervju

### Pravila za Hire/Reject:

**Hire dugme:**
- ✅ Enabled kada je intervju status: **Completed**
- ✅ Enabled kada je pozicija: **Open**
- ❌ Disabled u svim ostalim slučajevima

**Reject dugme:**
- ✅ Enabled kada je intervju status: **Completed**
- ❌ Disabled u svim ostalim slučajevima

## 📋 Tabela u Candidates Kartici

| Kolona | Opis | Može se editovati? |
|--------|------|-------------------|
| **Name** | Ime kandidata | ✅ Da |
| **Surname** | Prezime kandidata | ✅ Da |
| **Email** | Email adresa | ✅ Da |
| **Phone** | Broj telefona | ✅ Da |
| **Status** | Status kandidata | ❌ Ne - samo iz Interview |
| **Interviews** | Broj intervjua | ❌ Ne - read-only |
| **Actions** | Edit / Delete | - |

## 💡 Primeri Korišćenja

### Primer 1: Zapošljavanje Kandidata
```
1. Kandidat "Marko Petrović" ima status: Active
2. Zakažeš intervju za poziciju "Java Developer"
3. Nakon intervjua klikneš "Complete"
4. Klikneš "Hire"
5. Rezultat:
   - Marko Petrović → Status: Hired
   - Pozicija "Java Developer" → Closed
```

### Primer 2: Odbijanje Kandidata
```
1. Kandidat "Ana Jovanović" ima status: Active
2. Zakažeš intervju za poziciju "Frontend Developer"
3. Nakon intervjua klikneš "Complete"
4. Klikneš "Reject"
5. Rezultat:
   - Ana Jovanović → Status: Rejected
   - Pozicija "Frontend Developer" → ostaje Open
   - Intervju → Canceled
```

### Primer 3: Pokušaj Editovanja Statusa (Neće Raditi)
```
1. Idi na Candidates karticu
2. Klikni "Edit" na kandidatu
3. Vidiš status kao read-only polje
4. Ne možeš ga promeniti
5. Prikazana je poruka:
   "Status se menja samo iz Interview kartice pomoću Hire/Reject dugmadi."
```

## 🔒 Zaštita Podataka

- Status se **ne može promeniti** direktno iz Candidates forme
- Status je **read-only** u edit formi
- Novi kandidati **uvek** počinju sa statusom "Active"
- Status se menja **samo** kroz validovan Interview workflow

## 🚀 Prednosti Ovog Pristupa

1. **Konzistentnost**: Status se menja samo na jednom mestu
2. **Tačnost**: Status odražava stvarno stanje intervju procesa
3. **Audit Trail**: Sve promene statusa vezane su za konkretne intervjue
4. **Automatizacija**: Zatvaranje pozicije se dešava automatski pri zapošljavanju

## 📱 UI Izmene

### Candidates Kartica:
- Uklonjeni Hire/Reject dugmad
- Status polje je read-only u formi
- Dodato upozorenje: "Status se menja iz Interview kartice"

### Interview Kartica:
- Hire/Reject dugmad ostaju
- Dodati confirmation dialozi na srpskom jeziku
- Automatsko osvežavanje liste kandidata nakon promene statusa

## ✅ Testiranje

1. **Test 1: Kreiranje Kandidata**
   - Kreiraj kandidata
   - Proveri da je status "Active"
   - Proveri da ne možeš da menjaš status u formi

2. **Test 2: Editovanje Kandidata**
   - Edituj ime, prezime, email, telefon
   - Proveri da status ostaje isti
   - Proveri da status polje ne može da se menja

3. **Test 3: Hire Workflow**
   - Zakaži intervju
   - Označi kao Completed
   - Klikni Hire
   - Proveri: kandidat → Hired, pozicija → Closed

4. **Test 4: Reject Workflow**
   - Zakaži intervju
   - Označi kao Completed
   - Klikni Reject
   - Proveri: kandidat → Rejected, pozicija → Open

## 🔄 Backend Logika

Postojeći backend endpointi:
- `POST /api/interviews/{id}/hire` - Zaposli kandidata
- `PUT /api/candidates/{id}/status?status={status}` - Ažuriraj status
- `PUT /api/interviews/{id}/status?status={status}` - Ažuriraj status intervjua

Ovi endpointi su već implementirani i rade pravilno! ✅

