select asi.codAsig as COD_ASIGNATURA, asi.nombre as NOMBRE, 
       asi.cursos.codCurso AS COD_CURSO , asi.cursos.denominacion AS NOMBRE, 
       asi.cursos.centros.nombre as NOMBRE_CENTRO
from Asignaturas asi
     left join asi.evaluacioneses ev
where ev.nota is null

