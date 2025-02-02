ALTER session  SET NLS_DATE_FORMAT='YYYY-MM-DD' ;

/* BORRAR TABLAS */

DROP TABLE participa cascade constraint;
DROP TABLE  usa cascade constraint;
DROP TABLE patrocina cascade constraint;
DROP TABLE estudiantes cascade constraint;
DROP TABLE  recursos cascade constraint;
DROP TABLE  entidades cascade constraint;
DROP TABLE  proyectos cascade constraint;

CREATE TABLE entidades
(
	codentidad number(10,0) NOT NULL,
	descripcion varchar2(30),
	telefono varchar2(10),
	direccion varchar2(40),
	contacto varchar2(30),
	PRIMARY KEY (codentidad)
);


CREATE TABLE estudiantes
(
	codestudiante number(10,0) NOT NULL,
	nombre varchar2(30),
	direccion varchar2(40),
	tlf varchar2(10),
	fechaalta date,
	PRIMARY KEY (codestudiante)
);


CREATE TABLE participa
(
	codparticipacion number(10,0) NOT NULL,
	codestudiante number(10,0) NOT NULL,
	codigoproyecto number(10,0) NOT NULL,
	tipoparticipacion varchar2(20),
	numaportaciones number(10,0),
	PRIMARY KEY (codparticipacion)
);


CREATE TABLE patrocina
(
	codpatrocinio number(10,0) NOT NULL,
	codigoproyecto number(10,0) NOT NULL,
	codentidad number(10,0) NOT NULL,
	importeaportacion float,
	fechaaportacion date,
	PRIMARY KEY (codpatrocinio)
);


CREATE TABLE proyectos
(
	codigoproyecto number(10,0) NOT NULL,
	nombre varchar2(45) UNIQUE,
	fechainicio date,
	fechafin date,
	presupuesto float,
	extraaportacion float,
	PRIMARY KEY (codigoproyecto)
);


CREATE TABLE recursos
(
	codrecurso number(10,0) NOT NULL,
	descripcion varchar2(35),
	unidades number(10,0),
	distancia number(10,0),
	extra float,
	pvp float,
	PRIMARY KEY (codrecurso)
);


CREATE TABLE usa
(
	coduso number(10,0) NOT NULL,
	codigoproyecto number(10,0) NOT NULL,
	codrecurso number(10,0) NOT NULL,
	cantidad number(10,0),
	fechaasignacion date,
	PRIMARY KEY (coduso)
);



/* Insert Proyectos
--CREATE TABLE proyectos
--(- codigoproyecto int NOT NULL,nombre varchar(45),fechainicio date,fechafin date,presupuesto float,extraaportacion float,
--	PRIMARY KEY (codigoproyecto),
--	UNIQUE (nombre) */
insert into proyectos values (1,'Big data para Redes Sociales','2018-01-10', '2020-03-10',150000.0, 150 );
insert into proyectos values (2,'Apps para la programación algorítmica', '2018-02-05', '2021-05-10',180000.0, 250 );
insert into proyectos values (3,'Herramientas de Ciberseguridad y auditoría', '2018-03-01', '2021-09-01',190000.0, 100 );
insert into proyectos values (4,'Sistemas de Control de acceso a instituciones', '2018-04-01', '2020-12-10',160000.0, 200 );
insert into proyectos values (5,'Proyecto Luz solar', '2021-06-01', '2022-12-10',160000.0, 200 );

insert into proyectos values (15,'Ciberseguridad en las aulas', '2023-06-01', '2024-12-10',200000.0, 200 );
insert into proyectos values (18,'Big Data en los Logs', '2023-09-01', '2024-12-10',150000.0, 150 );

/* Insert recursos */
/*codrecurso int NOT NULL, 	descripcion varchar(30),
	unidades int,	distancia int,	extra float,	pvp float, */

