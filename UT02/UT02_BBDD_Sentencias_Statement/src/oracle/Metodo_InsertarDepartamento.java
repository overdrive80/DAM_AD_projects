package oracle;

import java.sql.*;

public class Metodo_InsertarDepartamento {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// Cargar el driver
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// Establecemos la conexion con la BD
		Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");

		String msg = insertardepartamento(conexion, 50, "NUEVO DEPT", "MADRID");
		
		System.out.println(msg);

		conexion.close();
	}// main

	public static String insertardepartamento(Connection conexion, int dept, String nom, String loc) {
		StringBuilder mensaje = new StringBuilder();
		try {
			String sql = "INSERT INTO departamentos VALUES(" + dept + ", '" + nom + "', '" + loc + "')";
			Statement sentencia = conexion.createStatement();
			
			int filas = sentencia.executeUpdate(sql);
			
			System.out.println(sql);
			mensaje.append("Registro Insertado. Filas afectadas: " + filas);
			
			sentencia.close();
		} catch (SQLException e) {
			mensaje.append(String.format("HA OCURRIDO UNA EXCEPCIÓN:%n"));
			mensaje.append(String.format("Mensaje   : %s%n", e.getMessage().trim()));
			mensaje.append(String.format("SQL estado: %s%n", e.getSQLState()));
			mensaje.append(String.format("Cód error : %s%n", e.getErrorCode()));
		}
		return mensaje.toString();
	}
}//
