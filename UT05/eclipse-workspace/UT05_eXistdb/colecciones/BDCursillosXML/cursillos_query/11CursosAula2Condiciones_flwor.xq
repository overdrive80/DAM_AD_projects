for $curso in  collection("/db/BDCursillosXML/cursillos_datos/Cursos")/curso
let $n:=($curso/nombre)
where $curso/aula=2 and $curso/precio[@cuota="mensual"] and 
$curso/precio[@moneda="euro"] and $curso/precio<50
order by $curso/nombre
return data($n)