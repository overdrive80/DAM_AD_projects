(: Con value cambiamos contenido, con replace se cambia nodo :)
update value 
doc("/db/BDCursillosXML/cursillos_datos/Empresa.xml")/empresas/empresa[@id="1"]/email
with "EmpresaAlfa@dominiox.es",
update replace 
doc("/db/BDCursillosXML/cursillos_datos/Empresa.xml")/empresas/empresa[@id="1"]/telefono
with <telefono>888888</telefono>


