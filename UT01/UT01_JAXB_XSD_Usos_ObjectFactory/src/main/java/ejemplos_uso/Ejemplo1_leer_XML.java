package ejemplos_uso;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import gestionXML.LeerXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;
import jakarta.xml.bind.Unmarshaller;
import clases.*;

public class Ejemplo1_leer_XML {
	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaXMLCentrosProfes = rutaDatos + File.separator +  "centrosprofes.xml";
	
	public static void main(String[] args) {
		//Mostramos el contenido del XML
		mostrarXML();
		
	}
	
	public static void mostrarXML(){
		
		//LeerXML.run(ObjectFactory.class, rutaXMLCentrosProfes);
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();

			JAXBElement<?> jaxbElement = (JAXBElement<?>) u.unmarshal(new FileInputStream(rutaXMLCentrosProfes));
			
			Marshaller m = jaxbContext.createMarshaller();
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Visualiza por consola
			m.marshal(jaxbElement, System.out);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
}
