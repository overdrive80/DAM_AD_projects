(:Inserta dos nodos uno al principio, en la posición 1, y el otro al final del documento :)
update insert 
<empresa id="2">
		<nombre>Empresa Z</nombre>
		<cif>B32674129</cif>
		<direccion> C\ Las Marinas 25</direccion>
		<localidad>Granada</localidad>
		<provincia>Granada</provincia>
		<cpostal>18004</cpostal>
		<telefono>952245689</telefono> 
		<email>empresaz@dominioz.es</email>
		<web>www.dominioz.es</web>
	</empresa>
preceding (doc("/db/BDCursillosXML/cursillos_datos/Empresa.xml")/empresas/empresa[1]) ,

 update insert 
<empresa id="4">
		<nombre>Empresa Beta</nombre>
		<cif>B12374129</cif>
		<direccion> C\ Golondrina 45</direccion>
		<localidad>Granada</localidad>
		<provincia>Granada</provincia>
		<cpostal>18004</cpostal>
		<telefono>952123456</telefono> 
		<email>empresabeta@dominiobeta.es</email>
		<web>www.dominiobeta.es</web>
	</empresa>

into doc("/db/BDCursillosXML/cursillos_datos/Empresa.xml")/empresas