insert into recursos values (1,'Equipo informático I5', 100, 0, 25, 300);
insert into recursos values (2,'Equipo informático I7', 50, 0, 30, 350);	
insert into recursos values (3,'Disco Almacenamiento 5T', 200, 0, 50, 120);
insert into recursos values (4,'Controladores de presencia tipo 1', 10, 0, 80, 320);	
insert into recursos values (5,'Controladores biométricos', 20, 0, 100, 400);	
insert into recursos values (6,'Torno automático', 10, 0, 100, 500);	
insert into recursos values (7,'Torno semiautomático', 10, 0, 120, 550);	
insert into recursos values (8,'Impresora Láser', 30, 0, 30, 140);
insert into recursos values (9,'Pantalla digital', 25, 0, 30, 640);
insert into recursos values (10,'Servidor NAS', 10, 0, 30, 540);
insert into recursos values (11,'Router wifi', 20, 0, 10, 100);



/* Insert usa */
/*coduso int NOT NULL, 	codigoproyecto int NOT NULL,	codrecurso int NOT NULL,
	cantidad int,	fechaasignacion date, */
	
/*proyecto 1 recursos 1,2,3,8,9*/
insert into usa values (1,1, 1, 5,'2018-01-02' );
insert into usa values (2,1, 2, 1,'2018-01-02' );
insert into usa values (3,1, 3, 5,'2018-01-05' );
insert into usa values (4,1, 8, 1,'2018-01-12' );
insert into usa values (5,1, 9, 1,'2018-01-13' );

/*proyecto 2 recursos 1,2,3,8,10,11*/
insert into usa values (6,2, 1, 5,'2018-02-12' );
insert into usa values (7,2, 2, 1,'2018-02-12' );
insert into usa values (8,2, 3, 5,'2018-02-15' );
insert into usa values (9,2, 8, 1,'2018-02-22' );
insert into usa values (10,2, 10, 1,'2018-02-23' );
insert into usa values (11,2, 11, 2,'2018-02-23' );
	

/*proyecto 3 recursos 1,8,10,11*/
insert into usa values (12,3, 1, 3,'2018-03-15' );
insert into usa values (13,3, 8, 1,'2018-03-15' );
insert into usa values (14,3, 10, 1,'2018-03-20' );
insert into usa values (15,3, 11, 2,'2018-03-20' );
	
/*proyecto 4 recursos 1,4,5,6,7,8,10*/
insert into usa values (16,4, 1, 3,'2018-04-10' );
insert into usa values (17,4, 4, 2,'2018-04-10' );
insert into usa values (18,4, 5, 2,'2018-04-11' );
insert into usa values (19,4, 6, 2,'2018-04-11' );
insert into usa values (20,4, 7, 2,'2018-04-15' );
insert into usa values (21,4, 8, 1,'2018-04-15' );
insert into usa values (22,4, 10, 1,'2018-04-20' );

	
/* Insert estudiantes 
	codestudiante int NOT NULL, nombre varchar(30),	direccion varchar(30),	tlf varchar(10),
	fechaalta date, */

	INSERT INTO estudiantes VALUES (1,'ALICIA GARCÍA RAMOS' ,'C/Los Sauces 3. Talavera. ESP', '925666777','2018-01-20'  );
	INSERT INTO estudiantes VALUES (2,'RAQUEL GARRIDO SANZ' , 'C/Ronda 9. Madrid. ESP', '910066777', '2018-01-23' );
	INSERT INTO estudiantes VALUES (3,'CESSARE LANFALONI' , 'C/Via Platta. Peruggia. ITA', '925666777', '2018-01-24' );
	
	INSERT INTO estudiantes VALUES (4,'MATHEO CORLEONE' , 'C/Dolce Vita. Nápoles. ITA', '875666022',  '2018-02-15');
	INSERT INTO estudiantes VALUES (5,'WILIAN STEWART' , 'Street Penny Lane 12. UK', '556677232', '2018-02-15' );
	INSERT INTO estudiantes VALUES (6,'DAVID HUTTON' , 'Street Football 2. Londres. UK', '234009911', '2018-02-25' );

	INSERT INTO estudiantes VALUES (7,'ALEJANDRO MARTOS' , 'C/La Dolorosa 34. Madrid. ESP', '91222333',  '2018-04-15');
	INSERT INTO estudiantes VALUES (8,'PEDRO SULIVAN' , 'C/Bardales 34. Talavera. ESP', '925444000', '2018-04-15' );
	INSERT INTO estudiantes VALUES (9,'MARIA TENAILLE' , 'C/Frco Aguirre 30. Talavera. ESP', '925888777', '2018-04-25' );

	INSERT INTO estudiantes VALUES (10,'JUANDE RAMOS' , 'C/Los Dolomitas 23. Madrid. ESP', '91000999',  '2018-04-15');
	
	INSERT INTO estudiantes VALUES (11,'MARÍA JIMÉNEZ SULIVAN' , 'C/Buero Vallejo 43. Guadalajara. ESP', '925444000', '2018-01-15' );
	INSERT INTO estudiantes VALUES (12,'ANTONIO CAMACHO' , 'C/Emerson Fitipaldi 6. Talavera. ESP', '925888777', '2018-02-25' );
	INSERT INTO estudiantes VALUES (13,'MARÍA GUERRERO' , 'C/Las Galeras 32. Madrid. ESP', '910088771', '2018-04-25' );

	INSERT INTO estudiantes VALUES (19,'JUAN RAMÓN PASOS' , 'C/Las Espadas32. Talavera. ESP', '925762222', '2023-10-01' );
	INSERT INTO estudiantes VALUES (20,'MALENA ROJAS VERDES' , 'C/Las Galeras 32. Madrid. ESP', '910088771', '2023-10-01' );

	

