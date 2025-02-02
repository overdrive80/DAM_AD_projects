package mySQL;

import java.sql.*;

public class actividad2_6 {

	public static void main(String[] args) {

		parte1();
		parte2();
	}

	private static void parte1() {

		try {
			// Cargamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Creamos la conexion
			String URL = "jdbc:mysql://localhost:3306/ejemplo";
			String user = "EJEMPLO";
			String pass = user;
			Connection conn = DriverManager.getConnection(URL, user, pass);

			// Creamos el gestor de sentencias
			String SQL = """
							SELECT apellido, oficio, salario
							FROM empleados
							WHERE dept_no = 10;
					""";
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(SQL);

			System.out.println("Empleados del departamento 10:");
			while (result.next()) {
				System.out.printf("\t%s, %s, %f %n%n", result.getString(1),
						result.getString(2),result.getDouble(3));

			}

			result.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void parte2() {

		try {
			// Cargamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Creamos la conexion
			String URL = "jdbc:mysql://localhost:3306/ejemplo";
			String user = "EJEMPLO";
			String pass = user;
			Connection conn = DriverManager.getConnection(URL, user, pass);

			// Creamos el gestor de sentencias
			String SQL = """
					SELECT e.apellido, e.salario, d.dnombre AS nombre_departamento
					FROM empleados e
					JOIN departamentos d ON e.dept_no = d.dept_no
					WHERE e.salario = (SELECT MAX(salario) FROM empleados);
								""";
			
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(SQL);

			System.out.println("Empleado con máximo salario:");
			while (result.next()) {
				System.out.printf("\t%s, %f, %s %n", result.getString(1),
				result.getDouble(2),result.getString(3));

			}

			result.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
