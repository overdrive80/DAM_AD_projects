package mySQL;

import java.sql.*;

public class InsertarDepartamento {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// Cargar el driver
			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			// recuperar argumentos de main
			String dep = "50"; // args[0]; // num. departamento
			String dnombre = "NUEVO DEPART"; // args[1]; // nombre
			String loc = "MADRID"; // args[2]; // localidad

			// construir orden INSERT
			String sql = "INSERT INTO departamentos VALUES " + "( ?, ?, ? )";

			System.out.println(sql);
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, Integer.parseInt(dep));
			sentencia.setString(2, dnombre);
			sentencia.setString(3, loc);

			int filas;//

			filas = sentencia.executeUpdate();
			System.out.println("Filas afectadas: " + filas);

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			System.out.println("HA OCURRIDO UNA EXCEPCIÓN:");
			System.out.println("Mensaje:    " + e.getMessage());
			System.out.println("SQL estado: " + e.getSQLState());
			System.out.println("Cód error:  " + e.getErrorCode());
		}

	}// fin de main
}// fin de la clase
