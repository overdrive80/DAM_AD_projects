package gestionXML;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

@SuppressWarnings("unused")
public class GenerarXML {

	public static void run(Class<?> claseInstancia, Object raiz, String archivoXML) {
		try {
			JAXBContext context = JAXBContext.newInstance(claseInstancia);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Lo mostramos por pantalla, se puede añadir un parametro para controlar este
			// comportamiento
			m.marshal(raiz, System.out);
			
			// Generar en disco el archivo XML
			m.marshal(raiz, new File(archivoXML));
		} catch (JAXBException e) {

			e.printStackTrace();
		}

	}
}
