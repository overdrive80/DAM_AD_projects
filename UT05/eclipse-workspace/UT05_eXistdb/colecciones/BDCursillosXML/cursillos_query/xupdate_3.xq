(: cambiar el nombre del nodo <edificio> de cada
documento de la colección Aulas por <lugar>:)
for $x in collection("/db/BDCursillosXML/cursillos_datos/Aulas")/aula/edificio
return update rename $x as "lugar"