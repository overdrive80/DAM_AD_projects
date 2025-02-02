package mySQL;

import java.sql.*;

public class ProcSubida {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			// recuperar parametros de main
			String dep =   "10"; // departamento //args[0];
			String subida =  "1000"; // subida  //args[1];

			// construir orden DE LLAMADA
			String sql = "{ call subida_sal (?, ?) } ";

			// Preparamos la llamada
			CallableStatement llamada = conexion.prepareCall(sql);
			// Damos valor a los argumentos
			llamada.setInt(1, Integer.parseInt(dep)); // primer argumento-dep
			llamada.setFloat(2, Float.parseFloat(subida)); // segundo arg

			//System.out.println(llamada.executeQuery()); // ejecutar el procedimiento
			int resultado = llamada.executeUpdate();
			System.out.printf("Subida realizada.... %d registros se han actualizado%n", resultado);
			
			llamada.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// fin de main
}// fin de la clase
