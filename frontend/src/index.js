import React from 'react';
import ReactDOM from 'react-dom/client';

// 1. KORAK: UVEZITE BOOTSTRAP PRVO
import 'bootstrap/dist/css/bootstrap.min.css'; 

// 2. KORAK: NAKON TOGA UVEZITE VAŠ CUSTOM CSS
import './index.css'; 
// NAPOMENA: Ako ste stilove stavili u App.css a ne index.css, onda ovde treba da stoji './App.css'
// a u App.js fajlu izbrišite tu liniju da se ne uvozi duplo. Najbolja praksa je da glavni CSS bude ovde.

import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);