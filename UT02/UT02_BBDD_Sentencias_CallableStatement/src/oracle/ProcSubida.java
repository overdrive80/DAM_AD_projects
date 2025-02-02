package oracle;

import java.sql.*;

public class ProcSubida {
	public static void main(String[] args) {
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO",	"EJEMPLO");

			// recuperar parametros de main
			String dep = "10"; // departamento //args[0];
			String subida = "1000"; // subida //args[1];

			// construir orden DE LLAMADA
			String sql = "{ call subida_sal (?, ?) } ";

			// Preparamos la llamada
			CallableStatement llamada = conexion.prepareCall(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// Damos valor a los argumentos
			llamada.setInt(1, Integer.parseInt(dep)); // primer argumento-dep
			llamada.setFloat(2, Float.parseFloat(subida)); // segundo arg

			// System.out.println(llamada.executeQuery()); // ejecutar el procedimiento
			int resultado = llamada.executeUpdate();
			System.out.printf("Subida realizada.... %d registros se han actualizado%n", resultado);

			llamada.close();
			conexion.close();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {

			if (e.getErrorCode() == 1062) {
				System.out.println("CLAVE PRIMARIA DUPLICADA");
			} else if (e.getErrorCode() == 1452) {
				System.out.println("CLAVE AJENA NO EXISTE");
			} else if (e.getErrorCode() == 1438) {
				System.out.println("Valor mayor que el que permite la precisión especificada para esta columna");
			} else {
				System.out.println("HA OCURRIDO UNA EXCEPCIÓN:");
				System.out.println("Mensaje:    " + e.getMessage());
				System.out.println("SQL estado: " + e.getSQLState());
				System.out.println("Cód error:  " + e.getErrorCode());
			}
		}

	}// fin de main
}// fin de la clase
