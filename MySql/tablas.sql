create database ExamenJava;

use ExamenJava;

create  table Ninja (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Rango VARCHAR(50) NOT NULL,
    Aldea VARCHAR(50) NOT NULL
);

create  table Mision (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Descripcion TEXT NOT NULL,
    Rango VARCHAR(50) NOT NULL,
    Recompensa DECIMAL(10, 2) NOT NULL
);

create  table MisionNinja (
    ID_Ninja INT,
    ID_Mision INT,
    FechaInicio DATE,
    FechaFin DATE,
    PRIMARY KEY (ID_Ninja, ID_Mision),
    FOREIGN KEY (ID_Ninja) REFERENCES Ninja(ID),
    FOREIGN KEY (ID_Mision) REFERENCES Mision(ID)
);

create  table Habilidad (
    ID_Ninja INT,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion TEXT NOT NULL,
    PRIMARY KEY (ID_Ninja, Nombre),
    FOREIGN KEY (ID_Ninja) REFERENCES Ninja(ID)
);