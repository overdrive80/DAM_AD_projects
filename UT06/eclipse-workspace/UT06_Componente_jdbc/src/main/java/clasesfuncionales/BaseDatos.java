package clasesfuncionales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Israel Lucas Torrijos
 * 
 *         Esta clase permite crear una instancia emulando una base de datos
 *         Sigue el patrón de diseño Singleton para evitar más de una instancia.
 * 
 *         Este patrón suele estar desaconsejado a nivel general pero aquí es
 *         útil.
 *
 */
public class BaseDatos {

	// Campos de la clase Singleton
	private static BaseDatos instancia = null;
	private String url;
	private String username;
	private String password;
	private String driver;
	private Connection conexion;

	// Constructor privado de la base con argumentos
	private BaseDatos(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	// Pseudo constructor basado en un metodo de clase
	public static BaseDatos crearInstancia(String driver, String url, String username, String password) {
		if (instancia == null) {
			instancia = new BaseDatos(driver, url, username, password);
			instancia.setConexion();
		}
		return instancia;
	}

	public ResultSet getResultSet(String sQuery) {
		// Creamos la conexión si no existe
		if (hasConexion() == false) {
			setConexion();
		}

		// Inicializamos las variables
		Statement stmt = null;
		ResultSet rSet = null;

		// Creamos un objeto declaracion y ejecutamos consulta
		try {
			stmt = this.conexion.createStatement();
			rSet = stmt.executeQuery(sQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rSet;
	}

	public void setConexion() {
		if (hasConexion() == false) {
			try {
				Class.forName(driver);

				this.conexion = DriverManager.getConnection(url, username, password);
			} catch (SQLException ex) {
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje : %s %n", ex.getMessage());
				System.out.printf("SQL estado: %s %n", ex.getSQLState());
				System.out.printf("Cód error : %s %n", ex.getErrorCode());
			} catch (ClassNotFoundException e) {
				System.out.printf("Driver '%s' no encontrado.%n", driver);
			}
		}
	}

	public Connection getConexion() {
		return conexion;
	}

	public boolean hasConexion() {

		if (this.conexion == null) {
			return false;
		}

		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void closeConexion() {

		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
