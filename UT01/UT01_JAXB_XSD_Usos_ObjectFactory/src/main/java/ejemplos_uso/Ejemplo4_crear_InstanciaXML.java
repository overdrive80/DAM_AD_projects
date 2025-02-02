package ejemplos_uso;

import java.io.File;
import java.math.BigInteger;
import clases.*;
import gestionXML.GenerarXML;
import jakarta.xml.bind.JAXBElement;

// Creamos una instancia del documento XML desde cero
public class Ejemplo4_crear_InstanciaXML {
	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaNuevoXMLCentrosProfes = rutaDatos + File.separator +  "centrosprofes_nueva_instancia.xml";
	
	public static void main(String[] args) {
		inicializar();
	}
	
	public static void inicializar() {
		
		// Creamos el objeto de tipo DatosCentro
		DatosCentro nuevoDatosCentro = new DatosCentro();
		
		nuevoDatosCentro.setCodigocentro(BigInteger.valueOf(1));
		nuevoDatosCentro.setNombrecentro("IES Miguel de Cervantes");
		nuevoDatosCentro.setDireccion("Paseo Arroyomolinos, 66; 28938 Móstoles (Madrid)");
		
		//Creamos al director
		Profesor director = new Profesor();
		director.setCodigoprofesor(BigInteger.valueOf(1));
		director.setNombreprofe("Israel Lucas");
		director.setSalario(2000);
		
		//Añadimos el director al Centro
		nuevoDatosCentro.setDirector(director);
		
		//Creamos los profesores que figuran en los datos del Centro
		Profesor profe = new Profesor();
		profe.setCodigoprofesor(BigInteger.valueOf(2));
		profe.setNombreprofe("Ichigo Kurosaki");
		profe.setSalario(1500);
		
		//Creamos el nuevo objeto de tipo profesores y añadimos el profesor
		Profesores profesores = new Profesores();
		profesores.getListaProfesores().add(profe);
		
		//Creamos otro profesor
		profe = new Profesor();
		profe.setCodigoprofesor(BigInteger.valueOf(3));
		profe.setNombreprofe("Mark Lenders");
		profe.setSalario(1500);
		
		//Añadimos otro profesor
		profesores.getListaProfesores().add(profe);
		
		// Creamos el objeto del centro
		Centro nuevoCentro = new Centro();
		nuevoCentro.setDatoscentro(nuevoDatosCentro);
		nuevoCentro.setProfesores(profesores);
			
		//Creamos el objeto que se mapea a la raiz del XML
		Centros centros = new Centros();
		
		//Añadimos el nuevo Centro a la lista del objeto raíz
		centros.getListaCentros().add(nuevoCentro);
		
		//Creamos el objeto ObjectFactory
		ObjectFactory factoria = new ObjectFactory();
		
		//Creamos el objeto que encapsula el objeto raíz
		JAXBElement<Centros> jaxbElement = factoria.createCentros(centros);
		
		GenerarXML.run(ObjectFactory.class, jaxbElement, rutaNuevoXMLCentrosProfes);
		
		/** Si la clase que está identificada para ser el tipo del elemento raíz del XML "Centros"
		 *  tiene la etiqueta @XmlRootElement, podríamos usar la siguiente sentencia:
		 */
		//GenerarXML.run(ObjectFactory.class, todosCentros, rutaNuevoXMLCentrosProfes);
		
	}
}
