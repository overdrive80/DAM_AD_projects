package eje4_fichero_xml;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/*
 * OPERACIONES CON UN FICHERO XML.
 */
@SuppressWarnings("unused")
public class DAM_AD_01_R_040 {
	static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		int op = 0, n = 0;
		do {
			dibujamenu();
			System.out.println("TECLEA OPERACIÓN: ");

			op = teclado.nextInt();
			switch (op) {
			case 1:
				creararticxml();
				break;
			case 2:
				visualizar();
				break;
			case 3:
				System.out.println("Teclea el número de artículo a consultar: ");
				n = teclado.nextInt();
				consultar(n);
				break;
			case 4: // insertar nodo
				System.out.println("Teclea número de artículos a insertar: ");
				n = teclado.nextInt();
				insertarregistros(n);
				break;
			case 5: // modificar un nodo artículo
				while (true) {
					System.out.println("Teclea el código de artículo a actualizar: ");
					n = teclado.nextInt();
					teclado.nextLine();
					if (comprobarsiexiste(n) != null) {
						actualizararticulo(n);
						break;
					} else
						System.out.println("El artículo tecleado no existe. \n" + "Teclea de nuevo: ");
				}
				break;
			case 6:
				visualizartotales();
				break;
			case 7: // Borrar un artículo
				while (true) {
					System.out.println("Teclea el código de artículo a BORRAR: ");
					n = teclado.nextInt();
					if (comprobarsiexiste(n) != null) {
						borrararticulo(n);
						break;
					} else
						System.out.println("El artículo tecleado no existe. \n" + "Teclea de nuevo: ");
				}

				break;
			} // switch
		} while (op != 0);

	} // fin main
////////////////////////////////////////////////////////////////////////////////////

	private static void dibujamenu() {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(
				"\t...............................................\n" + "\t.  1 Crear fichero XML de artículos. \n"
						+ "\t.  2 Visualizar los datos.  \n" + "\t.  3 Consultar un artículo.\n"
						+ "\t.  4 Insertar n nodos artículo.\n" + "\t.  5 Modificar los datos de un artículo.\n"
						+ "\t.  6 Visualizar totales unidades e importe.\n" + "\t.  7 Borrar un nodo artículo.\n"
						+ "\t.  0 SALIR.\n" + "\t...............................................");
		System.out.println("-------------------------------------------------------------------------");

	} // fin dibujamenu
