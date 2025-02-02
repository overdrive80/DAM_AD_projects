for $curso in  collection("/db/BDCursillosXML/cursillos_datos/Cursos")/curso
where $curso/precio/@cuota="mensual"
order by $curso/nombre
return $curso/nombre