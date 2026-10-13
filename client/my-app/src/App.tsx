import React, { useState, useEffect, useRef } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import keycloak from './keycloak';
import Navigation from './components/Navigation';
import Home from './pages/Home';
import Movies from './pages/Movies';
import SeatSelection from './pages/SeatSelection';
import PaymentSuccessPage from './pages/PaymentResultPage';
import ProtectedRoute from './ProtectedRouteProps';
import ProfilePage from './pages/ProfilePage';
import './App.css'
export default function App() {
  const [isInitialized, setIsInitialized] = useState(false);
  const isRun = useRef(false);

  useEffect(() => {
    if (isRun.current) return;
    isRun.current = true;

    // 1. INTERCEPT PAYU PARAMS BEFORE KEYCLOAK SEES THEM
    const queryParams = new URLSearchParams(window.location.search);
    const payuOrderId = queryParams.get('orderId');
    const payuError = queryParams.get('error');

    if (payuOrderId || payuError) {
      // Store them safely in session storage so PaymentSuccessPage can read them
      if (payuOrderId) sessionStorage.setItem('payu_orderId', payuOrderId);
      if (payuError) sessionStorage.setItem('payu_error', payuError);

      // Immediately clean the URL so Keycloak's OIDC parser ignores them completely
      window.history.replaceState({}, document.title, window.location.pathname);
    }

    // keycloack init
  console.log(import.meta.env.VITE_MOCK_SERVICES);  
  if(import.meta.env.VITE_MOCK_SERVICES === 'true') setIsInitialized(true);
  else keycloak.init({ onLoad: 'check-sso' })
      .then(() => setIsInitialized(true))
      .catch(err => console.error("Keycloak init failed", err));
  }, []);
  if (!isInitialized) return <div style={{ padding: '20px' }}>Loading Cinema...</div>;

  return (
    <BrowserRouter>
      <Navigation />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/movies" element={<Movies />} />
        <Route path="/payment-success" element={<PaymentSuccessPage />} />
        <Route path="/profile" element={
          <ProtectedRoute>
            <ProfilePage />
          </ProtectedRoute>
        } />
        <Route path="/book/:showingId" element={
          <ProtectedRoute>
            <SeatSelection />
          </ProtectedRoute>
        } />
      </Routes>
    </BrowserRouter>
  );
}