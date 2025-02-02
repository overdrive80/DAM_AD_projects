package parte_6;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class SAX_PruebaSax {

	public static void main(String[] args) throws FileNotFoundException, IOException, SAXException {

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser  = factory.newSAXParser();
	        XMLReader xmlReader = saxParser.getXMLReader();
	        
	        AlumnosHandler handler = new AlumnosHandler();
		    xmlReader.setContentHandler(handler);
		    
			InputSource fileXML = new InputSource("alumnos.xml");
			xmlReader.parse(fileXML);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}
}

class AlumnosHandler extends DefaultHandler {
	public AlumnosHandler() {
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
	public void startElement(String uri, String localName, String qName, Attributes atts) {
		//atts por si el elemento tiene atributos
		System.out.printf("\t Principio Elemento: %s %n", qName);
	}
	@Override
	public void endElement(String uri, String localName, String qName) {
		System.out.printf("\tFin Elemento: %s %n", qName);
	}
	@Override
	public void characters(char[] ch, int inicio, int longitud) throws SAXException {
		String car = new String(ch, inicio, longitud);
		// quitar saltos de línea
		car = car.replaceAll("[\t\n]", "");
		System.out.printf("\t Caracteres: %s %n", car);
	}

}