/* Insert participa 
codparticipacion int NOT NULL,	codestudiante int NOT NULL,	codigoproyecto int NOT NULL,
	tipoparticipacion varchar(20),	numaportaciones int,  */

	/*estudiante 1 proyectos 1 y 2 */
	INSERT INTO participa VALUES (1,1,1,'Becario',0 );
	INSERT INTO participa VALUES (2,1,2,'Ayudante nivel 1', 2 );
	
	/*estudiante 2 proyectos 1 y 2 y 3*/
	INSERT INTO participa VALUES (3,2,1,'Becario',1 );
	INSERT INTO participa VALUES (4,2,2,'Ayudante nivel 1', 2 );
	INSERT INTO participa VALUES (5,2,3, 'Investigador', 0);
	
	/*estudiante 3 proyectos 4*/
	INSERT INTO participa VALUES (6,3,4,'Investigador', 1 );
	
	/*estudiante 4 proyectos 4*/
	INSERT INTO participa VALUES (7,4,4,'Becario',1 );
	
	/*estudiante 5 proyectos 4*/
	INSERT INTO participa VALUES (8,5,4,'Ayudante nivel 1', 2 );
	
	
	/*estudiante 6 proyectos 1*/
	INSERT INTO participa VALUES (9,6,1,'Becario',1 );
	
	/*estudiante 7 proyectos 1*/
	INSERT INTO participa VALUES (10,7,1 ,'Investigador', 2 );
	
	/*estudiante 8 proyectos 2 y 3*/
	INSERT INTO participa VALUES (11,8,2,'Investigador', 1 );
	INSERT INTO participa VALUES (12,8,3,'Ayudante nivel 1', 2 );
	
	
	/*estudiante 9 proyectos 2 y 3*/
	INSERT INTO participa VALUES (13,9,2,'Investigador', 0 );
	INSERT INTO participa VALUES (14,9,3,'Investigador', 1 ); 
	
	/*estudiante 10 proyectos 4*/
	INSERT INTO participa VALUES (15,10,4,'Becario',0 );


	/*estudiante 11 proyectos 1 y 4*/
	INSERT INTO participa VALUES (16,11,1,'Becario',1 );
    INSERT INTO participa VALUES (17,11,4,'Becario',0 );


	/*estudiante 12 proyectos 2 y 3*/
	INSERT INTO participa VALUES (18,12,2,'Becario',2 );
	INSERT INTO participa VALUES (19,12,3,'Investigador',0 );
	
	/*estudiante 13 proyectos 4*/
	INSERT INTO participa VALUES (20,13,4,'Becario',0 );


	/* Proyecto  18 , est 19 y 20*/
	INSERT INTO participa VALUES (23,19,18,'Becario',0 );
	INSERT INTO participa VALUES (24,20,18,'Investigador',0 );
	
	
