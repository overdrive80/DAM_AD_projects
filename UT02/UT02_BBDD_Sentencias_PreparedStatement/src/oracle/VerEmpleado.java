package oracle;

import java.sql.*;

public class VerEmpleado {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO",
					"EJEMPLO");

			// recuperar parametros de main
			String dep = "10"; // args[0]; //departamento
			String oficio = "DIRECTOR"; // args[1]; //oficio

			// construimos la orden SELECT
			String sql = "SELECT apellido, salario FROM empleados WHERE dept_no = ? AND oficio = ? ORDER BY 1";

			// Preparamos la sentencia
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, Integer.parseInt(dep));
			sentencia.setString(2, oficio);

			ResultSet rs = sentencia.executeQuery();
			while (rs.next())
				System.out.printf("%s => %.2f %n", rs.getString("apellido"), rs.getFloat("salario"));

			rs.close();// liberar recursos
			sentencia.close();
			conexion.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}// fin de main

}// fin de la clase
