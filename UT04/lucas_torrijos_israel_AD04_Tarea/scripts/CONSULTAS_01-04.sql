--CONSULTA 01
select p.codigoproyecto AS CODIGOPROYECTO, p.nombre AS NOMBREPROYECTO, 
       deref(par.estudiante).codestudiante AS CODESTUDIANTE, 
       deref(par.estudiante).nombre AS NOMBREESTUDIANTE, 
       deref(par.estudiante).direccion AS DIRECCIONESTUDIANTE,
       par.numaportaciones AS NUMAPORTACIONES, 
       par.numaportaciones * p.extraaportacion as IMPORTE
from tbl_proyectos p,table(p.participa) par;

--CONSULTA 02
select p.codigoproyecto AS CODIGOPROYECTO, p.nombre AS NOMBREPROYECTO,
       sum(pat.importeaportacion), count(pat.importeaportacion)
from tbl_proyectos p,table(p.patrocina) pat
group by p.codigoproyecto, p.nombre;

--CONSULTA 03
select tes.codestudiante AS CODESTUDIANTE, tes.nombre AS NOMBRE, tes.direccion as DIRECCIÓN
from tbl_estudiantes Tes
where Tes.codestudiante not in (
    select distinct deref(par.estudiante).codestudiante
    from tbl_proyectos p, table(p.participa) par
);

--CONSULTA 04
SELECT 
    p.codigoproyecto AS CODIGOPROYECTO, p.nombre AS NOMBRE,
       NVL(SUM(deref(us.recurso).PVP*deref(us.recurso).unidades),0) AS TOTALGASTO_SINEXTRA,
       NVL(SUM((deref(us.recurso).extra+deref(us.recurso).PVP)*deref(us.recurso).unidades),0) AS TOTALGASTO
FROM tbl_proyectos p
LEFT JOIN TABLE(p.usa) us ON 1=1
GROUP BY p.codigoproyecto, p.nombre;