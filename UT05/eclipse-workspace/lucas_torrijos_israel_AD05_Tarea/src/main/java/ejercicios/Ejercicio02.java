package ejercicios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import logica.ExistManager;
@SuppressWarnings("unused")
public class Ejercicio02 {
	protected static Scanner teclado = new Scanner(System.in);
	private String docfacturas = "doc('facturas.xml')";
	private String docdetalles = "doc('detallefacturas.xml')";
	private String docproductos = "doc('productos.xml')";
	private Collection coleccion;
	private String retorno = "&#13;";

	public Ejercicio02() {
		this.coleccion = ExistManager.getColeccionActual();
	}

	public void apartadoA() {
		System.out.println("Actualizando el importe de las facturas...\n");

		// Primero. Actualizamos
		String XQuery = String.format("""
						let $detalle := %s//factura
				              let $producto := %s//product

						for $factura in %s//factura
						let $importe := sum(
						    for $prodDetalle in $detalle[@numero=$factura/@numero]/producto
						    let $descuento := number($prodDetalle/@descuento)
						    let $pvp := number($producto[codigo=$prodDetalle/codigo]/@pvp)
						    let $unidad := number($prodDetalle/unidades)
						    return ($unidad*$pvp)*(1-$descuento)
						    )
						return (
							update value $factura/importe with $importe,
							concat("Factura ", $factura/@numero, ", actualizada, Importe = ", $importe)
						    )
				""", docdetalles, docproductos, docfacturas);

		ExistManager.ejecutarConsulta(XQuery);
		System.out.println();
	}

	public void apartadoB() {
		/***** FACTURA *****/
		System.out.print("Teclea el número de factura: ");
		int numFactura = analizarValorDado();
		System.out.println();

		if (!validarFactura(numFactura, docdetalles)) {return;};

		/***** CODIGO *****/
		System.out.println("Datos del producto: ");
		System.out.print(" ".repeat(3) + "Código: ");
		int numCodigo = analizarValorDado();

		if (!validarDatosCodigo(numFactura, numCodigo)) {return;};

		/***** DESCUENTO *****/
		System.out.print(" ".repeat(3) + "Descuento: ");
		double descuento = analizarDecimalDado();

		if (!validarDescuento(descuento)) {return;};

		/***** UNIDADES *****/
		System.out.print(" ".repeat(3) + "Unidades: ");
		int numUnidades = analizarValorDado();

		if (!validarStock(numCodigo, numUnidades)) {return;};

	    // Si hay stock, insertar los detalles y disminuir el stock
	    String consultaInsertar = String.format("""
	    		let $producto := <producto descuento='%s'>
		             <codigo>%d</codigo>
		             <unidades>%d</unidades>
		           </producto>
	            return
	            update insert $producto into %s//factura[@numero=%d]
	            """, String.valueOf(descuento), numCodigo, numUnidades, docdetalles, numFactura);

	    ExistManager.ejecutarConsulta(consultaInsertar);

	    // Descontar el stock
	    String consultaDescontar = String.format("""
	            let $stock := %s//product[codigo='%d']/stock
	            return
	            update value $stock with data($stock)-%d
	            """, docproductos, numCodigo, numUnidades);

	    ExistManager.ejecutarConsulta(consultaDescontar);

	    System.out.println();
	}

	public void apartadoC() {
		System.out.print("Teclea el número de factura a consultar: ");
		int numFactura = analizarValorDado();
		System.out.println();

		if (!validarFactura(numFactura, docfacturas)) {return;};

		StringBuilder build = new StringBuilder();
		build.append(String.format("for $factura in %s//factura[@numero=%d]", docfacturas, numFactura));
		build.append("return");
		build.append(String.format("<factura numero='{$factura/@numero}'>%s", retorno));
		build.append(String.format("%3s{$factura/fecha}%s", " ".repeat(3), retorno));
		build.append(String.format("%3s{$factura/importe}%s", " ".repeat(3), retorno));
		build.append(String.format("%3s{$factura/numcliente}%s", " ".repeat(3), retorno));
		build.append("</factura>");

		// System.out.println(build.toString());

		ExistManager.ejecutarConsulta(build.toString());
		System.out.println();
	}

