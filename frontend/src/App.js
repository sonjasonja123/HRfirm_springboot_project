import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Navbar from './components/Navbar';
import Login from './components/Login';
import ProtectedRoute from './components/ProtectedRoute';
import CompanyList from './components/CompanyList';
import PositionList from './components/PositionList';
import CandidateList from './components/CandidateList';
import InterviewList from './components/InterviewList';
import Dashboard from './components/Dashboard';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container-fluid mt-4">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            } />
            <Route path="/companies" element={
              <ProtectedRoute>
                <CompanyList />
              </ProtectedRoute>
            } />
            <Route path="/positions" element={
              <ProtectedRoute>
                <PositionList />
              </ProtectedRoute>
            } />
            <Route path="/candidates" element={
              <ProtectedRoute>
                <CandidateList />
              </ProtectedRoute>
            } />
            <Route path="/interviews" element={
              <ProtectedRoute>
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
