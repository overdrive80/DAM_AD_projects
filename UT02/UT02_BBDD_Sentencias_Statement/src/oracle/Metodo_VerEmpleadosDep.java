package oracle;

import java.sql.*;

public class Metodo_VerEmpleadosDep {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// Cargar el driver
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// Establecemos la conexion con la BD
		Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");

		verEmpleadosDep(conexion, 10);

		conexion.close();
	}// main

	public static void verEmpleadosDep(Connection conexion, int dept) {
		StringBuilder mensaje = new StringBuilder();

		try {
			Statement sentencia = conexion.createStatement();

			String sql = "select emp_no, apellido, oficio, salario from empleados where dept_no = " + dept;
			ResultSet resul = sentencia.executeQuery(sql);

			System.out.println("Datos de los empleados del dep : " + dept);
			while (resul.next()) {
				System.out.println("Emp-no:" + resul.getInt(1) + ". Apellido: " + resul.getString(2) + ". Oficio: "
						+ resul.getString(3) + ". Salario: " + resul.getFloat(4));
			}

			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			mensaje.append(String.format("HA OCURRIDO UNA EXCEPCIÓN:%n"));
			mensaje.append(String.format("Mensaje   : %s%n", e.getMessage().trim()));
			mensaje.append(String.format("SQL estado: %s%n", e.getSQLState()));
			mensaje.append(String.format("Cód error : %s%n", e.getErrorCode()));

			System.out.println(mensaje.toString());
		}
	}
}//
