package h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class h2 {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("org.h2.Driver");

			// Establecemos la conexion con la BD
			//Si se desea crear la BD se añade créate=true, por ejemplo:
				/*String sURL = "jdbc:derby:memory:myDB;create=true";
				  Connection con = DriverManager.getConnection(sURL);*/
			Connection conexion = DriverManager.getConnection("jdbc:h2:C:/BBDD/h2/ejemplo/ejemplo","sa","");

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
