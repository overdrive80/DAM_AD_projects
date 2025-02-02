package mySQL;

import java.sql.*;

public class Metodo_ActualizarSalarioEmpleado {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// CONEXION A MYSQL
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

		String msg = actualizarSalarioEmpleado(conexion, 10, 100);
		
		System.out.println(msg);

		conexion.close();
	}// main

	public static String actualizarSalarioEmpleado(Connection conexion, int dept, float subida) {
		StringBuilder mensaje = new StringBuilder();
		try {
			Statement sentencia = conexion.createStatement();
			
			String sql = "UPDATE empleados SET salario = salario + " + subida + " WHERE dept_no = " + dept;
			int filas = sentencia.executeUpdate(sql);
			
			if (filas == 0)
				mensaje.append("NO se actualizó ningún registro");
			else
				mensaje.append("Actualizado/s. Filas afectadas: " + filas);
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