	public void apartadoD() {
		System.out.print("Teclea el número de factura a consultar para mostrar los detalles: ");
		int numFactura = analizarValorDado();
		System.out.println();
		
		if (!validarFactura(numFactura, docdetalles)) {return;};
		
		
		String consulta =String.format("""
			for $factura in %s//factura[@numero=%d]
			return
			<factura numero="{$factura/@numero}">&#13;
			    <codigo>{$factura/data(codigo)}</codigo>&#13;
			  {
			    for $producto in $factura/producto
			    return
			    ( '  ', 
			        <producto descuento="{$producto/@descuento}">&#13;
			        <codigo>{$producto/data(codigo)}</codigo>&#13;
			        <unidades>{$producto/data(unidades)}</unidades>&#13;
			    </producto>,
			      if ($producto is $factura/producto[last()]) then '&#10;' else '&#10; '
			    )
			  }
			</factura>
				""", docdetalles, numFactura );

		ExistManager.ejecutarConsulta(consulta);
		System.out.println();

	}
	
	
	
	public void apartadoA_OpcionAlternativa() {
		System.out.println("Actualizando el importe de las facturas...\n");	
		
		try {		
			XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			servicio.setProperty("indent", "yes");
			
			// Primero recorremos las facturas con los importes a actualizar
			String consultaFacturas = String.format("%s//factura/data(@numero)", docfacturas);

			ResourceSet rsFacturas = servicio.query(consultaFacturas);
			ResourceIterator it = rsFacturas.getIterator();
			XMLResource nodoFactura = null;
			

			while (it.hasMoreResources()) {
				Double importe = 0.0;
				
				nodoFactura = (XMLResource) it.nextResource();
				String numFactura = (String) nodoFactura.getContent();
				
				//System.out.println("Factura número " + numFactura);
				String consultaDetalles = String.format("%s//factura[@numero=%s]/producto/(data(@descuento) , data(codigo) , data(unidades))", docdetalles, numFactura);
				
				ResourceSet rsDetalles = servicio.query(consultaDetalles);
				ResourceIterator itDetalles = rsDetalles.getIterator();
				Resource nodoDetalle= null;
				
				//System.out.println(rsDetalles.getSize());
				
				while (itDetalles.hasMoreResources()) {
					nodoDetalle = (XMLResource) itDetalles.nextResource();
					String descuento = (String) nodoDetalle.getContent();
					
					nodoDetalle = (XMLResource) itDetalles.nextResource();
					String codigoProd = (String) nodoDetalle.getContent();
					
					nodoDetalle = (XMLResource) itDetalles.nextResource();
					String unidades = (String) nodoDetalle.getContent();
					
					//System.out.println(String.format("%s, %s, %s", descuento,codigoProd, unidades));
					
					// Ahora hay que obtener el precio de cada producto del codigoProd
					String consultaProducto = String.format("distinct-values(%s//product[codigo='%s']/data(@pvp))", docproductos, codigoProd);
					ResourceSet rsPrecio = servicio.query(consultaProducto);
					
					// Si no obtenemos resultado el precio debe ser cero
					double precio= 0.0;
					if (rsPrecio.getSize() > 0) {
						Resource nodoPrecio = rsPrecio.getResource(0);
						precio = Double.parseDouble((String) nodoPrecio.getContent());
					} 
					
					//System.out.println(String.format("Producto %s", codigoProd) );
					
					// Calculamos el subimporte de cada producto
					Integer iUnidades = Integer.parseInt(unidades);
					Double dblDescuento = Double.parseDouble(descuento);
					
					Double subimporte = (precio * iUnidades) * (1 - dblDescuento);
					
					importe = importe + subimporte;
				}
							
				String consultaActualiza = String.format("update value %s//factura[@numero='%s']/importe with %s", docfacturas, numFactura, importe);
				servicio.query(consultaActualiza);
				
				String importeFormato = convertirNumero(importe, "#.00", true);
				
				System.out.println(String.format("Factura %s, actualizada, Importe = %s", numFactura, importeFormato));
			}
			
			System.out.println();

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}

	}
	
