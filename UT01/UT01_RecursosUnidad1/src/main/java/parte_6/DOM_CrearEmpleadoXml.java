package parte_6;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class DOM_CrearEmpleadoXml {
	public static void main(String args[]) throws IOException {
		File fichero = new File("AleatorioEmple.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");

		int id, dep, posicion = 0; // para situarnos al principio del fichero
		Double salario;
		char apellido[] = new char[10], aux;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "Empleados", null);
			document.setXmlVersion("1.0");

			for (;;) {
				file.seek(posicion); // nos posicionamos
				id = file.readInt(); // obtengo id de empleado
				for (int i = 0; i < apellido.length; i++) {
					aux = file.readChar();
					apellido[i] = aux;
				}
				String apellidos = new String(apellido);
				dep = file.readInt();
				salario = file.readDouble();

				if (id > 0) { // id validos a partir de 1
					Element elemento = document.createElement("empleado"); // nodo empleado
					Element root = document.getDocumentElement();
					root.appendChild(elemento);

					// añadir ID
					CrearElemento("id", Integer.toString(id), elemento, document);
					// Apellido
					CrearElemento("apellido", apellidos.trim(), elemento, document);
					// añadir DEP
					CrearElemento("dep", Integer.toString(dep), elemento, document);
					// añadir salario
					CrearElemento("salario", Double.toString(salario), elemento, document);
				}
				posicion = posicion + 36; // me posiciono para el sig empleado
				if (file.getFilePointer() == file.length())
					break;

			} // fin del for que recorre el fichero

			Source source = new DOMSource(document);
			Result result = new StreamResult(new java.io.File("Empleados.xml"));
			Result consola = new StreamResult(System.out);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Habilitar la indentación
			transformer.transform(source, result);
			transformer.transform(source, consola);

		} catch (Exception e) {
			System.err.println("Error: " + e);
		}

		file.close(); // cerrar fichero
	}// fin de main

	// Inserción de los datos del empleado
	static void CrearElemento(String datoEmple, String valor, Element raiz, Document document) {
		Element elem = document.createElement(datoEmple);
		Text text = document.createTextNode(valor); // damos valor
		raiz.appendChild(elem); // pegamos el elemento hijo a la raiz
		elem.appendChild(text); // pegamos el valor
	}
}// fin de la clase