/* Insert entidades */
/*	codentidad int NOT NULL,descripcion varchar(30),	telefono varchar(10),
	direccion varchar(40),	contacto varchar(30), */

	INSERT INTO entidades VALUES (1,'Financiera de Electricidad', '91888999','Avda Madrid 4. Madrid','Pedro Ruiz');
	INSERT INTO entidades VALUES (2,'Financiera de IFC', '92588999','Avda Logroño 22. Talavera','Alicia Mateo');
	INSERT INTO entidades VALUES (3,'Banco emprendedores', '949220099','Avda Bardales 4. Guadalajara','Aurora Sánchez');
	INSERT INTO entidades VALUES (4,'Centros Comerciales 5G', '911880088','Polígono Alcalá 1. Madrid','Arsenio García');
	INSERT INTO entidades VALUES (5,'Gasolineras Limpias', '914443330','C/La mina 223. Madrid','Alfonso García');
	INSERT INTO entidades VALUES (10,'Banco de ciberseguridad', '91121212','C/Los Sauces 11. Madrid','Juade Ruiz');
	
/* Insert patrocina */
/*	codpatrocinio int NOT NULL,	codigoproyecto int NOT NULL,	codentidad int NOT NULL,
	importeaportacion float,	fechaaportacion date, */

	/* proyecto 1 entidad 1 y 2 */
	INSERT INTO patrocina VALUES (1,1, 1,50000,'2018-01-20');
	INSERT INTO patrocina VALUES (2,1, 2,75000,'2018-01-26');
	
	/*proyecto 2 entidad 3*/
	INSERT INTO patrocina VALUES (3,2, 3,150000,'2018-02-15');
	
	
	/*proyecto 3 entidad 3 y 4*/
	INSERT INTO patrocina VALUES (4,3, 3,85000,'2018-03-15');
	INSERT INTO patrocina VALUES (5,3, 4,60000,'2018-03-20');
	
	/*proyecto 4 entidad 3 y 4 y 5*/
	INSERT INTO patrocina VALUES (6,4, 3,25000,'2018-04-10');
	INSERT INTO patrocina VALUES (7,4, 4,15000,'2018-04-11');
	INSERT INTO patrocina VALUES (8,4, 5,30000,'2018-04-12');
	
	
	/* Entidades 10 en el proyecto 15 */
	INSERT INTO patrocina VALUES (10,15, 10,35000,'2023-09-12');
	
/* Create Foreign Keys */

ALTER TABLE patrocina
	ADD FOREIGN KEY (codentidad)
	REFERENCES entidades (codentidad)
;


ALTER TABLE participa
	ADD FOREIGN KEY (codestudiante)
	REFERENCES estudiantes (codestudiante)
;


ALTER TABLE participa
	ADD FOREIGN KEY (codigoproyecto)
	REFERENCES proyectos (codigoproyecto)
;


ALTER TABLE patrocina
	ADD FOREIGN KEY (codigoproyecto)
	REFERENCES proyectos (codigoproyecto)
;


ALTER TABLE usa
	ADD FOREIGN KEY (codigoproyecto)
	REFERENCES proyectos (codigoproyecto)
;


ALTER TABLE usa
	ADD FOREIGN KEY (codrecurso)
	REFERENCES recursos (codrecurso)
;

ALTER session  SET NLS_DATE_FORMAT='DD-MM-YYYY' ;

