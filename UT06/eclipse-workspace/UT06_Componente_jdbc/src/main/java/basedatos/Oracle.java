package basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Oracle {
	private static Oracle instancia = null;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username;
	private String password;
	private Connection conexion = null;

	private Oracle(String username, String password) {
		// Establecemos las variables
		this.username = username;
		this.password = password;

	}

	// Constructor privado de la base con argumentos
	private Oracle(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	// Pseudo constructor basado en un metodo de clase
	public static Oracle crearInstancia(String driver, String url, String username, String password) {
		if (instancia == null) {
			instancia = new Oracle(driver, url, username, password);
			instancia.setConexion();
		}
		return instancia;
	}

	public static Oracle crearInstancia(String username, String password) {
		if (instancia == null) {
			instancia = new Oracle(username, password);
			instancia.setConexion();
		}
		return instancia;
	}

	/**** CONEXION BBDD ****/
	public Connection getConexion() {

		return this.conexion;
	}

	private void setConexion() {

		if (hasConexion() == false) {
			try {
				Class.forName(driver);

				this.conexion = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				controlErroresSQL(e);
			} catch (ClassNotFoundException e) {
				System.out.printf("Driver '%s' no encontrado.%n", driver);
			}
		}

	}

	public boolean hasConexion() {

		if (this.conexion == null) {
			return false;
		}

		return true;
	}

	/******** OPERACIONES CRUD ********/
	/**
	 * 
	 * Las que se crean en el componente suelen afectar A UNA FILA/OBJETO
	 * de una TABLA
	 */
	// Modifaremos el tipo de objeto que necesitemos recuperar con una instancia de
	// POJO
	@SuppressWarnings("unused")
	public Object consultar(String codigo) {
		Object obj = new Object();

		// Si no existe la conexión la creamos
		setConexion();

		String strSQL = "SELECT * FROM tabla WHERE codigo=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, codigo);

			ResultSet rs = ptmt.executeQuery();

			if (rs.next()) {

				String campo = rs.getString(1);

				// obj.setCampo (campo);

			} else {
				System.out.printf("El objeto %s no existe.%n", codigo);
			}

			rs.close();
			ptmt.close();

		} catch (SQLException e) {
			controlErroresSQL(e);
		}

		return obj;

	};

	public void insertar() {
	};

	public void actualizar() {
	};

	public void borrar() {
	};

	/*** CONTROL DE ERRORES ****/
	public void controlErroresSQL(SQLException e) {

		int codError = e.getErrorCode();

		// Controlamos segun el codigo de error lanzado
		switch (codError) {
		case 1049:
			System.out.println("La base de datos no existe." + " (" + e.getMessage() + ")");
			break;
		case 1017:
			System.out.println("Conexión denegada. El nombre de usuario/contraseña es incorrecto." + " ("
					+ e.getErrorCode() + ")");
			break;
		default:
			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje : %s %n", e.getMessage().trim());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n%n", e.getErrorCode());
		}

	}
}
