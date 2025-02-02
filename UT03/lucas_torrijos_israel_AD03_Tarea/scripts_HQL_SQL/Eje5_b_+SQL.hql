select e.id.codAsig as COD_ASIGNATURA, e.asignaturas.nombre as NOMBRE,
       max(e.asignaturas.cursos.codCurso) as COD_CURSO, max(e.asignaturas.cursos.denominacion) as NOMBRE,
       max(e.id.numAlumno) as COD_ALUMNO, max(e.alumnos.nombre) as NOMBRE_ALUMNO,  
       max(e.nota) as NOTAASIG, e.id.codEvaluacion as EVALUACION
from Evaluaciones e
where e.id.codAsig in (
	SELECT a.codAsig
	FROM Centros ce
	left JOIN ce.cursoses c
	JOIN c.asignaturases a
	where ce.localidad = 'TOLEDO'
)
group by e.id.codAsig,e.id.codEvaluacion, e.asignaturas.nombre
order by e.id.codAsig, e.id.codEvaluacion