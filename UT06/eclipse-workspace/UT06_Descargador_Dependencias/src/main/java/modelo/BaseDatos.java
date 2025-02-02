package modelo;

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
	private Connection conn;

	// Constructor privado de la base
	private BaseDatos() {
	}
	
	// Constructor privado de la base con argumentos
	private BaseDatos(String url, String username, String password) {
		this.url = url;
		this.username=username;
		this.password = password;
	}

	// Pseudo constructor basado en un metodo de clase
	public static BaseDatos crearInstancia() {
		if (instancia == null) {
			instancia = new BaseDatos();
		}
		return instancia;
	}
	
	// Pseudo constructor basado en un metodo de clase
	public static BaseDatos crearInstancia(String url, String username, String password) {
		if (instancia == null) {
			instancia = new BaseDatos(url, username, password);
		}
		return instancia;
	}

	public ResultSet getResultSet(String sQuery) {
		//Creamos la conexión si no existe
		if (hasConexion() == false) {
			setConexion();
		}
		
		//Inicializamos las variables
		Statement stmt = null;
		ResultSet rSet = null;
		
		//Creamos un objeto declaracion y ejecutamos consulta
		try {
			stmt = this.conn.createStatement();
			rSet = stmt.executeQuery(sQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rSet;
	}
	
	public void setConexion() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			this.conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean hasConexion() {
		
		if (this.conn == null) {
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
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
