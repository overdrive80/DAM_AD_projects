package gestionXML;

/**
 * La clase ObjectFactory suele contener métodos de fábrica para crear instancias 
 * de los objetos que representan los elementos del esquema XML. 
 * Estos métodos se utilizan para construir objetos que luego se pueden convertir a XML.
 */

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LeerXML {
	
	public static JAXBElement<?> run(Class<?> claseInstancia, String archivoXML, boolean verbose) {
		try {
			// Creamos el contexto
			JAXBContext jaxbContext = JAXBContext.newInstance(claseInstancia);
			Unmarshaller unmarshaller  = jaxbContext.createUnmarshaller();
		
			JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(new FileInputStream(archivoXML));
			
			if (verbose) {
				Marshaller marshaller  = jaxbContext.createMarshaller();
				
				marshaller .setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				
				// Visualiza por consola
				marshaller.marshal(jaxbElement, System.out);
			}
			
			return jaxbElement;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el archivo " + archivoXML);
		}
		return null;

		
	}
	public static void run(Class<?> claseInstancia, String archivoXML) {
		try {
			// Creamos el contexto
			JAXBContext jaxbContext = JAXBContext.newInstance(claseInstancia);
			Unmarshaller u = jaxbContext.createUnmarshaller();
		
			JAXBElement<?> jaxbElement = (JAXBElement<?>) u.unmarshal(new FileInputStream(archivoXML));
			
			Marshaller m = jaxbContext.createMarshaller();
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Visualiza por consola
			m.marshal(jaxbElement, System.out);
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		}

		
	}
	
	/**
	 * Otro metodo sobrecargado para especificar el tipo de clase para el JAXBElement
	 * @param <claseJAXBElement>
	 * @param claseInstancia
	 * @param claseJAXBElement
	 * @param archivoXML
	 */
	@SuppressWarnings("unchecked")
	public static <claseJAXBElement> void run(Class<?> claseInstancia, Class<?> claseJAXBElement, String archivoXML) {
		try {
			// Creamos el contexto
			JAXBContext jaxbContext = JAXBContext.newInstance(claseInstancia);
			Unmarshaller u = jaxbContext.createUnmarshaller();
		
			JAXBElement<claseJAXBElement> jaxbElement = (JAXBElement<claseJAXBElement>) u.unmarshal(new FileInputStream(archivoXML));
			
			Marshaller m = jaxbContext.createMarshaller();
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Visualiza por consola
			m.marshal(jaxbElement, System.out);
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		}

		
	}

}