////////////////////////////////////////////////////////////////////////////////////

	private static void borrararticulo(int codigoarti) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Articulos.xml"));
			document.getDocumentElement().normalize();
			// Nuevo documento sin el nodo a borrar
			DOMImplementation implementation = builder.getDOMImplementation();
			Document docsalida = implementation.createDocument(null, "Articulos", null);
			docsalida.setXmlVersion("1.0");
			// crea una lista con todos los nodos articulo
			NodeList artic = document.getElementsByTagName("articulo");
			// Recorro los nodos cuando localice el que hay que modificar lo modifico en la
			// lista
			for (int i = 0; i < artic.getLength(); i++) {
				Node articulo = artic.item(i);
				String codi = ((Element) articulo).getElementsByTagName("codigo").item(0).getTextContent();
				String nombreS = ((Element) articulo).getElementsByTagName("denominacion").item(0).getTextContent();
				String precioS = ((Element) articulo).getElementsByTagName("precio").item(0).getTextContent();
				String unidadesS = ((Element) articulo).getElementsByTagName("unidades").item(0).getTextContent();
				String zonaS = ((Element) articulo).getElementsByTagName("zona").item(0).getTextContent();

				if (Integer.parseInt(codi) == (codigoarti)) { // Registro a borrar
					System.out.println("-----------------------------------------------------------------");
					System.out.println("------------ARTÍCULO A BORRAR --------------------------------");
					System.out.println("Código: " + codi);
					System.out.println("Denominación: " + nombreS);
					System.out.println("Unidades: " + unidadesS);
					System.out.println("Precio: " + precioS);
					System.out.println("Zona: " + zonaS);
					System.out.println("-----------------------------------------------------------------");
				} else { // se añade al nuevo documento
					Element articuloMOD = docsalida.createElement("articulo");
					docsalida.getDocumentElement().appendChild(articuloMOD);
					insertanododocument(Integer.parseInt(codi), Integer.parseInt(unidadesS), nombreS, zonaS,
							Float.parseFloat(precioS), docsalida, articuloMOD);
				}
			} // fin for
			System.out.println("Actualizo el fichero Articulos.xml");
			Source source = new DOMSource(docsalida);
			Result result = new StreamResult(new java.io.File("Articulos.xml"));
			Transformer transformer;
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			System.out.println("REGISTRO BORRADO.");
		} catch (Exception e) {
			System.out.println("------ HA OCURRIDO UN ERROR AL CREAR EL DocumentBuilder.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	} // fin borrararticulo
////////////////////////////////////////////////////////////////////////////////////

	private static void visualizartotales() {
		int unidades = 0;
		String articcaro = "", articvend = "";
		float precio = 0;
		int totaluni = 0, masuni = 0;
		float totalimport = 0, importe = 0, mascaro = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Articulos.xml"));
			document.getDocumentElement().normalize();
			// crea una lista con todos los nodos articulo
			NodeList artic = document.getElementsByTagName("articulo");
			// recorrer cada elemento articulo
			System.out.println("--------- --------------------------- --------- --------- --------- --------------");
			System.out.println("COD ARTI  NOMBRE ART                   UNIDADES  PRECIO    IMPORTE     ZONA");
			System.out.println("--------- --------------------------- --------- --------- --------- --------------");
			for (int i = 0; i < artic.getLength(); i++) {
				Node articulo = artic.item(i); // obtener un nodo artículo y sus etiquetas
				String codigo = ((Element) articulo).getElementsByTagName("codigo").item(0).getTextContent();
				String nombre = ((Element) articulo).getElementsByTagName("denominacion").item(0).getTextContent();
				String datoss1 = ((Element) articulo).getElementsByTagName("precio").item(0).getTextContent();
				precio = Float.parseFloat(datoss1);
				String datoss2 = ((Element) articulo).getElementsByTagName("unidades").item(0).getTextContent();
				unidades = Integer.parseInt(datoss2);
				String zona = ((Element) articulo).getElementsByTagName("zona").item(0).getTextContent();
				importe = unidades * precio;
				System.out.printf("%-10s %-25s %8s %9s %9.2f %15s \n", codigo, nombre, datoss2, datoss1, importe, zona);

				totaluni = totaluni + unidades;
				totalimport = totalimport + importe;
				if (precio > mascaro) {
					articcaro = nombre;
					mascaro = precio;
				}
				if (unidades > masuni) {
					articvend = nombre;
					masuni = unidades;
				}
			} // fin for
			System.out.println("--------- --------------------------- --------- --------- --------- --------------");
			System.out.printf("%-10s %-25s %8s %9s %9.2f %15s \n", "TOTALES: ", " ", totaluni, "  ", totalimport, " ");
			System.out.println("Artículo más caro : " + articcaro);
			System.out.println("Artículo más vendido : " + articvend);
			System.out.println("--------- --------------------------- --------- --------- --------- --------------");
		} catch (Exception e) {
			System.out.println("------ HA OCURRIDO UN ERROR AL CREAR EL DocumentBuilder.------");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}// fin visualizartotales
////////////////////////////////////////////////////////////////////////////////////

	private static Node comprobarsiexiste(int codi) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Node articulo = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Articulos.xml"));
			document.getDocumentElement().normalize();
			NodeList artic = document.getElementsByTagName("articulo");
			// recorrer cada elemento articulo extraer el código y preguntar
			for (int i = 0; i < artic.getLength(); i++) {
				Node arti = artic.item(i); // obtener un nodo articulo y sus etiquetas
				String codigo = ((Element) arti).getElementsByTagName("codigo").item(0).getTextContent();
				if (Integer.parseInt(codigo) == codi) {
					articulo = arti;
					break;
				}
			} // fin for

		} catch (Exception e) {
			System.out.println("-----------------------------------------------------------------");
			System.out.println("------ HA OCURRIDO UN ERROR EN -comprobarsiexiste- ------");
			e.printStackTrace();
		}
		return articulo;
	} // fin comprobarsiexiste
