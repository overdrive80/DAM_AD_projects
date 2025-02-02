--VACIADO Y LLENADO DE TABLA ENTIDADES
TRUNCATE TABLE tbl_entidades DROP STORAGE;
INSERT INTO tbl_entidades SELECT * FROM entidades;
/

--VACIADO Y LLENADO DE LA TABLA RECURSOS
TRUNCATE TABLE tbl_recursos DROP STORAGE;
INSERT INTO tbl_recursos SELECT * FROM recursos;
/

--VACIADO Y LLENADO DE LA TABLA ESTUDIANTES
TRUNCATE TABLE tbl_estudiantes DROP STORAGE;
INSERT INTO tbl_estudiantes SELECT * FROM estudiantes;
/

--VACIADO Y LLENADO DE LA TABLA PROYECTOS CON TABLAS ANIDADAS VACIAS
TRUNCATE TABLE tbl_proyectos DROP STORAGE;
INSERT INTO tbl_proyectos 
  SELECT p.codigoproyecto, p.nombre, p.fechainicio, p.fechafin, p.presupuesto, p.extraaportacion,
         tbl_ani_patrocina(), tbl_ani_usa(), tbl_ani_participa()
  FROM proyectos p;
/
--SI TUVIERA REFERENCIAS EN ALGUN CAMPO, PARA AÑADIR VALOR USAMOS REF(CAMPO)
--Y EN EL FROM HAY QUE PONER LA TABLA DEL CAMPO. SELECT...REF(TABLA.CAMPO) FROM ...,TABLA WHERE ...=TABLA.ID;


