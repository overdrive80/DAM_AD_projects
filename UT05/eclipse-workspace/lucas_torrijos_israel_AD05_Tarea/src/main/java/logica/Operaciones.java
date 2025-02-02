package logica;

import ejercicios.Ejercicio01;
import ejercicios.Ejercicio02;

public class Operaciones {

	private String baseURI = "xmldb:exist://localhost:8085/exist/xmlrpc/db";
	private String usuario = "admin";
	private String pass = "admin";
	private String separador = "/";
	private String rutaActual = System.getProperty("user.dir");

	public Operaciones() {

		// Establecemos la URI base de la base de datos, el usuario y pass
		ExistManager.setDatosConexion(baseURI, usuario, pass);
		
		// Conectamos al root de la BBDD
		ExistManager.conectar(baseURI);

	}

	public void ejercicio0() {
		String carpetaColecciones = "colecciones";

		ExistManager.conectar(baseURI);
		ExistManager.cargarCarpeta(rutaActual + separador + carpetaColecciones);
	}

	public void ejercicio1() {

		String nombreColeccion = "ColeccionPruebas";
		String URI = baseURI + separador + nombreColeccion;
		
		// Comprobamos que la colección se haya subido
		if (!ExistManager.existeColeccion(nombreColeccion)) {
			System.out.println("No existe la colección: " + nombreColeccion);
			return;
		}
		
		// Establece la coleccion en la variable estatica
		ExistManager.conectar(URI);
		new Ejercicio01();
	}
	
	public Ejercicio02 ejercicio2() {
		
		String nombreColeccion = "ColeccionVentas";
		String URI = baseURI + separador + nombreColeccion;
		
		// Comprobamos que la colección se haya subido
		if (!ExistManager.existeColeccion(nombreColeccion)) {
			System.out.println("No existe la colección: " + nombreColeccion);
			return null;
		}
		
		// Establece la coleccion en la variable estatica
		ExistManager.conectar(URI);
		return new Ejercicio02();
		
	}

}
