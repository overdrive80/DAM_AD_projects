package test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

public class ExistManager {
	private static final String nodo = "\u251c\u2500";
	private static final String ultimoNodo = "\u2514\u2500";
	private static final String rama = "\u2502";
	private static Collection coleccion = null;

	private static String baseuri;
	private static String uri;
	private static String user;
	private static String pass;

	public static void conectar(String URI) {
		try {
			uri = URI;

			// Establecemos la ruta del driver para eXist
			String driver = "org.exist.xmldb.DatabaseImpl";

			// Cargamos la clase del driver
			Class<?> cl = Class.forName(driver);

			// Creamos una nueva instancia de la base de datos utilizando reflexión
			Database database = (Database) cl.getDeclaredConstructor().newInstance();

			// Indicamos que cree la base de datos si no existe
			database.setProperty("create-database", "true");

			// Registramos la base de datos
			DatabaseManager.registerDatabase(database);

			// Obtenemos la coleccion
			coleccion = DatabaseManager.getCollection(uri, user, pass);

			if (coleccion == null) {
				System.out.println("No se pudo conectar a la base de datos. Revise los datos de conexión:");
				System.out.println(" ".repeat(3) + "- URI: " + uri);
				System.out.println(" ".repeat(3) + "- Usuario: " + user);
				System.out.println(" ".repeat(3) + "- Contraseña: " + pass);
				return;
			}

		} catch (ClassNotFoundException ex) {
			System.out.println("ERROR: No se encontró el controlador de la base de datos. Verifique los conectores.");
		} catch (InstantiationException ex) {
			System.out.println("ERROR: No se pudo crear una instancia de la base de datos.");
		} catch (IllegalAccessException ex) {
			System.out.println("ERROR: Acceso ilegal al crear una instancia de la base de datos.");
		} catch (XMLDBException ex) {
			System.out.println("ERROR: Error de base de datos XMLDB.");
		} catch (IllegalArgumentException e) {
			System.out.println("ERROR: Argumento ilegal.");
		} catch (InvocationTargetException e) {
			System.out.println("ERROR: Excepción invocada al intentar crear una instancia de la base de datos.");
		} catch (NoSuchMethodException e) {
			System.out.println("ERROR: No se encontró el método especificado.");
		} catch (SecurityException e) {
			System.out.println("ERROR: Excepción de seguridad.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long getContadorColecciones() {
		long contador = 0;

		if (coleccion != null) {
			try {
				contador = coleccion.getChildCollectionCount();

			} catch (XMLDBException e) {
				e.printStackTrace();
			}
		}
		return contador;
	}

	public static void mostrarColecciones() {

		if (coleccion != null) {
			String[] colecciones;
			try {

				colecciones = coleccion.listChildCollections();

				if (colecciones.length == 0) {
					System.out.println("No hay colecciones en: " + coleccion.getName());
					return;
				}

				for (int j = 0; j < colecciones.length; j++) {
					System.out.println(colecciones[j]);
				}

			} catch (XMLDBException e) {
				e.printStackTrace();
			}
		}

	}

	public static void mostrarContenido() {
		if (coleccion != null) {
			try {
				System.out.println(coleccion.getName());
				mostrarTodo(coleccion, "");
			} catch (XMLDBException e) {
				e.printStackTrace();
			}
		}
	}

	private static void mostrarTodo(Collection coleccion, String prefijo) throws XMLDBException {
		boolean soloRecursos = false;

		// Mostrar recursos de la colección actual
		String[] recursos = coleccion.listResources();

		// Mostrar colecciones hijas de la colección actual
		String[] colecciones = coleccion.listChildCollections();
		int numColecciones = colecciones.length;

		// Si solo hay recursos lo valoramos para pintar los nodos del árbol
		if (numColecciones == 0) {
			soloRecursos = true;
		}

		printRecursos(recursos, prefijo, soloRecursos);

		// Si hay al menos una colección hija
		if (numColecciones > 0) {

			// Recorremos todas las colecciones hijas excepto la última
			for (int i = 0; i < numColecciones - 1; i++) {
				// Obtenemos la colección hija actual
				Collection subCollection = coleccion.getChildCollection(colecciones[i]);
				// Imprimimos el nombre de la colección hija precedido por un nodo y
				// "Colección:"
				System.out.println(prefijo + nodo + " Colección: " + colecciones[i]);
				// Llamada recursiva para mostrar el contenido de la colección hija
				mostrarTodo(subCollection, prefijo + rama + " ".repeat(2));
			}

			// Obtenemos la última colección hija
			Collection subCollection = coleccion.getChildCollection(colecciones[numColecciones - 1]);
			// Imprimimos el nombre de la última colección hija precedido por un guion
			// vertical invertido y seguido de "Colección:"
			System.out.println(prefijo + ultimoNodo + " Colección: " + colecciones[numColecciones - 1]);
			// Llamada recursiva para mostrar el contenido de la última colección hija
			mostrarTodo(subCollection, prefijo + " ".repeat(3));
		}
	}

	private static void printRecursos(String[] recursos, String prefijo, boolean soloRecursos) {
		if (recursos != null && recursos.length > 0) {

			if (!soloRecursos) {
				// Imprimir todos los recursos
				for (int i = 0; i < recursos.length; i++) {
					System.out.println(prefijo + nodo + " Recurso: " + recursos[i]);
				}

			} else {
				// Recorremos todas los recursos excepto el último
				for (int i = 0; i < recursos.length - 1; i++) {
					System.out.println(prefijo + nodo + " Recurso: " + recursos[i]);
				}

				// Imprimir el último recurso con el símbolo ultimoNodo
				System.out.println(prefijo + ultimoNodo + " Recurso: " + recursos[recursos.length - 1]);
			}
		}
	}

	/**
	 * Este método asume la colección actual como la base de comprobación
	 * 
	 * @param recurso
	 * @return
	 */
	public static boolean existeRecurso(String nombreRecurso, boolean mostrar) {
		boolean existe = false;

		if (coleccion == null) {
			return false;
		}

		// Recuperamos la lista de recursos
		try {
			Resource recurso = coleccion.getResource(nombreRecurso);

			if (recurso != null) {
				// System.out.println(recurso.getResourceType());
				if (mostrar) {
					mostrarRecurso(recurso);
				}

				existe = true;
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return existe;

	}

	public static void mostrarRecurso(Resource recurso) throws XMLDBException {
		System.out.println(recurso.getContent());
	}

	public static boolean existeColeccion(String nombreColeccion) {
		boolean existe = false;

		if (coleccion == null) {
			return false;
		}

		try {
			Collection col = DatabaseManager.getCollection(uri + "/" + nombreColeccion);

			if (col != null) {
				existe = true;
				// System.out.println(col.getName());
			}

		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return existe;

	}

	public static void crearColeccion(String nombreColeccion) {

		if (coleccion == null) {
			return;
		}

		try {
			if (existeColeccion(nombreColeccion)) {
				Collection col = DatabaseManager.getCollection(uri + "/" + nombreColeccion);
				System.out.println("Ya existe la colección: " + col.getName());
				return;
			}

			CollectionManagementService mgtService = (CollectionManagementService) coleccion
					.getService("CollectionManagementService", "1.0");
			mgtService.createCollection(nombreColeccion);

			System.out.println("Coleccion creada: " + nombreColeccion);
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

	}

	public static void borrarColeccion(String nombreColeccion) {

		if (coleccion == null) {
			return;
		}

		try {
			if (!existeColeccion(nombreColeccion)) {
				Collection col = DatabaseManager.getCollection(uri + "/" + nombreColeccion);
				System.out.println("No existe la colección: " + col.getName());
				return;
			}

			CollectionManagementService mgtService = (CollectionManagementService) coleccion
					.getService("CollectionManagementService", "1.0");
			mgtService.removeCollection(nombreColeccion);

			System.out.println("Coleccion borrada: " + nombreColeccion);
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

	}

	public static void cargarCarpeta(String ruta) {

		cargarCarpeta(ruta, "");
	}

	public static void cargarCarpeta(String ruta, boolean raiz) {

		if (!raiz) {
			cargarCarpeta(ruta, "");

		} else {
			File file = new File(ruta);

			String nombreRaiz = file.getName();

			crearColeccion(nombreRaiz);
			cargarCarpeta(ruta, nombreRaiz);
		}
	}

	public static void cargarCarpeta(String ruta, String base) {
		// Ruta de la carpeta con las colecciones
		File file = new File(ruta);

		if (!file.exists()) {
			return;
		}

		File[] archivos = file.listFiles();

		for (File archivo : archivos) {
			String nombreColeccion = archivo.getName();
			String rutaRelativa;
			String URIActual = getUri();

			// Definimos la ruta relativa y la URI de conexion
			if (base.isEmpty()) {
				rutaRelativa = nombreColeccion;

			} else {
				rutaRelativa = base + "/" + nombreColeccion;
				URIActual = getUri() + "/" + base;
			}

			if (archivo.isDirectory()) {

				// System.out.println(rutaRelativa);
				crearColeccion(rutaRelativa);

				// Llamar recursivamente a cargarCarpeta con la nueva ruta relativa
				cargarCarpeta(archivo.getAbsolutePath(), rutaRelativa);
			} else {

				if (!archivo.canRead())
					System.out.println("Error al leer el archivo: " + archivo.getName());
				else {
					cargarRecurso(archivo, URIActual);
				}

			}
		}
	}

	public static void cargarRecurso(File recurso, String URI) {

		String nombreRecurso = recurso.getName();

		try {
			if (!recurso.canRead())
				System.out.println("ERROR AL LEER EL FICHERO");
			else {

				Collection coleccionActual = DatabaseManager.getCollection(URI, user, pass);

				if (existeRecurso(nombreRecurso, coleccionActual, false)) {
					System.out.println("Ya existe el recurso: " + nombreRecurso);
					return;
				}

				Resource nuevoRecurso = coleccionActual.createResource(nombreRecurso, "XMLResource");
				nuevoRecurso.setContent(recurso);
				coleccionActual.storeResource(nuevoRecurso);

				System.out.println("Recurso cargado: " + nombreRecurso);
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

	}

	public static boolean existeRecurso(String nombreRecurso, Collection coleccionActual, boolean mostrar) {
		boolean existe = false;

		if (coleccionActual == null) {
			return false;
		}

		// Recuperamos la lista de recursos
		try {
			Resource recurso = coleccionActual.getResource(nombreRecurso);

			if (recurso != null) {
				// System.out.println(recurso.getResourceType());
				if (mostrar) {
					mostrarRecurso(recurso);
				}

				existe = true;
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return existe;

	}

	public static Collection getColeccionActual() {

		if (coleccion != null) {
			return coleccion;
		}

		return null;
	}

	public static String getBaseUri() {
		return baseuri;
	}

	public static void setBaseUri(String baseuri) {
		ExistManager.baseuri = uri;
	}

	public static String getUri() {
		return uri;
	}

	public static void setUri(String uri) {
		ExistManager.uri = uri;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		ExistManager.user = user;
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		ExistManager.pass = pass;
	}

}
