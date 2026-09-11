-- 1. Safely remove old custom tables (CASCADE handles foreign key dependencies)
DROP TABLE IF EXISTS Payments CASCADE;
DROP TABLE IF EXISTS Tickets CASCADE;
DROP TABLE IF EXISTS Showings CASCADE;
DROP TABLE IF EXISTS Cinemas CASCADE;
DROP TABLE IF EXISTS Movies CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

-- 2. Create the updated tables
CREATE TABLE Users (
    UserId serial PRIMARY KEY,
    Email varchar(100) NOT NULL UNIQUE,
    KeycloakId varchar(100) UNIQUE
);

CREATE TABLE Movies (
    MovieId serial PRIMARY KEY,
    Title varchar(100) NOT NULL,
    DurationMinutes integer NOT NULL,
    PosterUrl varchar(255)
);

CREATE TABLE Cinemas (
    CinemaId serial PRIMARY KEY,
    Name varchar(100) NOT NULL,
    City varchar(100) NOT NULL,
    Address varchar(255) NOT NULL
);

CREATE TABLE Showings (
    ShowingId serial PRIMARY KEY,
    CinemaId integer NOT NULL,
    MovieId integer NOT NULL,
    ShowingTime timestamp NOT NULL,
    BasePrice decimal(10,2) NOT NULL,
    CONSTRAINT fk_Cinemas FOREIGN KEY (CinemaId) REFERENCES Cinemas(CinemaId),
    CONSTRAINT fk_Movies FOREIGN KEY (MovieId) REFERENCES Movies(MovieId)
);

CREATE TABLE Tickets (
    TicketId serial PRIMARY KEY,
    ShowingId integer NOT NULL,
    UserId integer NOT NULL,
    SitNumber integer NOT NULL,
    SitRow varchar(2) NOT NULL,
    payu_order_id varchar(100),
    Status varchar(20) DEFAULT 'RESERVED',
    CONSTRAINT fk_Showings FOREIGN KEY (ShowingId) REFERENCES Showings(ShowingId),
    CONSTRAINT fk_Users FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

CREATE TABLE Payments (
    PaymentId serial PRIMARY KEY,
    TicketId integer NOT NULL,
    Amount decimal(10,2) NOT NULL,
    PaymentDate timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_Tickets FOREIGN KEY (TicketId) REFERENCES Tickets(TicketId)
);
