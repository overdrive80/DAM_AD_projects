for $curso in  collection("/db/BDCursillosXML/cursillos_datos/Cursos")/curso
let $profe:=$curso/profesor
where $curso/plazas=20
order by $curso/nombre
return $curso/nombre
