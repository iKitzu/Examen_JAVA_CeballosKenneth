show tables;

-- Inserts para Ninja
insert into Ninja (Nombre, Rango, Aldea) values ('Naruto Uzumaki', 'Genin', 'Konoha');
insert into Ninja (Nombre, Rango, Aldea) values ('Sasuke Uchiha', 'Genin', 'Konoha');
insert into Ninja (Nombre, Rango, Aldea) values ('Sakura Haruno','Ninja','Konoha')

-- Inserts para Misiones
insert into Mision (Descripcion, Rango, Recompensa) values ('Protección del puente en la Tierra de las Olas', 'B', 1500.00);
insert into Mision (Descripcion, Rango, Recompensa) values ('Recuperación del pergamino secreto', 'A', 5000.00);

-- Inserts para MisionesNinja
insert into MisionNinja (ID_Ninja, ID_Mision, FechaInicio, FechaFin) values (1, 1, '2024-09-01', null);
insert into MisionNinja (ID_Ninja, ID_Mision, FechaInicio, FechaFin) values (2, 2, '2024-09-02', null);

-- Inserts para Habilidad
insert into Habilidad (ID_Ninja, Nombre, Descripcion) values (1, 'Rasengan', 'Técnica de concentración de chakra en la mano para crear una esfera destructiva.');
insert into Habilidad (ID_Ninja, Nombre, Descripcion) values (2, 'Chidori', 'Técnica de concentración de chakra en la mano para crear un rayo de alta velocidad.');

