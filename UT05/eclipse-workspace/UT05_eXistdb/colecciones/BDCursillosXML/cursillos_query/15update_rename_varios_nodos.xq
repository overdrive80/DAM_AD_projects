
(: renombre el nodo 'empresa/web' poniendolo en mayusculas:)
for $x in doc("/db/BDCursillosXML/cursillos_datos/Empresa.xml")/empresas/empresa/web
return
update rename  $x 
as upper-case(name($x))

