import React from 'react';
import { Navigate } from 'react-router-dom';
import authService from '../services/authService';

const ProtectedRoute = ({ children, requiredType }) => {
  const isAuthenticated = authService.isAuthenticated();
  const userType = authService.getUserType();
  
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  
  // Provera tipa korisnika ako je requiredType postavljen
  if (requiredType && userType !== requiredType) {
    // Preusmeri na odgovarajuću stranicu
    if (userType === 'company') {
      return <Navigate to="/company/dashboard" replace />;
    } else {
      return <Navigate to="/" replace />;
    }
  }
  
  return children;
};

export default ProtectedRoute;
