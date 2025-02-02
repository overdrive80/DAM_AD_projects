package parte_6;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DOM_LecturaEmpleadoXml {
	public static void main(String[] args) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Empleados.xml"));
			Element root = document.getDocumentElement();
			root.normalize();

			System.out.printf("Elemento raiz: %s %n", root.getNodeName());
			// crea una lista con todos los nodos empleado
			NodeList empleados = document.getElementsByTagName("empleado");
			System.out.printf("Nodos empleado a recorrer: %d %n", empleados.getLength());

			// recorrer la lista
			for (int i = 0; i < empleados.getLength(); i++) {
				Node emple = empleados.item(i); // obtener un nodo empleado
				if (emple.getNodeType() == Node.ELEMENT_NODE) {// tipo de nodo
					// obtener los elementos del nodo
					Element elementoPadre = (Element) emple;

					Node elementoHijo = elementoPadre.getElementsByTagName("id").item(0);
					System.out.printf("ID = %s %n", elementoHijo.getTextContent());

					elementoHijo = elementoPadre.getElementsByTagName("apellido").item(0);
					System.out.printf(" * Apellido = %s %n", elementoHijo.getTextContent());

					elementoHijo = elementoPadre.getElementsByTagName("dep").item(0);
					System.out.printf(" * Departamento = %s %n", elementoHijo.getTextContent());

					elementoHijo = elementoPadre.getElementsByTagName("salario").item(0);
					System.out.printf(" * Salario = %s %n", elementoHijo.getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// fin de main
}// fin de la clase
