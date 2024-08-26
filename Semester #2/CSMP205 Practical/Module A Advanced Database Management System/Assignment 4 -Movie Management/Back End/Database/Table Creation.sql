CREATE TABLE Production_Company (
    Name VARCHAR(100) PRIMARY KEY,
    Address TEXT
);

-- Movie Table
CREATE TABLE Movie (
    Title VARCHAR(255),
    Year INT,
    Length INT,
    Plot_outline TEXT,
    Production_Company_Name VARCHAR(100),
    PRIMARY KEY (Title, Year),
    FOREIGN KEY (Production_Company_Name) REFERENCES Production_Company (Name)
);

-- Genre Table
CREATE TABLE Genre (Type VARCHAR(50) PRIMARY KEY);

-- Movie_Genre Table
CREATE TABLE Movie_Genre (
    Movie_Title VARCHAR(255),
    Movie_Year INT,
    Genre_Type VARCHAR(50),
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
    Name VARCHAR(100),
    Date_of_Birth DATE,
    PRIMARY KEY (Name, Date_of_Birth)
);

-- Director Table
CREATE TABLE Director (
    Name VARCHAR(100),
    Date_of_Birth DATE,
    PRIMARY KEY (Name, Date_of_Birth)
);

-- Movie_Actor Table
CREATE TABLE Movie_Actor (
    Movie_Title VARCHAR(255),
    Movie_Year INT,
    Actor_Name VARCHAR(100),
    Role VARCHAR(100),
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
    Movie_Title VARCHAR(255),
    Movie_Year INT,
    Director_Name VARCHAR(100),
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
    Movie_Title VARCHAR(255),
    Movie_Year INT,
    Quote TEXT,
    Actor_name VARCHAR(100),
    Quote_id INT,
    PRIMARY KEY (
        Movie_Title,
        Movie_Year,
        Quote_id
    ),
    FOREIGN KEY (Movie_Title, Movie_Year) REFERENCES Movie (Title, Year),
    FOREIGN KEY (Actor_name) REFERENCES Actor (Name)
);