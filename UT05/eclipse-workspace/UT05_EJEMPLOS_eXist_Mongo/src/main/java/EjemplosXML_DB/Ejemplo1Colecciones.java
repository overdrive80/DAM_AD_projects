package EjemplosXML_DB;
//LA BD EXIST ESCUCHA EN PUERTO 8085
//USANDO LA COLECCION: ColeccionPruebas, CONSULTAR LA URI

import java.lang.reflect.InvocationTargetException;

import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
@SuppressWarnings("unused")
public class Ejemplo1Colecciones {

	static String driver = "org.exist.xmldb.DatabaseImpl"; // Driver para eXist
	static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/ColeccionPruebas"; // URI colección
	static String usu = "admin"; // Usuario
	static String usuPwd = "admin"; // Clave
	static Collection col = null;

	public static void main(String[] args) {
		
		//PROBAR LOS METODOS DE UNO EN UNO PARA VER LOS CAMBIOS SE QUE VAN REALIZANDO
		
		veruniversidad();

		// añadir un nodo departamento a universidad
		String nuedep = "<departamento telefono='8899' tipo='A'> "
				+ " <codigo>DIBU1</codigo> <nombre>Dibujo Técnico</nombre> </departamento>";

		insertardepartamento(nuedep);

		// Añadir empleados a ese departamento
		//<empleado salario="2200"> <puesto>Profesor Asoc</puesto> <nombre>Pilar Rojas</nombre> </empleado>
		String nuevoemple = " <empleado salario='2200'> "
				+ "<puesto>Profesor Asoc</puesto>   <nombre>Pilar Rojas</nombre>  </empleado>";
		
		String dep = "DIBU1";
		insertarempledep(nuevoemple, dep);

		// Actualizar el teléfono del departamento DIBU1, el nuevo teléfono es 925343434
		String nuevotelef = "925111111";
		actualizatlfdepart(dep, nuevotelef);
		
		// Actualizar el salario del empleado Pilar Rojas del departamento DIBU1,
		// sumarle 100
		String nombreemple = "Pilar Rojas";
		int subida = 100;
		actualizasalarioemple(nombreemple, dep, subida);
		
		veruniversidad();

		//borrar Pilar Rojas de departamento DIBU1
		borraremple(nombreemple, dep);
		
		//borrar departamento DIBU1
		borrardep(dep);

		//PROBAR LOS METODOS COMENTANDOLOS PARA VER LOS CAMBIOS QUE VAN REALIZANDO
		
		
	}// FIN
////////////////////////////////////////////////////////////////////////////////

