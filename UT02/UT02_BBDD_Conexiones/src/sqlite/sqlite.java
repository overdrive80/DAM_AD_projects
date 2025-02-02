package sqlite;

import java.sql.*;

public class sqlite {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("org.sqlite.JDBC");

			// Establecemos la conexion con la BD
			// Si la base de datos no existe LA CREA.
			Connection conexion = DriverManager.getConnection("jdbc:sqlite:C:/BBDD/SQLite/ejemplo.db");

			// Preparamos la consulta
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT * FROM departamentos";
			ResultSet resul = sentencia.executeQuery(sql);

			// Recorremos el resultado para visualizar cada fila
			// Se hace un bucle mientras haya registros y se van mostrando
			while (resul.next()) {
				System.out.printf("%d, %s, %s %n", resul.getInt(1), resul.getString(2), resul.getString(3));
				//System.out.printf("%d, %s, %s %n", resul.getInt("dept_no"), resul.getString("dnombre"), resul.getString("loc") );
			}
			
			//Cerramos objetos
			resul.close(); // Cerrar ResultSet
			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
