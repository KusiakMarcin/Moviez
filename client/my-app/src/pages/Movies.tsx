import React, { useState, useEffect } from 'react';
import keycloak from '../keycloak';
import { useNavigate } from 'react-router-dom';

interface Showing {
  showingId: number;
  movieTitle: string;
  durationMinutes: number;
  posterUrl: string | null;
  cinemaName: string;
  city: string;
  showingTime: string;
}

export default function Movies() {
  const navigate = useNavigate();
  
  // FIX 1: Match the exact spelling in your database
  const [city, setCity] = useState<string>('Warszawa');
  
  const [showings, setShowings] = useState<Showing[]>([]);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchShowings = async () => {
      if (!city) return;
      
      setIsLoading(true);
      try {
        let headers: Record<string, string> = {};

        // Only try to update and attach the token if the user is logged in
        if (keycloak.authenticated) {
          try {
            await keycloak.updateToken(30);
            headers['Authorization'] = `Bearer ${keycloak.token}`;
          } catch (tokenErr) {
            console.warn('Failed to refresh token, proceeding as guest');
          }
        }
        
        const url = `http://localhost:8081/api/showings?city=${encodeURIComponent(city)}`;
        
        // Fetch works for both guests and logged-in users now!
        const response = await fetch(url, { headers });

        if (response.ok) {
          const data: Showing[] = await response.json(); 
          const now = new Date();
          
          const upcomingShowings = data
            .filter(showing => new Date(showing.showingTime) > now)
            .sort((a, b) => new Date(a.showingTime).getTime() - new Date(b.showingTime).getTime());

          setShowings(upcomingShowings);
          setError('');
        } else {
          setError(`Failed to fetch showings. Status: ${response.status}`);
        }
      } catch (err) {
        setError('Network error while fetching showings.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchShowings();
  }, [city]);

  // Updated to show the Date as well, since we are showing multiple days
  const formatTime = (isoString: string) => {
    return new Date(isoString).toLocaleString([], { 
      month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' 
    });
  };

  return (
    <div className ="flex w-max p-20 max-w-1200 bg-blue-100 font-mono">
      <div className="flex gap-10">
        <div className="text-4x1 font-bold font-stretch-extra-condensed">Upcoming Showings</div>
        <div className ="flex">
        <div>
          <label >City:</label>
          <input 
            type="text" 
            value={city} 
            onChange={(e) => setCity(e.target.value)}
            
          />
        </div>
        {/* Date input removed for a simpler "All Upcoming" view */}
      </div>
      <div>XDDD</div>
        </div>
      
      {/* Search Filters */}
      

      {/* Error and Loading States */}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {isLoading && <p>Loading showings...</p>}

      {/* Showings Grid */}
      {!isLoading && !error && showings.length === 0 && (
        <p>No upcoming showings found in {city}.</p>
      )}

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
        {showings.map((showing) => (
          <div key={showing.showingId} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>
            <h3 style={{ margin: '0 0 10px 0', color: '#e50914' }}>{showing.movieTitle}</h3>
            <p><strong>When:</strong> {formatTime(showing.showingTime)}</p>
            <p><strong>Duration:</strong> {showing.durationMinutes} min</p>
            <p><strong>Cinema:</strong> {showing.cinemaName}</p>
            <button 
              onClick={() => navigate(`/book/${showing.showingId}`)} 
              style={{ 
                marginTop: '10px', 
                padding: '8px 15px', 
                background: '#e50914', 
                color: 'white', 
                border: 'none', 
                borderRadius: '4px', 
                cursor: 'pointer', 
                width: '100%' 
              }}
            >
              Book Ticket
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}