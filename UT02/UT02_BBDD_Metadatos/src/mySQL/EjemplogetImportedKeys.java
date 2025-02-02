package mySQL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemplogetImportedKeys {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver
			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			// Oracle
			/*
			 * Class.forName ("oracle.jdbc.driver.OracleDriver"); Connection conexion =
			 * DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:XE",
			 * "EJEMPLO", "EJEMPLO");
			 */

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData

			System.out.println("CLAVES ajenas de la tabla EMPLEADOS y las claves primarias referenciadas:");
			System.out.println("==============================================");

			ResultSet fk = dbmd.getImportedKeys(null, "EJEMPLO", "EMPLEADOS");

			while (fk.next()) {
				String fk_name = fk.getString("FKCOLUMN_NAME");
				String pk_name = fk.getString("PKCOLUMN_NAME");
				String pk_tablename = fk.getString("PKTABLE_NAME");
				String fk_tablename = fk.getString("FKTABLE_NAME");
				System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
				System.out.printf("Tabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
			}

			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

}
