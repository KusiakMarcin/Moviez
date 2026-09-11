import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import keycloak from '../keycloak';

export default function SeatSelection() {
  const { showingId } = useParams(); // Gets the ID from the URL (e.g., /book/1)
  const navigate = useNavigate();

  const rows = ['A', 'B', 'C', 'D', 'E', 'F'];
  const seatsPerRow = 10;

  const [bookedSeats, setBookedSeats] = useState<string[]>([]);
  const [selectedSeats, setSelectedSeats] = useState<string[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  // 1. Fetch already booked seats for this showing
  useEffect(() => {
    const fetchBookedSeats = async () => {
      try {
        await keycloak.updateToken(30);
        // Expects an array of strings like ["A4", "A5", "C2"]
        const response = await fetch(`http://localhost:8081/api/showings/${showingId}/seats`, {
          headers: { 'Authorization': `Bearer ${keycloak.token}` }
        });
        if (response.ok) {
          const data = await response.json();
          setBookedSeats(data);
        }
      } catch (err) {
        setError('Failed to load seat map.');
      } finally {
        setIsLoading(false);
      }
    };
    fetchBookedSeats();
  }, [showingId]);

  // 2. Toggle seat selection
  const handleSeatClick = (seatId: string) => {
    if (bookedSeats.includes(seatId)) return; // Ignore clicks on booked seats

    if (selectedSeats.includes(seatId)) {
      setSelectedSeats(selectedSeats.filter(s => s !== seatId));
    } else {
      setSelectedSeats([...selectedSeats, seatId]);
    }
  };

  // 3. Submit booking and redirect to PayU
  const handleCheckout = async () => {
    if (selectedSeats.length === 0) return;
    
    setIsLoading(true);
    try {
      await keycloak.updateToken(30);
      const response = await fetch('http://localhost:8081/api/bookings', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${keycloak.token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          showingId: Number(showingId),
          seats: selectedSeats // e.g., ["B3", "B4"]
        })
      });

      if (response.ok) {
        const data = await response.json();
        
        // Save the generated order ID for the payment result page
        localStorage.setItem('current_orderId', data.extOrderId);
        
        // Redirect to PayU
        window.location.href = data.payuRedirectUrl; 
      } else {
        setError('Failed to initialize payment.');
        setIsLoading(false);
      }
    } catch (err) {
      setError('Network error during checkout.');
      setIsLoading(false);
    }
  };

  if (isLoading) return <div style={{ padding: '20px' }}>Loading cinema layout...</div>;

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto', textAlign: 'center' }}>
      <h2>Select Your Seats</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      
      {/* The Cinema Screen */}
      <div style={{ background: '#ccc', padding: '10px', margin: '20px 0', borderRadius: '4px', letterSpacing: '5px' }}>
        SCREEN
      </div>

      {/* The Seat Grid */}
      <div style={{ display: 'inline-block', textAlign: 'left', marginBottom: '30px' }}>
        {rows.map(row => (
          <div key={row} style={{ display: 'flex', gap: '10px', marginBottom: '10px', alignItems: 'center' }}>
            <span style={{ width: '20px', fontWeight: 'bold' }}>{row}</span>
            {[...Array(seatsPerRow)].map((_, i) => {
              const seatId = `${row}${i + 1}`;
              const isBooked = bookedSeats.includes(seatId);
              const isSelected = selectedSeats.includes(seatId);

              // Seat styling logic
              let backgroundColor = '#4caf50'; // Available (Green)
              let cursor = 'pointer';
              if (isBooked) {
                backgroundColor = '#9e9e9e'; // Booked (Grey)
                cursor = 'not-allowed';
              } else if (isSelected) {
                backgroundColor = '#2196f3'; // Selected (Blue)
              }

              return (
                <button
                  key={seatId}
                  onClick={() => handleSeatClick(seatId)}
                  disabled={isBooked}
                  style={{ width: '40px', height: '40px', borderRadius: '8px 8px 4px 4px', border: 'none', backgroundColor, color: 'white', cursor, transition: '0.2s' }}
                >
                  {i + 1}
                </button>
              );
            })}
          </div>
        ))}
      </div>

      {/* Checkout Panel */}
      <div style={{ padding: '20px', background: '#f5f5f5', borderRadius: '8px' }}>
        <h3>Selected: {selectedSeats.length > 0 ? selectedSeats.join(', ') : 'None'}</h3>
        <button 
          onClick={handleCheckout}
          disabled={selectedSeats.length === 0}
          style={{ padding: '10px 20px', background: selectedSeats.length > 0 ? '#e50914' : '#ccc', color: 'white', border: 'none', borderRadius: '4px', cursor: selectedSeats.length > 0 ? 'pointer' : 'not-allowed', fontSize: '16px' }}
        >
          Pay & Book Tickets
        </button>
      </div>
    </div>
  );
}