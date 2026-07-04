



\c Moviez_Database



CREATE TABLE Users(

    UserId serial PRIMARY KEY,
    Email varchar(100)  NOT NULL UNIQUE,
    Password varchar(100)  NOT NULL


);

CREATE TABLE Movies(

    MovieId serial PRIMARY KEY,
    Title varchar(100) NOT NULL,


);

CREATE TABLE Cinemas(

    Cinema_Id serial PRIMARY KEY,


);



CREATE TABLE Showings(

    ShowingId serial PRIMARY KEY,
    CinemaId integer,
    MovieId integer,
    Showing_Date timestamp NOT NULL,

    CONSTRAINT fk_Cinemas FOREIGN KEY (CinemaID) REFERENCES Cinemas(Cinema_Id),
    CONSTRAINT fk_Movies FOREIGN KEY (MovieId) REFERENCES Movies(MovieId)

);

CREATE TABLE Tickets(

    TicketId serial PRIMARY KEY,
    SitNumber integer NOT NULL,
    SitRow varchar(1) NOT NULL,



)

CREATE TABLE Payments(

    PaymentId serial PRIMARY KEY,
    TicketId integer NOT NULL,

    CONSTRAINT fk_Tickets FOREIGN KEY (TicketId) REFERENCES Tickets(TicketId)

)