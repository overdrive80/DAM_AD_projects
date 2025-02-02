package mySQL;

import java.sql.*;

public class EjemploResultsetmetadata {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM departamentos");

			// Datos de las columnas
			ResultSetMetaData rsmd = rs.getMetaData();

			int nColumnas = rsmd.getColumnCount();
			String nula;
			System.out.printf("Número de columnas recuperadas: %d%n", nColumnas);

			for (int i = 1; i <= nColumnas; i++) {
				System.out.printf("Columna %d: %n ", i);
				System.out.printf("  Nombre: %s %n   Tipo: %s %n ", rsmd.getColumnName(i), rsmd.getColumnTypeName(i));

				if (rsmd.isNullable(i) == 0)
					nula = "NO";
				else
					nula = "SI";

				System.out.printf("  ¿Puede ser nula?: %s %n ", nula);
				System.out.printf("  Máximo ancho de la columna: %d %n", rsmd.getColumnDisplaySize(i));
			} // for

			sentencia.close();
			rs.close();
			conexion.close();

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main
}
