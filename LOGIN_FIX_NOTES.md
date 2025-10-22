# 🔧 Login Fix - Password Encoding Issue

## Problem

Login nije radio iako su kredencijali bili ispravni. Prikazivala se greška "Neuspešna prijava. Proverite kredencijale."

## Uzrok

Lozinke u bazi podataka su **enkriptovane** pomoću `PasswordEncoder.encode()`, ali login metode su koristile običnu `equals()` proveru:

```java
// ❌ POGREŠNO - Ne radi sa enkriptovanim lozinkama
public Optional<Company> login(String username, String password) {
    return companyRepository.findByUsername(username)
            .filter(c -> c.getPassword().equals(password));
}
```

## Rešenje

Ažurirane su login metode da koriste `PasswordEncoder.matches()` metodu koja pravilno poredi plain text lozinku sa enkriptovanom:

### CompanyService.java

```java
@Autowired
private PasswordEncoder passwordEncoder;

// ✅ ISPRAVNO - Koristi passwordEncoder.matches()
public Optional<Company> login(String username, String password) {
    return companyRepository.findByUsername(username)
            .filter(c -> passwordEncoder.matches(password, c.getPassword()));
}
```

### UserService.java

```java
@Autowired
private PasswordEncoder passwordEncoder;

// ✅ ISPRAVNO - Koristi passwordEncoder.matches()
public Optional<User> login(String username, String password) {
    return userRepository.findByUsername(username)
            .filter(u -> passwordEncoder.matches(password, u.getPassword()));
}
```

## Izmenjeni fajlovi

1. `src/main/java/com/hr/service/CompanyService.java`
   - Dodat `@Autowired PasswordEncoder passwordEncoder`
   - Ažurirana `login()` metoda da koristi `passwordEncoder.matches()`

2. `src/main/java/com/hr/service/UserService.java`
   - Dodat `@Autowired PasswordEncoder passwordEncoder`
   - Ažurirana `login()` metoda da koristi `passwordEncoder.matches()`

## Kako `passwordEncoder.matches()` radi

```java
passwordEncoder.matches(rawPassword, encodedPassword)
```

- **rawPassword**: Plain text lozinka koju korisnik unosi (npr. "tech123")
- **encodedPassword**: Enkriptovana lozinka iz baze (npr. "$2a$10$...")
- **Povratna vrednost**: `true` ako se lozinke poklapaju, `false` ako ne

## Testiranje

Nakon ponovnog pokretanja backend servera, sada bi trebalo da radi:

### Kompanija Login:
```
Username: techsolutions
Password: tech123
```

### Radnik Login:
```
Username: worker1
Password: worker123
```

## Napomena

Ova promena zahteva **restart backend servera**:

```bash
# Zaustavi trenutni backend (Ctrl+C)
# Pokreni ponovo:
mvn spring-boot:run
```

---

**Status:** ✅ Ispravljeno  
**Datum:** 23.10.2025  
**Kompilacija:** Uspešna

