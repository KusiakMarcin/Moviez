-- Insert 3 Test Cinemas (2 in Warsaw, 1 in Krakow)
INSERT INTO Cinemas (Name, City, Address) VALUES
('Multikino Zlote Tarasy', 'Warsaw', 'Zlota 59'),
('Cinema City Arkadia', 'Warsaw', 'al. Jana Pawla II 82'),
('Helios', 'Krakow', 'ul. Powstancow 1');

-- Insert 3 Test Movies
INSERT INTO Movies (Title, DurationMinutes, PosterUrl) VALUES
('Inception', 148, 'https://image.tmdb.org/t/p/w500/9gk7adZA2822eVzvG32B4Ope10.jpg'),
('Dune: Part Two', 166, 'https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2JGjjc9CW.jpg'),
('The Matrix', 136, 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg');

-- Insert Showings for TODAY (September 9, 2026) so they show up in React instantly!
INSERT INTO Showings (CinemaId, MovieId, ShowingTime, BasePrice) VALUES
(1, 1, '2026-09-09 18:30:00', 25.50), -- Inception at Zlote Tarasy
(1, 2, '2026-09-09 20:15:00', 30.00), -- Dune at Zlote Tarasy
(2, 3, '2026-09-09 19:00:00', 22.00), -- The Matrix at Arkadia
(3, 1, '2026-09-09 17:00:00', 20.00); -- Inception in Krakow (Will NOT show up in Warsaw search)
