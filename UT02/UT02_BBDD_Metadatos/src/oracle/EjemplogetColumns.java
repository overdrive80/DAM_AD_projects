package oracle;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemplogetColumns {

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

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData

			System.out.println("COLUMNAS TABLA DEPARTAMENTOS:");
			System.out.println("===================================");

			ResultSet columnas = null;
			//En Oracle el nombre de los objetos debe estar en mayuscula
			columnas = dbmd.getColumns(null, "EJEMPLO", "DEPARTAMENTOS", null);
			//getColumns(catalogo, esquema, patronDeTabla, tipos[])

			while (columnas.next()) {
				String nombCol = columnas.getString("COLUMN_NAME"); // getString(4)
				String tipoCol = columnas.getString("TYPE_NAME"); // getString(6)
				String tamCol = columnas.getString("COLUMN_SIZE"); // getString(7)
				String nula = columnas.getString("IS_NULLABLE"); // getString(18)

				System.out.printf("Columna: %s, Tipo: %s, Tamaño: %s, ¿Puede ser Nula:? %s %n", nombCol, tipoCol,
						tamCol, nula);
			}

			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

}
