import type { ReactNode } from 'react';
import keycloak from './keycloak';

interface ProtectedRouteProps {
  children: ReactNode;
}

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  if (!keycloak.authenticated) {
    keycloak.login({ redirectUri: window.location.href });
    return <div style={{ padding: '20px' }}>Redirecting to login...</div>;
  }

  return <>{children}</>;
}