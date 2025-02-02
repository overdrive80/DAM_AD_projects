package mySQL;

import java.sql.*;

public class Metodo_CrearVista {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// CONEXION A MYSQL
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE OR REPLACE VIEW totales ");
		sql.append("(dep, dnombre, nemp, media) AS ");
		sql.append("SELECT d.dept_no, dnombre, COUNT(emp_no), AVG(salario) ");
		sql.append("FROM departamentos d LEFT JOIN empleados e ");
		sql.append("ON e.dept_no = d.dept_no ");
		sql.append("GROUP BY d.dept_no, dnombre ");

		String msg = crearVista(conexion, sql.toString());

		System.out.println(msg);
		
		// COMPROBAMOS SI SE HA CREADO LA VISTA
		String consultaSQL = """
								SELECT table_name 
								FROM information_schema.tables
								WHERE table_type = 'VIEW' AND table_name LIKE 'totales'
								""";
		
		Statement sentencia = conexion.createStatement();
		ResultSet rs = sentencia.executeQuery(consultaSQL);
		
		while (rs.next()) {
			System.out.println("La vista: " + rs.getString(1));
		}		

		conexion.close();
	}// main

	public static String crearVista(Connection conexion, String vista) {
		StringBuilder mensaje = new StringBuilder();
		try {
			Statement sentencia = conexion.createStatement();
			
			int filas = sentencia.executeUpdate(vista);
			mensaje.append("Vista creada. Filas afectadas: " + filas);
					
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