////////////////////////////////////////////////////////////////////////////////////

	private static void actualizararticulo(int codigoarti) {
		int unidades = 0;
		String nombresartic = "", zona = "";
		float precio = 0;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Articulos.xml"));
			document.getDocumentElement().normalize();
			// Nuevo documento con las modificaciones
			DOMImplementation implementation = builder.getDOMImplementation();
			Document docsalida = implementation.createDocument(null, "Articulos", null);
			docsalida.setXmlVersion("1.0");
			// crea una lista con todos los nodos articulo
			NodeList artic = document.getElementsByTagName("articulo");
			// Recorro los nodos cuando localice el que hay que modificar lo modifico en la
			// lista
			for (int i = 0; i < artic.getLength(); i++) {
				Node articulo = artic.item(i);
				String codi = ((Element) articulo).getElementsByTagName("codigo").item(0).getTextContent();
				String nombreS = ((Element) articulo).getElementsByTagName("denominacion").item(0).getTextContent();
				String precioS = ((Element) articulo).getElementsByTagName("precio").item(0).getTextContent();
				String unidadesS = ((Element) articulo).getElementsByTagName("unidades").item(0).getTextContent();
				String zonaS = ((Element) articulo).getElementsByTagName("zona").item(0).getTextContent();

				if (Integer.parseInt(codi) == (codigoarti)) { // Se piden los datos a modificar
					System.out.println("-----------------------------------------------------------------");
					System.out.println("------------ARTÍCULO A MODIFICAR --------------------------------");
					System.out.println("Código: " + codi);
					System.out.println("Denominación: " + nombreS);
					System.out.println("Unidades: " + unidadesS);
					System.out.println("Precio: " + precioS);
					System.out.println("Zona: " + zonaS);
					System.out.println("-----------------------------------------------------------------");
					System.out.println("Teclea el nuevo nombre de artículo " + codi);
					nombresartic = teclado.nextLine();
					// leemos el precio
					do {
						System.out.println("Teclea el nuevo precio de artículo: ");
						try {
							precio = teclado.nextInt();
							teclado.nextLine();
							break;
						} catch (InputMismatchException e) {
							System.out.println(">>>El PRECIO DEL ARTÍCULO DEBE SER NUMÉRICO. ");
							teclado.nextLine();
						}
					} while (true);
					// Leemos las unidades y validamos
					do {
						System.out.println("Teclea las nuevas unidades de artículo: ");
						try {
							unidades = teclado.nextInt();
							teclado.nextLine();
							break;
						} catch (InputMismatchException e) {
							System.out.println(">>>LAS UNIDADES DEBEN SER NUMÉRICÁS");
							teclado.nextLine();
						}
					} while (true);
					// Leemos zona
					System.out.println("Teclea el nuevo nombre de la zona: ");
					zona = teclado.nextLine();
					System.out.println("LOS DATOS A ACTUALIZAR SON: " + "COD: " + codigoarti + ", NOMBRE: "
							+ nombresartic + ", UNI: " + unidades + ", PVP: " + precio + ", zona = " + zona);

					// Los añado al árbol del DOCSALIDA, modificado
					Element articuloMOD = docsalida.createElement("articulo");
					docsalida.getDocumentElement().appendChild(articuloMOD);
					insertanododocument(codigoarti, unidades, nombresartic, zona, precio, docsalida, articuloMOD);
				} else { // se añade sin modificar
					Element articuloMOD = docsalida.createElement("articulo");
					docsalida.getDocumentElement().appendChild(articuloMOD);
					insertanododocument(Integer.parseInt(codi), Integer.parseInt(unidadesS), nombreS, zonaS,
							Float.parseFloat(precioS), docsalida, articuloMOD);
				}
			} // fin for

			System.out.println("Actualizo el fichero Articulos.xml");
			Source source = new DOMSource(docsalida);
			Result result = new StreamResult(new java.io.File("Articulos.xml"));
			Transformer transformer;
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			System.out.println("REGISTRO AÑADIDO.");

		} catch (Exception e) {
			System.out.println("------ HA OCURRIDO UN ERROR AL CREAR EL DocumentBuilder.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}
	}// fin actualizararticulo
////////////////////////////////////////////////////////////////////////////////////

	private static void insertanododocument(int codigoarti, int unidades, String nombresartic, String zona,
			float precio, Document documento, Element articulo) { // añado la etiqueta codigo
		Element codigo = documento.createElement("codigo");
		Text text = documento.createTextNode(Integer.toString(codigoarti));
		articulo.appendChild(codigo);
		codigo.appendChild(text);

		// añado la etiqueta denominación
		Element denom = documento.createElement("denominacion");
		text = documento.createTextNode(nombresartic);
		articulo.appendChild(denom);
		denom.appendChild(text);

		// añado la etiqueta precio
		Element pvp = documento.createElement("precio");
		text = documento.createTextNode(Float.toString(precio));
		articulo.appendChild(pvp);
		pvp.appendChild(text);

		// añado la etiqueta unidades
		Element unid = documento.createElement("unidades");
		text = documento.createTextNode(Integer.toString(unidades));
		articulo.appendChild(unid);
		unid.appendChild(text);

		Element zonartic = documento.createElement("zona");
		text = documento.createTextNode(zona);
		articulo.appendChild(zonartic);
		zonartic.appendChild(text);
	} // insertar un nodo en un documento
////////////////////////////////////////////////////////////////////////////////////

	private static void insertarregistros(int n) {
		int codigoarti = 0, unidades = 0;
		String nombresartic = "", zona = "";
		float precio = 0;
		System.out.println("-----------------------------------------------------");
		System.out.println(" ENTRADA DE DATOS: teclea " + n + " registros.");
		System.out.println("-----------------------------------------------------");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Articulos.xml"));
			document.getDocumentElement().normalize();

			System.out.println("Elemento raíz: " + document.getDocumentElement().getNodeName());
			// crea una lista con todos los nodos articulo
			// NodeList artic = document.getElementsByTagName("articulo");
			// entrada de datos
			for (int i = 1; i <= n; i++) {
				System.out.println(">>>>>> Registro: " + i);
				do {
					System.out.println("Teclea el número de artículo: ");
					try {
						codigoarti = teclado.nextInt();
						teclado.nextLine();
						if (comprobarsiexiste(codigoarti) != null)
							System.out.println(
									">>>El CODIGO DE ARTÍCULO YA EXISTE: " + codigoarti + ", teclea de nuevo.");
						else // sigo leyendo
							break;
					} catch (InputMismatchException e) {
						System.out.println(">>>El CODIGO DE ARTÍCULO DEBE SER NUMÉRICO. ");
						teclado.nextLine();
					}
				} while (true);
				// Leemos nombre
				System.out.println("Teclea el nombre de artículo: ");
				nombresartic = teclado.nextLine();
				// leemos el precio
				do {
					System.out.println("Teclea el precio de artículo: ");
					try {
						precio = teclado.nextInt();
						teclado.nextLine();
						break;
					} catch (InputMismatchException e) {
						System.out.println(">>>El PRECIO DEL ARTÍCULO DEBE SER NUMÉRICO. ");
						teclado.nextLine();
					}

				} while (true);
				// Leemos las unidades y validamos
				do {
					System.out.println("Teclea las unidades de artículo: ");
					try {
						unidades = teclado.nextInt();
						teclado.nextLine();
						break;
					} catch (InputMismatchException e) {
						System.out.println(">>>LAS UNIDADES DEBEN SER NUMÉRICÁS");
						teclado.nextLine();
					}
				} while (true);
				// Leemos zona
				System.out.println("Teclea el nombre de la zona: ");
				zona = teclado.nextLine();

				System.out.println("LOS DATOS A GRABAR SON: " + "COD: " + codigoarti + ", NOMBRE: " + nombresartic
						+ ", UNI: " + unidades + ", PVP: " + precio + ", zona = " + zona);
				// Los añado al document, al arbol
				// se crea el nodo <articulo>
				Element articulo = document.createElement("articulo");
				document.getDocumentElement().appendChild(articulo);
				// Llevo al método insertar los datos y los añado al árbol
				insertanododocument(codigoarti, unidades, nombresartic, zona, precio, document, articulo);
				System.out.println("Actualizo el fichero Articulos.xml");
				Source source = new DOMSource(document);
				Result result = new StreamResult(new java.io.File("Articulos.xml"));
				Transformer transformer;
				transformer = TransformerFactory.newInstance().newTransformer();
				transformer.transform(source, result);

				System.out.println("REGISTRO AÑADIDO.");

			} // fin for

		} catch (Exception e) {
			System.out.println("------ HA OCURRIDO UN ERROR AL CREAR EL DocumentBuilder (-visualizar-) ------");
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	}// fin insertar n registros
////////////////////////////////////////////////////////////////////////////////////

	private static void consultar(int codi) {

		Node articulo = comprobarsiexiste(codi);
		if (articulo != null) {
			String codigo = ((Element) articulo).getElementsByTagName("codigo").item(0).getTextContent();

			String nombre = ((Element) articulo).getElementsByTagName("denominacion").item(0).getTextContent();
			String precio = ((Element) articulo).getElementsByTagName("precio").item(0).getTextContent();
			String unidades = ((Element) articulo).getElementsByTagName("unidades").item(0).getTextContent();
			String zona = ((Element) articulo).getElementsByTagName("zona").item(0).getTextContent();
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Código: " + codigo);
			System.out.println("Denominación: " + nombre);
			System.out.println("Unidades: " + unidades);
			System.out.println("Precio: " + precio);
			System.out.println("Zona: " + zona);
			System.out.println("-----------------------------------------------------------------");
		} else {
			System.out.println("-----------------------------------------");
			System.out.println("** ARTICULO : " + codi + " , NO EXISTE. ** ");
			System.out.println("-----------------------------------------");
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}// fin consultar
////////////////////////////////////////////////////////////////////////////////////

	private static void visualizar() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("Articulos.xml"));
			document.getDocumentElement().normalize();

			System.out.println("Elemento raíz: " + document.getDocumentElement().getNodeName());
			// crea una lista con todos los nodos articulo
			NodeList artic = document.getElementsByTagName("articulo");
			// recorrer cada elemento articulo
			System.out.println("COD ARTI  NOMBRE ART                   UNIDADES  PRECIO       ZONA");
			System.out.println("--------- --------------------------- --------- --------- ----------------");
			for (int i = 0; i < artic.getLength(); i++) {
				Node articulo = artic.item(i); // obtener un nodo articulo y sus etiquetas
				String codigo = ((Element) articulo).getElementsByTagName("codigo").item(0).getTextContent();
				String nombre = ((Element) articulo).getElementsByTagName("denominacion").item(0).getTextContent();
				String precio = ((Element) articulo).getElementsByTagName("precio").item(0).getTextContent();
				String unidades = ((Element) articulo).getElementsByTagName("unidades").item(0).getTextContent();
				String zona = ((Element) articulo).getElementsByTagName("zona").item(0).getTextContent();
				System.out.printf("%-10s %-25s %8s %9s %15s \n", codigo, nombre, precio, unidades, zona);

			}
			System.out.println("--------- --------------------------- --------- --------- ----------------");
		} catch (Exception e) {
			System.out.println("------ HA OCURRIDO UN ERROR AL CREAR EL DocumentBuilder (-visualizar-) ------");
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}// fin visualizar
////////////////////////////////////////////////////////////////////////////////////


	private static void creararticxml() {
		// Datos a insertar en el documeto xml, estructura nodo articulo
		/*
		 * <articulo> <codigo> </codigo> <denominacion> </denominacion> <precio>
		 * </precio> <unidades> </unidades> <zona></zona> </articulo>
		 */
		int codigoartic[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String denominacion[] = { "Pala Padel", "Portátil Acer", "Calendario Gregoriano", "Tablet SamSung",
				"Portatil MAC", "Bolsa Padel", "Bolsa Portatil", "Lapiceros", "Ratón Optico", "Equipo Música" };
		float precio[] = { 100, 500, 10, 300, 1000, 15, 20, 10, 15, 300 };
		int unidades[] = { 4, 2, 4, 5, 7, 10, 10, 10, 5, 4 };
		String zona[] = { "Madrid", "Toledo", "Madrid", "Ávila", "Cáceres", "Madrid", "Cáceres", "Toledo", "Madrid",
				"Cáceres" };

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "Articulos", null);
			document.setXmlVersion("1.0");
			// Bucle para crear los nodos
			for (int i = 0; i < codigoartic.length; i++) {
				System.out.println("------- Creo nodo con el artículo:" + codigoartic[i]);

				// se crea el nodo <articulo>
				Element articulo = document.createElement("articulo");
				document.getDocumentElement().appendChild(articulo);
				insertanododocument(codigoartic[i], unidades[i], denominacion[i], zona[i], precio[i], document,
						articulo);
				System.out.println("------ Nodo Creado ------");
			}
			System.out.println("Creo el fichero Articulos.xml");
			Source source = new DOMSource(document);
			Result result = new StreamResult(new java.io.File("Articulos.xml"));
			Transformer transformer;
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			// Volcado del documento de memoria a consola
			Result console = new StreamResult(System.out);
			transformer.transform(source, console);
		} catch (ParserConfigurationException e1) {
			System.out.println("------ ERROR AL CREAR EL DocumentBuilder en creararticxml  ------");
			e1.printStackTrace();

		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			System.out.println("------ HA OCURRIDO UN ERROR AL CREAR EL FICHERO XML  ------");
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	} // fin crear
////////////////////////////////////////////////////////////////////////////////////
}// fin clase

