select asi.codAsig as COD_ASIGNATURA, asi.nombre as NOMBRE, 
       asi.cursos.codCurso AS COD_CURSO , asi.cursos.denominacion AS NOMBRE, 
       asi.cursos.centros.nombre as NOMBRE_CENTRO
FROM Centros ce
JOIN ce.cursoses cu
JOIN cu.asignaturases asi
WHERE NOT EXISTS (
    SELECT 1
    FROM Alumnos al
    JOIN al.evaluacioneses ne
    WHERE ne.asignaturas = asi
    AND al.cursos = cu
)