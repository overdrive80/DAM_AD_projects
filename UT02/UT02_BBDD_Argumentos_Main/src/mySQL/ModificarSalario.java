package mySQL;

import java.sql.*;

public class ModificarSalario {

	public static void main(String[] args) {

		String dep = "10", subida = "100";
		// String dep = args[0];
		// String subida = args[1];

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// Cargar el driver
			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			String sql = String.format("UPDATE empleados SET salario = salario + %s WHERE dept_no = %s", subida, dep);

			System.out.println(sql);

			Statement sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(sql);
			System.out.printf("Empleados modificados: %d %n", filas);

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062)
				System.out.println("CLAVE PRIMARIA DUPLICADA");
			else if (e.getErrorCode() == 1452)
				System.out.println("CLAVE AJENA " + dep + " NO EXISTE");

			else {
				System.out.println("HA OCURRIDO UNA EXCEPCIÓN:");
				System.out.println("Mensaje:    " + e.getMessage());
				System.out.println("SQL estado: " + e.getSQLState());
				System.out.println("Cód error:  " + e.getErrorCode());
			}
		}

	}// fin de main
}// fin de la clase
