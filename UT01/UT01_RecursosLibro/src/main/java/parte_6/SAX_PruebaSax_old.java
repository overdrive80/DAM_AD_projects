package parte_6;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
//import org.xml.sax.helpers.XMLReaderFactory;

@SuppressWarnings("deprecation")
public class SAX_PruebaSax_old {

	public static void main(String[] args) throws FileNotFoundException, IOException, SAXException {

		try {
	        
			XMLReader procesadorXML = XMLReaderFactory.createXMLReader(); //Obsoleto
			AlumnosHandler gestor = new AlumnosHandler();
			
			procesadorXML.setContentHandler(gestor);
		    
			InputSource fileXML = new InputSource("alumnos.xml");
			procesadorXML.parse(fileXML);
			
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}
}// fin PruebaSax1

class GestionContenido extends DefaultHandler {
	public GestionContenido() {
		super();
	}
	@Override
	public void startDocument() {
		System.out.println("Comienzo del Documento XML");
	}
	@Override
	public void endDocument() {
		System.out.println("Final del Documento XML");
	}
	@Override
	public void startElement(String uri, String nombre, String nombreC, Attributes atts) {
		System.out.printf("\t Principio Elemento: %s %n", nombreC);
	}
	@Override
	public void endElement(String uri, String nombre, String nombreC) {
		System.out.printf("\tFin Elemento: %s %n", nombreC);
	}
	@Override
	public void characters(char[] ch, int inicio, int longitud) throws SAXException {
		String car = new String(ch, inicio, longitud);
		// quitar saltos de l�nea
		car = car.replaceAll("[\t\n]", "");
		System.out.printf("\t Caracteres: %s %n", car);
	}

}// fin GestionContenido
