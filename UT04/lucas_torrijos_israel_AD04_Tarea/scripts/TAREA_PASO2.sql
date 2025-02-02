--Borrado de tipos con dependencias de tipo o tabla
DROP TYPE fila_patrocina FORCE;
DROP TYPE fila_usa FORCE;
DROP TYPE fila_participa FORCE;
DROP TYPE tbl_patrocina FORCE;
DROP TYPE tbl_usa FORCE;
DROP TYPE tbl_participa FORCE;
/

--Creamos los tipos de las tablas
CREATE OR REPLACE TYPE fila_patrocina AS OBJECT (
    codpatrocinio     NUMBER(10, 0),
    entidad           REF fila_entidad, --análogo a clave foránea
    importeaportacion FLOAT,
    fechaaportacion   DATE
);
/

CREATE OR REPLACE TYPE fila_usa AS OBJECT (
    coduso          NUMBER(10, 0),
    recurso         REF fila_recurso, --análogo a clave foránea
    cantidad        NUMBER(10, 0),
    fechaasignacion DATE
);
/

CREATE OR REPLACE TYPE fila_participa AS OBJECT (
    codparticipacion  NUMBER(10, 0),
    estudiante        REF fila_estudiante, --análogo a clave foránea
    tipoparticipacion VARCHAR2(20),
    numaportaciones   NUMBER(10, 0)
);
/

--Creamos las tablas anidadas
CREATE OR REPLACE TYPE tbl_ani_patrocina AS TABLE OF fila_patrocina;
/
CREATE OR REPLACE TYPE tbl_ani_usa AS TABLE OF fila_usa;
/
CREATE OR REPLACE TYPE tbl_ani_participa AS TABLE OF fila_participa;
/