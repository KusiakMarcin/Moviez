import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import keycloak from '../keycloak';

export default function PaymentResultPage() {
  const navigate = useNavigate();
  
  // 1. Read from LOCAL STORAGE exactly once using a lazy useState initializer!
  const [orderId] = useState(() => localStorage.getItem('current_orderId'));
  
  // 2. Read Error Code from SESSION STORAGE
  const [errorCode] = useState(() => sessionStorage.getItem('payu_error'));

  const [tickets, setTickets] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Clear the error from storage when the component unmounts
  useEffect(() => {
    return () => {
      sessionStorage.removeItem('payu_error');
    };
  }, []);

  useEffect(() => {
    if (!orderId) {
      setLoading(false);
      return;
    }

    const fetchTickets = async () => {
      try {
        await keycloak.updateToken(30);
        const response = await fetch(`http://localhost:8081/api/tickets/order/${orderId}`, {
          headers: { 'Authorization': `Bearer ${keycloak.token}` }
        });

        if (response.ok) {
          const data = await response.json();
          setTickets(data);
          
          // Clear the order ID now that we have the data
          localStorage.removeItem('current_orderId');
        }
      } catch (err) {
        console.error('Error fetching tickets:', err);
      } finally {
        setLoading(false);
      }
    };

    setTimeout(fetchTickets, 1500); 
  }, [orderId]);

  if (loading) return <div style={{ padding: '20px' }}>Loading your tickets...</div>;
  
  if (!orderId) return <div style={{ padding: '20px' }}>Order reference missing from redirect.</div>;

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto', textAlign: 'center' }}>
      {errorCode ? (
        <h1 style={{ color: '#e50914' }}>Payment Failed or Cancelled ❌</h1>
      ) : (
        <h1 style={{ color: 'green' }}>Payment Successful! 🎉</h1>
      )}
      
      <p>Your order reference is: <strong>{orderId}</strong></p>
      
      <button 
        onClick={() => navigate(tickets.length > 0 && tickets[0]?.status === 'PAID' ? '/' : '/movies')} 
        style={{ marginTop: '30px', padding: '10px 20px', background: '#e50914', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}
      >
        {tickets.length > 0 && tickets[0]?.status === 'PAID' ? 'Back to Home' : 'Try Again / Pick Other Seats'}
      </button>

      <div style={{ marginTop: '30px', display: 'flex', flexDirection: 'column', gap: '15px' }}>
        {tickets.map((ticket, index) => {
          const isPaid = ticket.status === 'PAID';
          const isCancelled = ticket.status === 'CANCELLED' || ticket.status === 'FAILED';

          return (
            <div key={index} style={{ border: '2px dashed #ccc', padding: '20px', borderRadius: '10px' }}>
              <h3>🎟️ Ticket #{ticket.id}</h3>
              <p><strong>Status:</strong> 
                <span style={{ color: isPaid ? 'green' : isCancelled ? 'red' : 'orange', fontWeight: 'bold' }}>
                  {ticket.status}
                </span>
              </p>
            </div>
          );
        })}
      </div>
    </div>
  );
}