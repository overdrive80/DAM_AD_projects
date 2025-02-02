SELECT ev.asignaturas.codAsig as COD_ASIGNATURA,
       ev.asignaturas.nombre as NOMBRE,
       ev.asignaturas.cursos.codCurso as COD_CURSO,
       ev.asignaturas.cursos.denominacion as NOMBRE_CURSO,
       ev.alumnos.numAlumno as COD_ALUMNO,
       ev.alumnos.nombre as NOMBRE_ALUMNO,
       ev.nota as NOTAASIG,
       ev.id.codEvaluacion as EVALUACION
FROM Evaluaciones ev
WHERE ev.asignaturas.cursos.centros.localidad = 'TOLEDO'
AND (ev.asignaturas.codAsig, ev.id.codEvaluacion, ev.nota) IN (
    SELECT a.codAsig, e.id.codEvaluacion, MAX(e.nota)
    FROM Evaluaciones e
    JOIN e.asignaturas a
    WHERE e.asignaturas = ev.asignaturas
    GROUP BY a.codAsig, e.id.codEvaluacion
)
ORDER BY ev.asignaturas.codAsig, ev.id.codEvaluacion

