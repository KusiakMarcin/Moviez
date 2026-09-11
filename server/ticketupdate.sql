-- 1. Drop the existing table safely
DROP TABLE IF EXISTS Tickets;

-- 2. Recreate the table with the new column
CREATE TABLE Tickets (
    TicketId serial PRIMARY KEY,
    ShowingId integer NOT NULL,
    UserId integer NOT NULL,
    SitNumber integer NOT NULL,
    SitRow varchar(2) NOT NULL,
    payu_order_id varchar(100),    -- <-- NEW COLUMN ADDED HERE
    Status varchar(20) DEFAULT 'RESERVED',
    CONSTRAINT fk_Showings FOREIGN KEY (ShowingId) REFERENCES Showings(ShowingId),
    CONSTRAINT fk_Users FOREIGN KEY (UserId) REFERENCES Users(UserId)
);
