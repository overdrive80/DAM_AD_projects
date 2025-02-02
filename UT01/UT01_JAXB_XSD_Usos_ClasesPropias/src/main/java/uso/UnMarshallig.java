package uso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import clases.Centros;
import gestionXML.LeerXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;

@SuppressWarnings("unused")
public class UnMarshallig {

	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaXMLCentrosProfes = rutaDatos + File.separator +  "centrosprofes.xml";
	
	public static void main(String[] args) {
		importar();
	}
	
	public static void importar() {
		
		//Hacemos una deserializacion (Unmarshall)
		try {
			JAXBContext contexto = JAXBContext.newInstance(Centros.class);
			Unmarshaller unmarshall = contexto.createUnmarshaller();
			Centros centros;
			
			/** EN EL EXAMEN EMPLEA ESTE
			 * METODO 1. La clase raiz no debe tener
			 * anotaciones @XmlRegistry ni @XmlElementDecl
			 */
//			centros = (Centros) unmarshall.unmarshal(new FileInputStream(rutaXMLCentrosProfes));
			
			/**
			 * METODO 2. Es necesario usar StreamSource. 
			 * La clase raiz puede tener, o no, 
			 * anotaciones @XmlRegistry ni @XmlElementDecl
			 */		
//			Source source = new StreamSource(new FileInputStream(rutaXMLCentrosProfes));
//			JAXBElement<?> jaxbElement =  unmarshall.unmarshal(source, Centros.class);
//			centros = (Centros) jaxbElement.getValue();
			
			/* 
			 * METODO 3. 
			 * Para este modo es imprescicible que la clase mapeada como raíz implemente
			 * un método que devuelva un JAXBElement. Serán necesarias las 
			 * anotaciones @XmlRegistry // @XmlElementDecl
			 * 
			 *  */
			JAXBElement<?> jaxbElement =  (JAXBElement<?>) unmarshall.unmarshal(new FileInputStream(rutaXMLCentrosProfes) );
			centros = (Centros) jaxbElement.getValue();
			
			//Si queremos ver el resultado en consola debemos de usar el Marshaller con el elementoJAXB
			Marshaller m = contexto.createMarshaller();
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Visualiza por consola
			m.marshal(centros, System.out);
			
			
		} catch ( JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//LeerXML.run(Centros.class, rutaXMLCentrosProfes);
		
	}
}
