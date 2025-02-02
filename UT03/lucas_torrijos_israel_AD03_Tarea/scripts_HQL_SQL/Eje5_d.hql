SELECT alu.cursos.codCurso as COD_CURSO, alu.cursos.denominacion as DENOMINACION,
       alu.numAlumno AS COD_ALUMNO, alu.nombre AS NOMBRE_ALUMNO,
       ROUND(AVG(ev.nota), 1) AS MAX_NOTAMEDIA
FROM Alumnos alu
JOIN alu.evaluacioneses ev
GROUP BY alu.cursos.codCurso,  alu.cursos.denominacion,
      alu.numAlumno, alu.nombre
HAVING AVG(ev.nota) = (
	SELECT max(AVG(ev.nota))
	FROM Alumnos alu2
	JOIN alu2.evaluacioneses ev
	WHERE alu2.cursos = alu.cursos
	GROUP BY alu2.numAlumno
)
ORDER by alu.cursos

