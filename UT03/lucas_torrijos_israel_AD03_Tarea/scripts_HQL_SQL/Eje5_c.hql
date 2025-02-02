--Mas correcta
select asi.codAsig as COD_ASIGNATURA, asi.nombre as NOMBRE,
	   asi.cursos.codCurso as COD_CURSO, asi.cursos.denominacion as NOMBRE,
	   asi.cursos.centros.nombre as CENTRO, count(ev.alumnos) as NUMERO_ALUMNOS,
	   ROUND(AVG(ev.nota),1) as MEDIA_NOTA
from Asignaturas asi left join asi.evaluacioneses ev
where ev.nota is not null and ev.id.codEvaluacion = 3
group by asi.codAsig, asi.nombre,
	   asi.cursos.codCurso, asi.cursos.denominacion, asi.cursos.centros.nombre 

--Mas liosa
select asi2.codAsig as COD_ASIGNATURA, asi2.nombre as NOMBRE, 
	   a2.cursos.codCurso as COD_CURSO,a2.cursos.denominacion as NOMBRE, 
	   a2.cursos.centros.nombre as CENTRO, count(distinct a2.nombre) as NUMERO_ALUMNOS,
	   ROUND(COALESCE(AVG(CASE WHEN e2.id.codEvaluacion = 3 THEN e2.nota ELSE null END), 0),1) as MEDIA_NOTA
from Alumnos a2
left join a2.evaluacioneses e2
left join e2.asignaturas asi2 
where e2.nota is not null and e2.id.codEvaluacion = 3
group by asi2.codAsig, asi2.nombre, a2.cursos.codCurso,a2.cursos.denominacion, a2.cursos.centros.nombre