	private static void borraremple(String nombreemple, String dep) {

		System.out.println("===============================================");
		System.out.println("Metodo borraremple()");

		if (conectar() != null) {
			if (comprobaremple(nombreemple, dep)) {
				try {
					System.out.printf("Borro el empleado %s del departamento: %s\n", nombreemple, dep);
					XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
					ResourceSet result = servicio.query("update delete /universidad/departamento[codigo='" + dep
							+ "']/empleado[nombre='" + nombreemple + "']");

					System.out.println("Emp borrado.");
				} catch (Exception e) {
					System.out.println("Error al borrar emple.");
					e.printStackTrace();
				}
			} else
				System.out.printf("El departamento %s NO EXISTE \n", dep);
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

	}// borraremple
////////////////////////////////////////////////////////////////////////////////

	private static void actualizasalarioemple(String nombreemple, String dep, int subida) {

		System.out.println("===============================================");
		System.out.println("Metodo actualizasalarioemple()");

		if (conectar() != null) {
			if (comprobaremple(nombreemple, dep)) {
				try {
					System.out.printf("Actualizo salario del empleado %s del departamento %s \n", nombreemple, dep);
					XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

					// si solo hay un empleado esta es la orden.
					String orden = "update value /universidad/departamento[codigo='" + dep + "']/empleado[nombre='"
							+ nombreemple + "']/@salario with " + " data(/universidad/departamento[codigo='" + dep
							+ "']/empleado[nombre='" + nombreemple + "']/@salario) + " + subida;

					// ResourceSet result = servicio.query (orden);

					// Para evitar el error porque hay varios empleados
					orden = "for $em in /universidad/departamento[codigo='" + dep + "']/empleado[nombre='" + nombreemple
							+ "'] " + " let $sal := data($em/@salario) "
							+ " return update value $em/@salario with data($sal)+ " + subida;

					ResourceSet result = servicio.query(orden);

					System.out.println("Emple modificado.");
				} catch (Exception e) {
					System.out.println("Error al modificar. Comprueba que sólo hay un empleado en ese departamento.");
					e.printStackTrace();
				}

			} else
				System.out.printf("\nEmpleado %s, no existe en el departamento: %s\n", nombreemple, dep);
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

	}// actualizasalarioemple
////////////////////////////////////////////////////////////////////////////////

	private static void borrardep(String dep) {

		System.out.println("===============================================");
		System.out.println("Metodo borrardep()");

		if (conectar() != null) {
			if (comprobardep(dep)) {
				try {
					System.out.printf("Borro el departamento: %s\n", dep);
					XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
					ResourceSet result = servicio
							.query("update delete /universidad/departamento[codigo='" + dep + "']");

					System.out.println("Dep borrado.");
				} catch (Exception e) {
					System.out.println("Error al borrar.");
					e.printStackTrace();
				}
			} else
				System.out.printf("El departamento %s NO EXISTE \n", dep);
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

	}// borrardep
////////////////////////////////////////////////////////////////////////////////

	private static boolean comprobardep(String dep) {
		// Devuelve true si el dep existe
		if (conectar() != null) {
			try {
				XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				ResourceSet result = servicio.query("/universidad/departamento[codigo='" + dep + "']");
				ResourceIterator i;
				i = result.getIterator();

				if (!i.hasMoreResources())
					return false;
				else
					return true;
			} catch (Exception e) {
				System.out.println("Error al consultar.");
				e.printStackTrace();
			}
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

		return false;

	}// comprobardep
////////////////////////////////////////////////////////////////////////////////

	private static boolean comprobaremple(String emp) {
		// Devuelve true si el emple existe
		if (conectar() != null) {
			try {
				XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				ResourceSet result = servicio.query("/universidad/departamento/empleado[nombre='" + emp + "']");
				System.out.println("Nombre: " + col.getName());

				ResourceIterator i;
				i = result.getIterator();

				if (!i.hasMoreResources())
					return false;
				else
					return true;
			} catch (Exception e) {
				System.out.println("Error al consultar.");
				e.printStackTrace();
			}
		} else
			System.out.println("Error en la conexión. Comprueba datos.");
		return false;

	}// comprobaremple
////////////////////////////////////////////////////////////////////////////////

	private static boolean comprobaremple(String emp, String dep) {

//Devuelve true si el emple existe
		if (conectar() != null) {
			try {
				XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				ResourceSet result = servicio
						.query("/universidad/departamento[codigo='" + dep + "']/empleado[nombre='" + emp + "']");
				ResourceIterator i;
				i = result.getIterator();

				if (!i.hasMoreResources())
					return false;
				else
					return true;
			} catch (Exception e) {
				System.out.println("Error al consultar.");
				e.printStackTrace();
			}
		} else
			System.out.println("Error en la conexión. Comprueba datos.");
		return false;

	}// comprobaremple
////////////////////////////////////////////////////////////////////////////////

	private static void actualizatlfdepart(String dep, String nuevotelef) {
		System.out.println("===============================================");
		System.out.println("Metodo actualizatlfdepart()");

		if (conectar() != null) {
			if (comprobardep(dep)) {
				try {
					System.out.printf("Actualizo teléfono : %s, en departamento  %s \n", nuevotelef, dep);
					XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
					ResourceSet result = servicio.query("update value /universidad/departamento[codigo='" + dep
							+ "']/@telefono with '" + nuevotelef + "'");

					System.out.println("Dep modificado.");
				} catch (Exception e) {
					System.out.println("Error al modificar.");
					e.printStackTrace();
				}
			} else
				System.out.printf("El departamento %s NO EXISTE \n", dep);
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

	}// actualizatlfdepart
////////////////////////////////////////////////////////////////////////////////

	private static void insertarempledep(String nuevoemple, String dep) {

		System.out.println("===============================================");
		System.out.println("Metodo insertarempledep()");

		if (conectar() != null) {
			try {
				XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				System.out.printf("Inserto: %s , en departamento %s \n", nuevoemple, dep);
				ResourceSet result = servicio
						.query("update insert " + nuevoemple + " into /universidad/departamento[codigo='" + dep + "']");
				col.close(); // borramos
				System.out.println("Emple insertado.");
			} catch (Exception e) {
				System.out.println("Error al insertar empleado.");
				e.printStackTrace();
			}
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

	} // fin insertarempledep
////////////////////////////////////////////////////////////////////////////////

	public static Collection conectar() {

		try {
			Class<?> cl = Class.forName(driver); // Cargar del driver
			Database database = (Database) cl.getDeclaredConstructor().newInstance(); // Instancia de la BD
			DatabaseManager.registerDatabase(database); // Registro del driver
			col = DatabaseManager.getCollection(URI, usu, usuPwd);
			return col;
		} catch (XMLDBException e) {
			System.out.println("Error al inicializar la BD eXist.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error en el driver.");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("Error al instanciar la BD.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Error al instanciar la BD.");
			e.printStackTrace();
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
		return null;
	}

////////////////////////////////////////////////////////////////////////////////
	public static void insertardepartamento(String nuedep) {

		System.out.println("===============================================");
		System.out.println("Metodo insertardepartamento()");

		if (conectar() != null) {
			try {
				XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				System.out.printf("Inserto departamento: %s\n", nuedep);
				ResourceSet result = servicio.query("update insert " + nuedep + " into /universidad");
				col.close(); // borramos
				System.out.println("Dep insertado.");
			} catch (Exception e) {
				System.out.println("Error al insertar departamento.");
				e.printStackTrace();
			}
		} else
			System.out.println("Error en la conexión. Comprueba datos.");

	}// FIN
////////////////////////////////////////////////////////////////////////////////

	public static void veruniversidad() {

		System.out.println("===============================================");
		System.out.println("Metodo veruniversidad()");

		if (conectar() != null) {
			try {
				XPathQueryService servicio;
				servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				ResourceSet result = servicio.query("for $de in /universidad/departamento return $de");
				// recorrer los datos del recurso.
				ResourceIterator i;
				i = result.getIterator();
				if (!i.hasMoreResources())
					System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
				while (i.hasMoreResources()) {
					Resource r = i.nextResource();
					System.out.println("--------------------------------------------");
					System.out.println((String) r.getContent());
				}
				col.close(); // borramos
			} catch (XMLDBException e) {
				System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
				e.printStackTrace();
			}
		} else
			System.out.println("Error en la conexión. Comprueba datos.");
	}// FIN
////////////////////////////////////////////////////////////////////////////////

} // fin clase
