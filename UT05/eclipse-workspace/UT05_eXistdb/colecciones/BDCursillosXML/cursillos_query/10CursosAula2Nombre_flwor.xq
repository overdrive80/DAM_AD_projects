for $curso in  collection("/db/BDCursillosXML/cursillos_datos/Cursos")/curso
let $n:=($curso/nombre)
where $curso/aula=2
order by $curso/nombre
return data($n)