	public String convertirNumero(double numero, String patron, boolean puntoDecimal) {
		String miPatron = patron;
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.getDefault());
		
		simbolos.setDecimalSeparator(',');
		simbolos.setGroupingSeparator('.');

		if (miPatron == null || miPatron.equals("") ) {
			miPatron = "#,###,###,##0.00";
		}

		if (puntoDecimal) {
			simbolos.setDecimalSeparator('.');
			simbolos.setGroupingSeparator(',');
		} 

		DecimalFormat formato = new DecimalFormat(miPatron, simbolos);

		String numeroFormato = formato.format(numero);

		return numeroFormato;
	}

	protected int analizarValorDado() {
		int opcion;

		// Las opciones solo admiten valor númerico
		try {
			opcion = teclado.nextInt();
			teclado.nextLine();

			// Validar que el valor sea positivo
			if (opcion < 0) {
				// System.out.println("No es un número positivo.");
				return -2;
			}
		} catch (java.util.InputMismatchException e) // error con otros caracteres
		{
			// System.out.println("No es un número.");
			teclado.nextLine();
			return -1;
		}
		return opcion;
	}

	protected double analizarDecimalDado() {
		double opcion;

		// Las opciones solo admiten valor númerico
		try {
			String entrada = teclado.next();
			teclado.nextLine(); // Consumir el salto de línea después de la entrada

			if (entrada.contains(",")) {
				return -4;
			}

			opcion = Double.parseDouble(entrada);

			// Validar que el valor sea positivo
			if (opcion < 0) {
				// System.out.println("No es un número positivo.");
				return -2;
			}
		} catch (java.util.InputMismatchException e) // error con otros caracteres
		{
			// System.out.println("No es un número.");
			teclado.nextLine();
			return -1;
		} catch (NumberFormatException e) {
			// System.out.println("No es un número decimal.");
			teclado.nextLine();
			return -3;
		}
		return opcion;
	}

	protected void opcionIncorrecta() {
		System.out.print("Opción incorrecta. ");
		// pause();
	}

	/********** METODOS DE VALIDACION ***************/
	private boolean validarFactura(int numFactura, String recurso) {
		// Errores que provocan salida. Texto no numerico y numeros negativos
		switch (numFactura) {
		case -1:
			opcionIncorrecta();
			return false;
		case -2:
			System.out.println("No hay facturas con codigo negativo.\n");
			return false;
		}

		// Comprobamos si existe factura
		if (!existeFactura(numFactura, recurso)) {
			System.out.println("No existe la factura: " + numFactura);
			System.out.println();
			return false;
		}
		
		return true;
	}

	private boolean validarDatosCodigo(int numFactura, int numCodigo) {
		switch (numCodigo) {
		case -1:
			System.out.println();
			opcionIncorrecta();
			return false;
		case -2:
			System.out.println("\nNo hay codigos negativos.\n");
			return false;
		}

		if (!existeCodigoProducto(numFactura, numCodigo)) {
			System.out.println(String.format("\nNo existe el código %d para la factura %d. No se insertará.", numCodigo,
					numFactura));
			System.out.println();
			return false;
		}
		
		return true;
	}

	private boolean validarDescuento(double descuento) {
		// DESCUENTO. Errores que provocan salida. Texto no numerico, numeros negativos
		// y uso de comas como decimal
		int icase = (int) descuento;
		switch (icase) {
		case -1:
			System.out.println();
			opcionIncorrecta();
			return false;
		case -2:
			System.out.println("\nNo hay codigos negativos.\n");
			return false;
		case -3:
			System.out.println("\nNo es un número decimal.\n");
			return false;
		case -4:
			System.out.println("\nEl descuento no es correcto.\n");
			return false;
		}
		
		return true;

	}

	private boolean validarStock(int numCodigo, int numUnidades) {
		// UNIDADES. Errores que provocan salida. Texto no numerico y numeros negativos
		switch (numUnidades) {
		case -1:
			opcionIncorrecta();
			return false;
		case -2:
			System.out.println("No hay unidades negativas.\n");
			return false;
		}

		// Comprobar stock
		if (!existeStock(numCodigo, numUnidades)) {
			System.out.println("\nNo existe stock suficiente. No se insertará.");
			System.out.println();
			return false;
		}
		
		return true;
	}

	/********** METODOS DE EXISTENCIA ****************/
	private boolean existeFactura(int numero, String recurso) {

		try {
			XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			servicio.setProperty("indent", "yes");

			String consulta = String.format("let $factura:=%s//factura[@numero=%d] return $factura", recurso, numero);
			ResourceSet result = servicio.query(consulta);

			if (result.getSize() > 0) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}
		return false;
	}

	private boolean existeCodigoProducto(int numFactura, int numCodigo) {

		try {
			XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			servicio.setProperty("indent", "yes");

			String consulta = String.format("let $codigo := %s//factura[@numero=%d]/producto[codigo=%d] return $codigo",
					docdetalles, numFactura, numCodigo);
			ResourceSet result = servicio.query(consulta);

			if (result.getSize() > 0) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}
		return false;
	}

	private boolean existeStock(int codProducto, int unidades) {

		try {
			XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			servicio.setProperty("indent", "yes");

			String consulta = String.format("let $stock := %s//product[codigo=%d]/data(stock) return $stock",
					docproductos, codProducto);
			ResourceSet result = servicio.query(consulta);

			if (result.getSize() > 0) {
				Resource resource = result.getResource(0);
				String strStock = resource.getContent().toString().trim();
				int stock = Integer.parseInt(strStock);

				if (stock >= unidades) {
					return true;
				}
			}

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	/**** SOLUCION ACTUALIZACION PROFESORA ****/
	

	private void Actualizacion() {

		String consulta = String.format("%s//factura/data(@numero)", docfacturas);
	
			try {

				XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");

				ResourceSet result = servicio.query(consulta);

				// recorrer los datos del recurso.
				ResourceIterator i = result.getIterator();

				if (!i.hasMoreResources()) {
					System.out.println("<< NO EXISTE LA FACTURA >>");
					
				} else {

					while (i.hasMoreResources()) {

						Resource r = i.nextResource();

						// System.out.println("------------------------------------------------------------");
						String numerofactura = (String) r.getContent();

						ActualizarFactura(numerofactura);

					}

					// System.out.println("Importe de la factura : "+Suma);

				}

				//coleccion.close();

			} catch (Exception e) {

				System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
				e.printStackTrace();

			}


	}// Actualizacion

	private void ActualizarFactura(String numero) {

		Double Suma = 0.0;

		// consulta para obtener la suma total de una factura

		String query = String.format("for $r in %s/detallefacturas/factura[@numero=" + numero + "]/producto " + 
		               "let $cod:=$r/codigo " + 
				       "let $descu:= data($r/[@descuento]) " + 
		               "let $pvp:=data(%s/productos/product[codigo=$cod]/@pvp) " + 
				       "let $cant:=data($r/unidades) " + 
		               "let $total:=$cant*$pvp - $cant*$pvp*$descu " + 
				       "return $total", docdetalles, docproductos);

			try {

				XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
				
				ResourceSet result = servicio.query(query);

				// recorrer los datos del recurso.
				ResourceIterator i = result.getIterator();

				if (!i.hasMoreResources()) {
					System.out.println("<< NO EXISTE LA FACTURA >>");
				} else {

					while (i.hasMoreResources()) {

						Resource r = i.nextResource();

						// System.out.println("------------------------------------------------------------");
						double importe = Double.parseDouble((String) r.getContent());
						Suma = Suma + importe;

					}

					// System.out.println("Importe de la factura : " + Suma);

					String cadena = "update value " + "/facturas/factura[@numero=" + numero + "]/importe with " + Suma;
					result = servicio.query(cadena);
					System.out.println("\tFactura " + numero + ", actualizada, Importe = " + Suma);

					//coleccion.close();

				}

			} catch (Exception e) {

				System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
				e.printStackTrace();

			}
	}
}
