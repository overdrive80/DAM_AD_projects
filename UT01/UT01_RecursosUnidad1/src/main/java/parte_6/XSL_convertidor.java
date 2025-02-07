package parte_6;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class XSL_convertidor {
	public static void main(String argv[]) throws IOException {
		String hojaEstilo = "alumnosPlantilla.xsl";
		String datosAlumnos = "alumnos.xml";
		File pagHTML = new File("mipagina.html");
		FileOutputStream os = new FileOutputStream(pagHTML); // crear fichero HTML

		Source estilos = new StreamSource(hojaEstilo); // fuente XSL
		Source datos = new StreamSource(datosAlumnos); // fuente XML
		Result result = new StreamResult(os); // resultado de la transformación

		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer(estilos);
			transformer.transform(datos, result); // obtiene el HTML
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}

		os.close(); 
	}
}
