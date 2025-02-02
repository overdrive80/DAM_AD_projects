--Borrado de tipos con dependencias de tipo o tabla
DROP TYPE fila_proyecto FORCE;
DROP TABLE tbl_proyectos cascade constraint;

--Creamos los tipos de las tablas
CREATE OR REPLACE TYPE fila_proyecto AS OBJECT (
    codigoproyecto  NUMBER(10, 0),
    nombre          VARCHAR2(45),
    fechainicio     DATE,
    fechafin        DATE,
    presupuesto     FLOAT,
    extraaportacion FLOAT,
    patrocina       tbl_ani_patrocina,
    usa             tbl_ani_usa,
    participa       tbl_ani_participa
);
/

CREATE TABLE tbl_proyectos OF fila_proyecto (
  codigoproyecto PRIMARY KEY,
  nombre UNIQUE
)
  NESTED TABLE patrocina STORE AS tbl_ani_patrocina
  NESTED TABLE usa STORE AS tbl_ani_usa
  NESTED TABLE participa STORE AS tbl_ani_participa;
/