package EjemplosXML_DB;

//LA BD EXIST ESCUCHA EN PUERTO 8085
//CONSULTAR LA URI
//USANDO LA COLECCION: ColeccionPruebas, BDProductosXML



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

public class Ejemplo2Colecciones {

	private static final String driver = "org.exist.xmldb.DatabaseImpl";
	private static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db";
	private static String usu = "admin";
	private static String usuPwd = "admin";

	public static void main(String[] args) {

		//PROBAR LOS METODOS DE UNO EN UNO PARA VER LOS CAMBIOS SE QUE VAN REALIZANDO
		
				try {
			descargarDocumento();
		} catch (TransformerException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			crearcoleccysubirarchivo("NUEVA_COLECCION");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			borrarfichero("NUEVA_COLECCION", "NUEVAS_ZONASx.xml");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		vercolecciones();

		ejecutarconsultafichero("miconsulta.xq");
		
		actualizaproductos();	

		// verrecursosdelascolecciones();
		
		try {
			borrarcoleccion("NUEVA_COLECCION");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		}
 
	}

	public static void vercolecciones() {

		System.out.println("\n============================================================");
		System.out.println("METODO vercolecciones()");

		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
			Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
			System.out.println("Número de colecciones: " + col.getChildCollectionCount());
			String[] colecciones = col.listChildCollections();
			for (int j = 0; j < colecciones.length; j++) {
				System.out.println(colecciones[j]);
				// Collection colecc = col.getChildCollection(colecciones[j]);
			}
		} catch (ClassNotFoundException ex) {
			System.out.println(" ERROR EN EL DRIVER. COMPRUEBA CONECTORES.");
		} catch (IllegalAccessException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void verrecursosdelascolecciones() {
		System.out.println("\n============================================================");
		System.out.println("METODO verrecursosdelascolecciones()");

		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);

			Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
			System.out.println("Número de colecciones: " + col.getChildCollectionCount());
			String[] colecciones = col.listChildCollections();
			for (int j = 0; j < colecciones.length; j++) {
				System.out.println("-------------------------- ");
				System.out.println(colecciones[j]);
				Collection colecc = col.getChildCollection(colecciones[j]);
				String[] lista = colecc.listResources();
				for (int i = 0; i < lista.length; i++) {
					Resource res = (Resource) colecc.getResource(lista[i]);
					System.out.println("ID del documento: " + res.getId());
					System.out.println("Contenido del documento:\n " + res.getContent());
				}
			}
		} catch (ClassNotFoundException ex) {
			System.out.println(" ERROR EN EL DRIVER. COMPRUEBA CONECTORES.");
		} catch (IllegalAccessException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void prueba1() {

		try {
			Class<?> cl = Class.forName(driver); // Cargar del driver
			Database database = (Database) cl.getDeclaredConstructor().newInstance(); // Instancia de la BD
			DatabaseManager.registerDatabase(database); // Registro del driver
			Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);

			XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet result = servicio.query("for $p in /productos/produc return $p");
			ResourceIterator i; // se utiliza para recorrer un set de recursos
			i = result.getIterator();
			if (!i.hasMoreResources()) {
				System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
			}
			while (i.hasMoreResources()) {
				Resource r = i.nextResource();
				System.out.println("Elemento: " + (String) r.getContent());
			}

		} catch (ClassNotFoundException ex) {
			System.out.println(" ERROR EN EL DRIVER. COMPRUEBA CONECTORES.");
		} catch (InstantiationException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (IllegalAccessException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void actualizaproductos() {

		System.out.println("\n============================================================");
		System.out.println("METODO actualizaproductos()");

		try {
			Class<?> cl = Class.forName(driver); // Cargar del driver
			Database database = (Database) cl.getDeclaredConstructor().newInstance(); // Instancia de la BD
			DatabaseManager.registerDatabase(database); // Registro del driver
			Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);

			XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
		
			String consulta = " for $p in collection('/db/BDProductosXML')/productos/produc "
					+ " let $st := $p/stock_actual "
					+ " return update value $p/stock_actual " 
					+ " with $st+10 ";
			ResourceSet result = servicio.query(consulta);
			ResourceIterator i; // se utiliza para recorrer un set de recursos
			i = result.getIterator();
	
			if (!i.hasMoreResources()) {
				System.out.println(" LA CONSULTA NO DEVUELVE NADA. PERO SI ACTUALIZA STOCK...");
			}
			while (i.hasMoreResources()) {
				Resource r = i.nextResource();
				System.out.println("Elemento: " + (String) r.getContent());
			}

		} catch (ClassNotFoundException ex) {
			System.out.println(" ERROR EN EL DRIVER. COMPRUEBA CONECTORES.");
		} catch (InstantiationException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (IllegalAccessException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void descargarDocumento() throws TransformerConfigurationException, TransformerException {

		System.out.println("============================================================");
		System.out.println("METODO descargarDocumento()");

		// Localizar un documento, extraerlo y guardarlo en disco

		try {
			Class<?> cl = Class.forName(driver); // Cargar del driver
			Database database = (Database) cl.getDeclaredConstructor().newInstance(); // Instancia de la BD
			DatabaseManager.registerDatabase(database); // Registro del driver

			String colec = URI + "/ColeccionPruebas";

			Collection col = DatabaseManager.getCollection(colec, usu, usuPwd);
			XMLResource res = (XMLResource) col.getResource("zonas.xml");
			if (res == null) {
				System.out.println("NO EXISTE EL DOCUMENTO");
			} else {
				System.out.println("ID del documento: " + res.getDocumentId());

				// Volcado del documento a un arbol DOM
				Node document = res.getContentAsDOM();
				Source source = new DOMSource(document);
				Transformer transformer = TransformerFactory.newInstance().newTransformer();

				// Volcado del documento de memoria a consola
				Result console = new StreamResult(System.out);
				transformer.transform(source, console);

				// Volcado del documento a un fichero
				Result fichero = new StreamResult(new java.io.File("./zonas.xml"));
				transformer = TransformerFactory.newInstance().newTransformer();
				transformer.transform(source, fichero);
			}
		} catch (ClassNotFoundException ex) {
			System.out.println(" ERROR EN EL DRIVER. COMPRUEBA CONECTORES.");
		} catch (InstantiationException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (IllegalAccessException ex) {
			System.out.println("Error al crear Instancia de la BD (Database) cl.newInstance()");
		} catch (XMLDBException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void crearcoleccysubirarchivo(String colecc) throws XMLDBException {

		System.out.println("\n============================================================");
		System.out.println("METODO crearcoleccysubirarchivo()");

		Collection col = null;
		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);

			col = DatabaseManager.getCollection(URI, usu, usuPwd);
			if (col != null) {
				// CREAR COLECCION dentro de col,
				CollectionManagementService mgtService = (CollectionManagementService) col
						.getService("CollectionManagementService", "1.0");
				mgtService.createCollection(colecc);
				System.out.println(" *** COLECCION CREADA: " + colecc);
			}
			// Nos posicionamos en la nueva coleccion y añadimos el archivo
			// Si es un ficheo binario ponemos BinaryResource
			String colec = URI + "/" + colecc;
			col = DatabaseManager.getCollection(colec, usu, usuPwd);

			File archivo = new File("NUEVAS_ZONAS.xml");
			if (!archivo.canRead()) {
				System.out.println("ERROR AL LEER EL FICHERO");
			} else {
				Resource nuevoRecurso = col.createResource(archivo.getName(), "XMLResource");
				nuevoRecurso.setContent(archivo);
				col.storeResource(nuevoRecurso);
				System.out.println("FICHERO AÑADIDO: "+ archivo.getName());
			}
		} catch (Exception e) {
			System.out.println("Error al inicializar la BD eXist");
		}
	}

	public static void borrarcoleccion(String colecc) throws XMLDBException {

		System.out.println("============================================================");
		System.out.println("METODO borrarcoleccion()");

		Collection col = null;
		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
			col = DatabaseManager.getCollection(URI, usu, usuPwd);
			CollectionManagementService mgtService = (CollectionManagementService) col
					.getService("CollectionManagementService", "1.0");
			
			mgtService.removeCollection(colecc);
			System.out.println(" *** COLECCION: " + colecc+ ", BORRADA. ***");
		} catch (Exception e) {
			System.out.println("Error al inicializar la BD eXist");
		}
	}

	public static void borrarfichero(String colecc, String fichero) throws XMLDBException {
		
		System.out.println("============================================================");
		System.out.println("METODO borrarfichero()");

		Collection col = null;

		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
			
			String colec = URI + "/" + colecc;
			col = DatabaseManager.getCollection(colec, usu, usuPwd);
			
			// borrar un xml de la coleccion			
			Resource recursoParaBorrar = col.getResource(fichero);
			col.removeResource(recursoParaBorrar);			
			System.out.println("FICHERO BORRADO: " + fichero + " en la coleccion: "+colecc);
			
		} catch (NullPointerException e) {
			System.out.println("No se puede borrar: " + fichero + ", No se encuentra en la coleccion: "+colecc);
			
		} catch (ClassNotFoundException ex) {
			System.out.println("ERROR DRIVER.");
		} catch (InstantiationException ex) {
			System.out.println("ERROR AL CREAR LA INSTANCIA.");
		} catch (IllegalAccessException ex) {
			System.out.println("ERROR AL CREAR LA INSTANCIA.");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void ejecutarconsultafichero(String fichero) {

		System.out.println("============================================================");
		System.out.println("METODO ejecutarconsultafichero()");

		try {
			Class<?> cl = Class.forName(driver); // Cargar del driver
			Database database = (Database) cl.getDeclaredConstructor().newInstance(); // Instancia de la BD
			DatabaseManager.registerDatabase(database); // Registro del driver
			Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
			System.out.println("Convirtiendo el fichero a cadena...");
			BufferedReader entrada = new BufferedReader(new FileReader(fichero));
			String linea = null;
			StringBuilder stringBuilder = new StringBuilder();
			String salto = System.getProperty("line.separator"); // es el salto de l�nea \n

			while ((linea = entrada.readLine()) != null) {
				stringBuilder.append(linea);
				stringBuilder.append(salto);
			}
			String consulta = stringBuilder.toString();
			// Ejecutar consulta
			System.out.println("Consulta: " + consulta);
			XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet result = servicio.query(consulta);
			ResourceIterator i; // se utiliza para recorrer un set de recursos
			i = result.getIterator();
			if (!i.hasMoreResources()) {
				System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
			}
			while (i.hasMoreResources()) {
				Resource r = i.nextResource();
				System.out.println("Elemento: " + (String) r.getContent());
			}
			
			entrada.close();

		} catch (ClassNotFoundException ex) {
			System.out.println("ERROR EN EL DRIVER.");
		} catch (InstantiationException ex) {
			System.out.println("ERROR AL CREAR LA INSTANCIA.");
		} catch (IllegalAccessException ex) {
			System.out.println("ERROR AL CREAR LA INSTANCIA.");
		} catch (XMLDBException ex) {
			System.out.println("ERROR AL OPERAR CON EXIST.");
		} catch (FileNotFoundException ex) {
			System.out.println("El fichero no se localiza: " + fichero);
		} catch (IOException ex) {
			Logger.getLogger(Ejemplo2Colecciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
