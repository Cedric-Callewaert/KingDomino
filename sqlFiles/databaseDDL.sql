CREATE TABLE Spelers (
    gebruikersnaam VARCHAR(50) PRIMARY KEY,
    geboortejaar INT,
    aantalgewonnen INT,
    aantalGespeeld INT
);

CREATE TABLE Tegels (
    idTegels INT AUTO_INCREMENT PRIMARY KEY,
    AantalKronen INT,
    Type VARCHAR(45),
    nummerAchterkant INT
);
