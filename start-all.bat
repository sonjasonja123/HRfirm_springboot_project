@echo off
echo Starting HR Management System...
echo.
echo This will start both backend and frontend
echo Make sure you have Java 17+, Maven, and Node.js 16+ installed
echo.
echo Starting backend in a new window...
start "Backend" cmd /k "mvn spring-boot:run"
echo.
echo Waiting 10 seconds for backend to start...
timeout /t 10 /nobreak > nul
echo.
echo Starting frontend in a new window...
start "Frontend" cmd /k "cd frontend && npm install && npm start"
echo.
echo Both applications are starting...
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo H2 Console: http://localhost:8080/h2-console
echo.
pause
