package oracle;

import java.sql.*;

public class InsertarDep {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");

			// recuperar argumentos de main
			String dep = args[0]; // num. departamento
			String dnombre = args[1]; // nombre
			String loc = args[2]; // localidad

			// construir orden INSERT. Debemos usar comillas simples en los string
			String sql = String.format("INSERT INTO DEPARTAMENTOS VALUES (%s, '%s', '%s')", dep, dnombre, loc);

			System.out.println("-------------------------------------------");
			System.out.println(sql);

			// https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html

			Statement sentencia = conexion.createStatement();
			int filas = 0;
			try {
				filas = sentencia.executeUpdate(sql.toString());
				System.out.println("Filas afectadas: " + filas);
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
				System.out.printf("Mensaje   : %s %n", e.getMessage());
				System.out.printf("SQL estado: %s %n", e.getSQLState());
				System.out.printf("Cód error : %s %n", e.getErrorCode());
			}

			// LISTAMOS LA TABLA
			verTablaDepartamentos(conexion);

			// BORRAMOS LA FILA
//			sql = String.format("DELETE FROM DEPARTAMENTOS WHERE dept_no = %s ", dep);
//
//			System.out.println("-------------------------------------------");
//			System.out.println(sql);
//
//			try {
//				filas = sentencia.executeUpdate(sql.toString());
//				System.out.println("Filas afectadas: " + filas);
//			} catch (SQLException e) {
//				// e.printStackTrace();
//				System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
//				System.out.printf("Mensaje   : %s %n", e.getMessage());
//				System.out.printf("SQL estado: %s %n", e.getSQLState());
//				System.out.printf("Cód error : %s %n", e.getErrorCode());
//			}
//
//			// LISTAMOS LA TABLA
//			verTablaDepartamentos(conexion);

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexi�n

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// fin de main

	private static void verTablaDepartamentos(Connection conexion) {
		String sql = "SELECT * FROM DEPARTAMENTOS";

		Statement sentencia;
		try {
			sentencia = conexion.createStatement();

			ResultSet rs = sentencia.executeQuery(sql);

			System.out.println("-------------------------------------------");
			while (rs.next()) {

				System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}// fin de la clase
