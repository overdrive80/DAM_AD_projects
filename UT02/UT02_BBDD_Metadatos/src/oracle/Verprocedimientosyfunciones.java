package oracle;

/*
 * DEBEMOS CREAR UN PROCEDIMIENTO O FUNCTION EN LA BASE DE DATOS
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Verprocedimientosyfunciones {

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

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos objeto
															// DatabaseMetaData

			ResultSet proc = dbmd.getProcedures(null, "EJEMPLO", null);

			if (!proc.isBeforeFirst()) {

				System.out.println("No hay ningún procedimiento en la base de datos.");

			} else {

				while (proc.next()) {
					String proc_name = proc.getString("PROCEDURE_NAME");
					String proc_type = proc.getString("PROCEDURE_TYPE"); // TIPO 1: PROCEDIMIENTO, TIPO 2: FUNCION
					System.out.printf("Nombre Procedimiento: %s - Tipo: %s %n", proc_name, proc_type);
				}
			}

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main

}
