package ejercicios;

//import org.w3c.dom.*;
//import org.xmldb.api.*;
//import org.w3c.dom.*;
//import javax.xml.transform.*;

import org.xmldb.api.base.Collection;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.*;

import logica.ExistManager;

public class Ejercicio01 {
	private Collection coleccion;
	private String docColeccion = "doc(\"productos.xml\")";
	private String descendant = "//";
	private String indent = " ".repeat(3);

	public Ejercicio01() {
		this.coleccion = ExistManager.getColeccionActual();
		this.iniciar();
	}

	private void iniciar() {

		// Apartado 1. APUNTES
		String enunciado = "1) Obtener los nodos de denominación y precio de todos los productos.";
		String xpath = docColeccion + descendant + "(denominacion | precio)";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 2.
		enunciado = "2) Obtener los nodos de los productos que sean memorias DDR3.";
		xpath = docColeccion + descendant + "produc[denominacion[contains(., 'DDR3')]]";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 3.
		enunciado = "3) Obtener los nodos de los productos con precio mayor de 60 € y de la zona 20.";
		xpath = docColeccion + descendant + "produc[precio > 60 and cod_zona = 20]";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 4.
		enunciado = "4) Obtener el número de productos que sean memorias y de la zona 10.";
		xpath = "count(" + docColeccion + descendant + "produc[denominacion[contains(., 'Memoria')] and cod_zona=10])";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 5. APUNTES
		enunciado = "5) Obtener la media redondeada del precio de los micros.";
		xpath = "round(avg(" + docColeccion + descendant + "produc[denominacion[contains(., 'Micro')]]/precio))";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 6.
		enunciado = "6) Obtener los datos de los productos cuyo stock mínimo sea mayor que su stock actual.";
		xpath = docColeccion + descendant + "produc[number(stock_minimo) > number(stock_actual)]";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 7.
		enunciado = "7) Obtener el nombre de producto y el precio de aquellos cuyo stock mínimo sea mayor que su\r\n"
				+ "stock actual y sean de la zona 40";
		xpath = docColeccion + descendant
				+ "produc[(number(stock_minimo) > number(stock_actual)) and cod_zona=40]/(denominacion | precio)";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 8.
		enunciado = "8) Obtener el producto más caro.";
		xpath = docColeccion + descendant + "produc[precio=max(//precio)]";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 9. APUNTES
		enunciado = "9) Obtener el producto más barato de la zona 20.";
		xpath = docColeccion + descendant + "produc[cod_zona=20][precio=min(" + docColeccion + descendant
				+ "produc[cod_zona=20]/precio)]";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		// Apartado 10.
		enunciado = "10) Obtener el producto más caro de la zona 10.";
		xpath = docColeccion + descendant + "produc[cod_zona=10][precio=max(" + docColeccion + descendant
				+ "produc[cod_zona=10]/precio)]";

		printEnunciado(enunciado, xpath);
		ejecutarConsulta( xpath);

		ExistManager.cerrarColeccion(coleccion);
		
	}
	
	private void printEnunciado(String enunciado, String consulta) {
		System.out.println(enunciado + "\n");
		System.out.println(indent + consulta + "\n");
		System.out.println(indent + "Resultado:");
	}

	private void ejecutarConsulta(String consulta) {

		try {
			XPathQueryService servicio = (XPathQueryService) coleccion.getService("XPathQueryService", "1.0");
			servicio.setProperty("indent", "yes");

			ResourceSet result = servicio.query(consulta);
			ResourceIterator i = result.getIterator();
			Resource res = null;

			while (i.hasMoreResources()) {
				res = i.nextResource();
				String contenido = (String) res.getContent();

				// System.out.println(indent+contenido);

				String[] lineas = contenido.split(System.lineSeparator());

				for (String linea : lineas) {
					System.out.println(indent + linea);
				}

				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}

			}

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}
		System.out.println();

	}
}
