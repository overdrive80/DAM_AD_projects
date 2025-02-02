package parte_6;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DOM_LecturaEmpleadoXml2 {
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

					Element elemento = (Element) emple; // obtener los elementos el nodo
					System.out.println("ID: " + getNodo("id", elemento));
					System.out.println("Apellido: " + getNodo("apellido", elemento));
					System.out.println("Departamento: " + getNodo("dep", elemento));
					System.out.println("Salario: " + getNodo("salario", elemento));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// fin de main

	// Obtener la información de un nodo
//	private static String getNodo(String etiqueta, Element elem) {
//
//		NodeList nodo = elem.getElementsByTagName(etiqueta).item(0).getChildNodes();
//		Node valornodo = (Node) nodo.item(0);
//		return valornodo.getNodeValue();// devuelve el valor del nodo
//	}
	
	private static String getNodo(String etiqueta, Element elem) {
	    Node nodo = elem.getElementsByTagName(etiqueta).item(0);
	    return nodo.getTextContent(); // Devuelve el contenido de texto directamente
	}


}// fin de la clase
