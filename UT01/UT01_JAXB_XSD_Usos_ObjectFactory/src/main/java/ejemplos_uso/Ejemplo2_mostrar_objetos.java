package ejemplos_uso;
import java.io.File;

import gestionXML.LeerXML;
import jakarta.xml.bind.JAXBElement;
import clases.*;
import java.util.List;

public class Ejemplo2_mostrar_objetos {
	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaXMLCentrosProfes = rutaDatos + File.separator +  "centrosprofes.xml";
	
	public static void main(String[] args) {
		
		//Visualizar objetos
		mostrarObjetos();
		
	}
	
	public static void mostrarObjetos() {
		
		JAXBElement<?> jaxbElement = (JAXBElement<?>) LeerXML.run(ObjectFactory.class, rutaXMLCentrosProfes, false);
		
		//Obtenemos todos los Centros, el elemento raiz del XML
		Centros centros = (Centros) jaxbElement.getValue();
		
		//En un arrayList volcamos los datos de todos los centros. Un nivel inferior a la raiz
		List<Centro> listaCentros = centros.getListaCentros();
		
		//Los datos del centro están compuestos por datos especificos del centro y una lista de profesores
		System.out.println("\nDATOS DE LOS CENTROS:\n");
		for (Centro objCentro: listaCentros) {
			// IMPRIMIMOS LOS DATOS DEL CENTRO
			DatosCentro datosCentro = objCentro.getDatoscentro();
			
			System.out.println("\tCodigo: " + datosCentro.getCodigocentro());
			System.out.println("\tNombre: " + datosCentro.getNombrecentro());
			System.out.println("\tDirección: " + datosCentro.getDireccion());
			
			Profesor director = datosCentro.getDirector();
			System.out.printf("\tEl director del centro es: %s (%s), con un salario de: %s%n%n",
					director.getNombreprofe(), director.getCodigoprofesor(), director.getSalario());
			
			//IMPRIMIMOS LOS PROFESORES DEL CENTRO
			System.out.println("\tLos profesores del centro son:\n");
			Profesores profesores =  objCentro.getProfesores();
			List<Profesor> listaProfesores = profesores.getListaProfesores();
			
			for (Profesor objProfesor: listaProfesores) {
				System.out.println("\t\tCódigo: " + objProfesor.getCodigoprofesor());
				System.out.println("\t\tNombre: " + objProfesor.getNombreprofe());
				System.out.println("\t\tSalario: " + objProfesor.getSalario());
				System.out.println();
			}
			
			System.out.println("\t" + "-".repeat(87));
		}
		
	}
}
