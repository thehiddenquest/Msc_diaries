-- Active: 1718901595292@@127.0.0.1@3306@movie_management

-- Production_Company Table
CREATE TABLE Production_Company (
    Name VARCHAR(25) PRIMARY KEY,
    Address TEXT
);
-- Movie Table
CREATE TABLE Movie (
    Title VARCHAR(25),
    Year INT,
    Length INT,
    Plot_outline TEXT,
    Production_Company_Name VARCHAR(25),
    PRIMARY KEY (Title, Year),
    FOREIGN KEY (Production_Company_Name) REFERENCES Production_Company (Name)
);

-- Genre Table
CREATE TABLE Genre (Type VARCHAR(25) PRIMARY KEY);

-- Movie_Genre Table
CREATE TABLE Movie_Genre (
    Movie_Title VARCHAR(25),
    Movie_Year INT,
    Genre_Type VARCHAR(25),
    PRIMARY KEY (
        Movie_Title,
        Movie_Year,
        Genre_Type
    ),
    FOREIGN KEY (Movie_Title, Movie_Year) REFERENCES Movie (Title, Year),
    FOREIGN KEY (Genre_Type) REFERENCES Genre (Type)
);

-- Actor Table
CREATE TABLE Actor (
    Name VARCHAR(25),
    Date_of_Birth DATE,
    PRIMARY KEY (Name, Date_of_Birth)
);

-- Director Table
CREATE TABLE Director (
    Name VARCHAR(25),
    Date_of_Birth DATE,
    PRIMARY KEY (Name, Date_of_Birth)
);

-- Movie_Actor Table
CREATE TABLE Movie_Actor (
    Movie_Title VARCHAR(25),
    Movie_Year INT,
    Actor_Name VARCHAR(25),
    Role VARCHAR(25),
    PRIMARY KEY (
        Movie_Title,
        Movie_Year,
        Actor_Name
    ),
    FOREIGN KEY (Movie_Title, Movie_Year) REFERENCES Movie (Title, Year),
    FOREIGN KEY (Actor_Name) REFERENCES Actor (Name)
);

-- Movie_Director Table
CREATE TABLE Movie_Director (
    Movie_Title VARCHAR(25),
    Movie_Year INT,
    Director_Name VARCHAR(25),
    PRIMARY KEY (
        Movie_Title,
        Movie_Year,
        Director_Name
    ),
    FOREIGN KEY (Movie_Title, Movie_Year) REFERENCES Movie (Title, Year),
    FOREIGN KEY (Director_Name) REFERENCES Director (Name)
);

-- Quotes Table
CREATE TABLE Quotes (
    Movie_Title VARCHAR(25),
    Movie_Year INT,
    Movie_Quotation TEXT,
    Actor_Name VARCHAR(25),
    PRIMARY KEY (Movie_Title, Movie_Year),
    FOREIGN KEY (Movie_Title, Movie_Year) REFERENCES Movie (Title, Year),
    FOREIGN KEY (Actor_Name) REFERENCES Actor (Name)
);

-- Director_Actor Table
CREATE TABLE Director_Actor (
    Director_Name VARCHAR(25),
    PRIMARY KEY (Director_Name),
    FOREIGN KEY (Director_Name) REFERENCES Director (Name),
    FOREIGN KEY (Director_Name) REFERENCES Actor (Name)
);

-- Populating Production Company table
INSERT INTO
    Production_Company (Name, Address)
VALUES (
        'Paramount Pictures',
        '5555 Melrose Avenue, Los Angeles, CA 90038'
    ),
    (
        'Warner Bros. Pictures',
        '4000 Warner Blvd., Burbank, CA 91522'
    ),
    (
        'Universal Pictures',
        '100 Universal City Plaza, Universal City, CA 91608'
    ),
    (
        'Walt Disney Pictures',
        '500 S Buena Vista St, Burbank, CA 91521'
    ),
    (
        '20th Century Studios',
        '10201 W Pico Blvd, Los Angeles, CA 90064'
    );

-- Populating Genre table
INSERT INTO
    Genre (Type)
VALUES ('Action'),
    ('Adventure'),
    ('Comedy'),
    ('Drama'),
    ('Fantasy'),
    ('Horror'),
    ('Mystery'),
    ('Romance'),
    ('Science Fiction'),
    ('Thriller');