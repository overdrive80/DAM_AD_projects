package ejemplos_uso;

import java.math.BigInteger;
import java.util.List;

import clases.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;

public class Ejemplo5_modificar_XML {
	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaXMLCentrosProfes = rutaDatos + File.separator +  "centrosprofes.xml";
	private static final String rutaNuevoXMLCentrosProfes = rutaDatos + File.separator +  "centrosprofes_modificado.xml";
	
	public static void main(String[] args) {
		modificarCentro(BigInteger.valueOf(1));
	}
	
	private static void modificarCentro(BigInteger codigoCentro) {
		// Esta es la opcion corta
		//JAXBElement jaxbElement = LeerXML.run(ObjectFactory.class, rutaXMLCentrosProfes, false);
		
		//Esta es la opcion larga si reutilizacion de código. Sólo se esta usando con fin didáctico
		JAXBContext contexto;
		try {
			// Leemos el XML
			contexto = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			
			JAXBElement<?> jaxbElement = (JAXBElement<?>)unmarshaller.unmarshal(new FileInputStream(rutaXMLCentrosProfes));
			Centros centros = (Centros) jaxbElement.getValue();
			List<Centro> listaCentros = centros.getListaCentros();
			
			//Recorremos los Centros y modificamos el contenido
			for (Centro objCentro:listaCentros) {
				
				DatosCentro datosCentro = objCentro.getDatoscentro();
				
				if (datosCentro.getCodigocentro().equals(codigoCentro)) {
					
					datosCentro.setDireccion("NUEVA DIRECCION POR DETERMINAR");
				}
				
			}
			
			// Ahora hacemos el marshaller. El contexto ya está creado
			Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			
			marshaller.marshal(jaxbElement, System.out);
			marshaller.marshal(jaxbElement, new FileOutputStream(rutaNuevoXMLCentrosProfes));
			
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
	}
}
