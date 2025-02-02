(: Productos con precio > 50 :)
for $prod in collection("/db/BDProductosXML")/productos/produc
where $prod/precio>50
return $prod 
