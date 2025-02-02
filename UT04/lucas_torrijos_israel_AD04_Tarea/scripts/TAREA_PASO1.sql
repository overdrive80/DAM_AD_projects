--Borrado de tipos con dependencias de tipo o tabla
DROP TYPE fila_entidad FORCE;
DROP TYPE fila_recurso FORCE;
DROP TYPE fila_estudiante FORCE;
/

--Borramos tablas si existen
DROP TABLE tbl_entidades cascade constraint;
DROP TABLE tbl_recursos cascade constraint;
DROP TABLE tbl_estudiantes cascade
constraint;
/
  
--Creamos los tipos de las tablas
CREATE OR REPLACE TYPE fila_entidad AS OBJECT (
    codentidad  NUMBER(10, 0),
    descripcion VARCHAR2(30),
    telefono    VARCHAR2(10),
    direccion   VARCHAR2(40),
    contacto    VARCHAR2(30)
);
/

CREATE OR REPLACE TYPE fila_recurso AS OBJECT (
    codrecurso  NUMBER(10, 0),
    descripcion VARCHAR2(35),
    unidades    NUMBER(10, 0),
    distancia   NUMBER(10, 0),
    extra       FLOAT,
    pvp         FLOAT
);
/

CREATE OR REPLACE TYPE fila_estudiante AS OBJECT (
    codestudiante NUMBER(10, 0),
    nombre        VARCHAR2(30),
    direccion     VARCHAR2(40),
    tlf           VARCHAR2(10),
    fechaalta     DATE
);
/

--Creamos las tablas
CREATE TABLE tbl_entidades OF fila_entidad (
  codentidad PRIMARY KEY
);
/

CREATE TABLE tbl_recursos OF fila_recurso (
  codrecurso PRIMARY KEY
);
/

CREATE TABLE tbl_estudiantes OF fila_estudiante (
  codestudiante PRIMARY KEY
);
/






