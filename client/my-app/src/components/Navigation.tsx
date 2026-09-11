import React from 'react';
import { Link } from 'react-router-dom';
import keycloak from '../keycloak';

export default function Navigation() {
  return (
    <nav style={{ display: 'flex', justifyContent: 'space-between', padding: '15px 30px', background: '#222', color: 'white', alignItems: 'center' }}>
      <div style={{ display: 'flex', gap: '20px' }}>
        <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>Home</Link>
        <Link to="/movies" style={{ color: 'white', textDecoration: 'none' }}>Movies</Link>
      </div>

      <div>
        {keycloak.authenticated ? (
          <div style={{ display: 'flex', gap: '15px', alignItems: 'center' }}>
            <span>Hello, {keycloak.tokenParsed?.preferred_username}</span>
            {/* Added Profile Link */}
            <Link to="/profile" style={{ color: '#e50914', textDecoration: 'none', fontWeight: 'bold' }}>Profile</Link>
            <button onClick={() => keycloak.logout()} style={{ background: '#444', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer', borderRadius: '4px' }}>
              Logout
            </button>
          </div>
        ) : (
          <button onClick={() => keycloak.login()} style={{ background: '#e50914', color: 'white', border: 'none', padding: '8px 15px', cursor: 'pointer', borderRadius: '4px' }}>
            Login
          </button>
        )}
      </div>
    </nav>
  );
}