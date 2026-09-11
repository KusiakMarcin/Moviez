import React, { useEffect, useState } from 'react';
import keycloak from '../keycloak';

export default function ProfilePage() {
  const [tickets, setTickets] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Extract user info straight from Keycloak's parsed token
  const userEmail = keycloak.tokenParsed?.email;
  const userName = keycloak.tokenParsed?.preferred_username || keycloak.tokenParsed?.name;

  useEffect(() => {
    const fetchMyTickets = async () => {
      try {
        await keycloak.updateToken(30);
        const response = await fetch('http://localhost:8081/api/tickets/my-tickets', {
          headers: { 'Authorization': `Bearer ${keycloak.token}` }
        });
        if (response.ok) {
          const data = await response.json();
          setTickets(data);
        }
      } catch (err) {
        console.error('Error fetching tickets:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchMyTickets();
  }, []);

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
      <h1>My Profile</h1>
      
      <div style={{ background: '#f5f5f5', padding: '20px', borderRadius: '8px', marginBottom: '30px' }}>
        <h3>Account Information</h3>
        <p><strong>Username:</strong> {userName}</p>
        <p><strong>Email:</strong> {userEmail}</p>
      </div>

      <h2>My Booked Tickets</h2>
      {loading ? (
        <p>Loading your tickets...</p>
      ) : tickets.length === 0 ? (
        <p>You haven't booked any tickets yet.</p>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
          {tickets.map((ticket, index) => (
            <div key={index} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px', background: '#fff' }}>
              <p><strong>Order Reference:</strong> {ticket.payuOrderId}</p>
              <p><strong>Status:</strong> <span style={{ color: ticket.status === 'PAID' ? 'green' : 'orange', fontWeight: 'bold' }}>{ticket.status}</span></p>
              <p><strong>Seat:</strong> Row {ticket.sitRow} | Seat {ticket.sitNumber}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}