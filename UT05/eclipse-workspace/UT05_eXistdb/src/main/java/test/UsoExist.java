package test;

import java.io.File;

import org.xmldb.api.base.XMLDBException;

public class UsoExist {
	private static String baseURI = "xmldb:exist://localhost:8085/exist/xmlrpc/db";
	private static final String sep = "/";
	private static String URI;
	private static String usuario = "admin";
	private static String pass = "admin";

	public static void main(String[] args) {
		String rutaActual = System.getProperty("user.dir");

		// Establecemos la URI base de la base de datos, el usuario y pass
		ExistManager.setBaseUri(baseURI);
		ExistManager.setUser(usuario);
		ExistManager.setPass(pass);

		// mostrarVentas();
		// pruebas();
		// cursillos();

		// Primero. Creamos la ruta de conexion sobre la que crear la coleccion
		ExistManager.conectar(baseURI);
		// ExistManager.crearColeccion("Test");
		// ExistManager.borrarColeccion("Test");

		ExistManager.cargarCarpeta(rutaActual + File.separator + "colecciones");

	}

	@SuppressWarnings("unused")
	private static void mostrarVentas() {
		URI = baseURI + sep + "ColeccionVentas";
		ExistManager.conectar(URI);

		// System.out.println(ExistManager.getContadorColecciones());
		ExistManager.mostrarColecciones();
		ExistManager.mostrarContenido();

	}

	@SuppressWarnings("unused")
	private static void pruebas() {

		URI = baseURI + sep + "ColeccionPruebas";
		ExistManager.conectar(URI);
		ExistManager.mostrarContenido();

		boolean existe;

		existe = ExistManager.existeRecurso("zonas.xml", true);

		if (existe) {
			System.out.println("El recurso zonas.xml existe.");
		} else {
			System.out.println("No existe el recurso zonas.xml");
		}
	}

	@SuppressWarnings("unused")
	private static void cursillos() {
		URI = baseURI + sep + "BDCursillosXML";
		ExistManager.conectar(URI);

		boolean existe;

		existe = ExistManager.existeColeccion("cursillos_datos");

		try {
			if (existe) {

				System.out.println(
						"La coleccion cursillos_datos existe en: " + ExistManager.getColeccionActual().getName());

			} else {
				System.out.println("no existe");
			}

		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
