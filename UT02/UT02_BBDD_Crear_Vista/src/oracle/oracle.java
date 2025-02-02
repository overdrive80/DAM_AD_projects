package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class oracle {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");

			//Comprobamos si hay conexion
			if (conexion==null) {
				System.out.println("No hay conexion");
				System.exit(-1);
			}
			
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
