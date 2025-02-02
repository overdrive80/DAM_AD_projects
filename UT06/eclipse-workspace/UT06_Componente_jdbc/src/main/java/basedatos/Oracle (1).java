package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Oracle {
	
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String username;
	private static String password;
	
	
	public static Connection getConnection (String username, String password) {
		Connection conexion = null;
		
		// Establecemos las variables
		Oracle.username = username;
		Oracle.password = password;
	
		// Establecemos la conexion con la BD
		try {
			//Cargar el driver
			Class.forName(driver);
			
			conexion = DriverManager.getConnection(url, Oracle.username, Oracle.password);
					
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			controlErroresSQL(e);
		}
		
		return conexion;
	}
	
	public static void controlErroresSQL(SQLException e) {

		int codError = e.getErrorCode();

		// Controlamos segun el codigo de error lanzado
		switch (codError) {
		case 1049:
			System.out.println("La base de datos no existe." + " (" + e.getMessage() + ")");
			break;
		case 1017:
			System.out.println("Conexión denegada. El nombre de usuario/contraseña es incorrecto." + " (" + e.getErrorCode()
					+ ")");
			break;
		default:
			System.out.println(e.getMessage() + " (" + codError + ")");
		}

	}

}
