#!/bin/bash
echo "Starting HR Management System..."
echo ""
echo "This will start both backend and frontend"
echo "Make sure you have Java 17+, Maven, and Node.js 16+ installed"
echo ""
echo "Starting backend in background..."
mvn spring-boot:run &
BACKEND_PID=$!
echo "Backend PID: $BACKEND_PID"
echo ""
echo "Waiting 10 seconds for backend to start..."
sleep 10
echo ""
echo "Starting frontend in background..."
cd frontend
npm install
npm start &
FRONTEND_PID=$!
echo "Frontend PID: $FRONTEND_PID"
echo ""
echo "Both applications are starting..."
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:3000"
echo "H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop both applications"
echo ""

# Function to cleanup processes on exit
cleanup() {
    echo ""
    echo "Stopping applications..."
    kill $BACKEND_PID 2>/dev/null
    kill $FRONTEND_PID 2>/dev/null
    echo "Applications stopped."
    exit 0
}

# Set trap to cleanup on script exit
trap cleanup SIGINT SIGTERM

# Wait for user to stop
wait
