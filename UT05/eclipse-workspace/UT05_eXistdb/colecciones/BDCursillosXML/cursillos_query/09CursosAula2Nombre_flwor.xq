for $curso in  collection("/db/BDCursillosXML/cursillos_datos/Cursos")/curso
where $curso/aula=2
order by $curso/nombre
return $curso/nombre