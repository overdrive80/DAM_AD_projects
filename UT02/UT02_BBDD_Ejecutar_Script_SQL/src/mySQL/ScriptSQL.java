package mySQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptSQL {

	private static final String rutaProyecto = ".";
	private static final String rutaCarpeta = "scriptsSQL";
	private static final String nombreScript = "scriptmysql.sql";
	
	private static final String rutaScript = rutaProyecto + File.separator + rutaCarpeta;
	private static final String script = rutaScript + File.separator + nombreScript;

	public static void main(String[] args) {

		File archivo = new File(script);
		FileReader lector;
		BufferedReader lectorBufer = null;
		StringBuilder scriptSQL = new StringBuilder();
		String consultas = "", linea = "", salto = System.getProperty("line.separator");

		try {

			// Si no existe el archivo finalizamos la ejecución
			if (!archivo.exists()) {
				System.out.println("El archivo " + nombreScript + " no existe.");
				return;
			}

			// Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Creamos la conexión
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo?allowMultiQueries=true",
					"EJEMPLO", "EJEMPLO");

			// Si existe conexión finalizamos
			if (conn == null) {
				System.out.println("No se ha creado ninguna conexión.");
				return;
			}

			// Creamos los archivos de lectura
			lector = new FileReader(archivo);
			lectorBufer = new BufferedReader(lector);

			// Path ruta = Paths.get(script );
			// BufferedReader lectorBufer = Files.newBufferedReader(ruta,
			// StandardCharsets.ISO_8859_1); //StandardCharsets.UTF_8)

			// Contruimos el archivo StringBuilder
			while ((linea = lectorBufer.readLine()) != null) {
				scriptSQL.append(linea).append(salto);
			}

			// Lo convertimos a String y lo pasamos a ejecución
			consultas = scriptSQL.toString();
			System.out.println(consultas);

			Statement stmt = conn.createStatement();

			// Ejecutamos las setencias del script
			stmt.executeUpdate(consultas);

			// Cerramos objetos
			lectorBufer.close();
			stmt.close();
			conn.close();


		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			controlErroresSQL(e);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void controlErroresSQL(SQLException e) {
		int codError = e.getErrorCode();

		// Controlamos error en la conexión de la base de datos.
		if (e instanceof com.mysql.cj.jdbc.exceptions.CommunicationsException) {

			System.out.println("La conexión con la base de datos ha fallado. ¿Está ejecutandose el servicio?");
			return;

		}

		// Controlamos segun el codigo de error lanzado
		switch (codError) {
		case 1049:
			System.out.println("La base de datos no existe." + " (" + e.getMessage() + ")");
			break;
		default:
			System.out.println(codError);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
