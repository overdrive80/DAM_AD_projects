package oracle;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemplogetPrimaryKeys {

	public static void main(String[] args) {
		try {
			// Oracle
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");

			// Comprobamos si hay conexion
			if (conexion == null) {
				System.out.println("No hay conexion");
				System.exit(-1);
			}

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData

			System.out.println("CLAVE PRIMARIA TABLA DEPARTAMENTOS:");
			System.out.println("===================================");
			ResultSet pk = dbmd.getPrimaryKeys(null, "EJEMPLO", "DEPARTAMENTOS");
			String pkDep = "", separador = "";
			while (pk.next()) {
				pkDep = pkDep + separador + pk.getString("COLUMN_NAME");// getString(4)
				separador = "+";
			}
			System.out.println("Clave Primaria: " + pkDep);

			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

	/*
	 * Each primary key column description has the following columns:
	 * 
	 * 1. TABLE_CAT String => table catalog (may be null) 2. TABLE_SCHEM String =>
	 * table schema (may be null) 3. TABLE_NAME String => table name 4. COLUMN_NAME
	 * String => column name 5. KEY_SEQ short => sequence number within primary key(
	 * a value of 1 represents the first column of the primary key, a value of 2
	 * would represent the second column within the primary key). 6. PK_NAME String
	 * => primary key name (may be null)
	 */
}
