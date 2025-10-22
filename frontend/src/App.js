import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Navbar from './components/Navbar';
import LoginSelection from './components/LoginSelection';
import CompanyLogin from './components/CompanyLogin';
import WorkerLogin from './components/WorkerLogin';
import ProtectedRoute from './components/ProtectedRoute';
import CompanyList from './components/CompanyList';
import PositionList from './components/PositionList';
import CandidateList from './components/CandidateList';
import InterviewList from './components/InterviewList';
import Dashboard from './components/Dashboard';
import CompanyDashboard from './components/CompanyDashboard';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container-fluid mt-4">
          <Routes>
            {/* Login Routes */}
            <Route path="/login" element={<LoginSelection />} />
            <Route path="/login/company" element={<CompanyLogin />} />
            <Route path="/login/worker" element={<WorkerLogin />} />
            
            {/* Company Routes */}
            <Route path="/company/dashboard" element={
              <ProtectedRoute requiredType="company">
                <CompanyDashboard />
              </ProtectedRoute>
            } />
            
            {/* Worker Routes */}
            <Route path="/" element={
              <ProtectedRoute requiredType="worker">
                <Dashboard />
              </ProtectedRoute>
            } />
            <Route path="/companies" element={
              <ProtectedRoute requiredType="worker">
                <CompanyList />
              </ProtectedRoute>
            } />
            <Route path="/positions" element={
              <ProtectedRoute requiredType="worker">
                <PositionList />
              </ProtectedRoute>
            } />
            <Route path="/candidates" element={
              <ProtectedRoute requiredType="worker">
                <CandidateList />
              </ProtectedRoute>
            } />
            <Route path="/interviews" element={
              <ProtectedRoute requiredType="worker">
                <InterviewList />
              </ProtectedRoute>
            } />
          </Routes>
        </div>
        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </div>
    </Router>
  );
}

export default App;
