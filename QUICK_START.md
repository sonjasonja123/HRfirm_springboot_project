# Quick Start Guide

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher  
- Node.js 16 or higher

## Windows Users

### Option 1: Start Everything at Once
1. Double-click `start-all.bat`
2. Wait for both applications to start
3. Open your browser and go to `http://localhost:3000`

### Option 2: Start Separately
1. Double-click `start-backend.bat` (wait for it to start)
2. Double-click `start-frontend.bat`

## Linux/Mac Users

### Option 1: Start Everything at Once
1. Make scripts executable: `chmod +x *.sh`
2. Run: `./start-all.sh`
3. Open your browser and go to `http://localhost:3000`

### Option 2: Start Separately
1. Make scripts executable: `chmod +x *.sh`
2. Run: `./start-backend.sh` (wait for it to start)
3. Run: `./start-frontend.sh`

## Manual Start

### Backend
```bash
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm start
```

## Access Points
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## First Steps
1. Add some companies
2. Create positions for those companies
3. Add candidates
4. Schedule interviews between candidates and positions
5. Add interview rounds as needed

## Troubleshooting
- Make sure ports 3000 and 8080 are not in use
- Check that Java, Maven, and Node.js are properly installed
- For Windows users, make sure to run as Administrator if needed
