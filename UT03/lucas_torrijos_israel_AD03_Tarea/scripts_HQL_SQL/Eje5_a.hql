SELECT ce.codCentro as COD_CENTRO, ce.nombre as NOMBRE, 
       coalesce(cu.codCurso, 0) as COD_CURSO, NVL(cu.denominacion,'Sin cursos') as NOMBRE_CURSO, 
       coalesce(count(al),0) as NUMA_ALUMNOS
FROM Centros ce 
	left join ce.cursoses cu 
	left join cu.alumnoses al
GROUP by ce.codCentro, ce.nombre, cu.codCurso, cu.denominacion
HAVING count(al) = (
	SELECT coalesce(max(count(al2)),0)
	FROM Centros ce2 
		left join ce2.cursoses cu2 
		left join cu2.alumnoses al2
	WHERE ce2 = ce
	GROUP by cu2.codCurso
)