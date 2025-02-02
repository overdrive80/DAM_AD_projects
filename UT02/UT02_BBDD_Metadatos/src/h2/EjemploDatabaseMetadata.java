package h2;

import java.sql.*;

public class EjemploDatabaseMetadata {
	public static void main(String[] args) {
		try {
			// Cargar el driver
			Class.forName("org.h2.Driver");

			// Establecemos la conexion con la BD
			Connection conexion = DriverManager.getConnection("jdbc:h2:C:/BBDD/h2/ejemplo/ejemplo","sa","");

			//Comprobamos si hay conexion
			if (conexion==null) {
				System.out.println("No hay conexion");
				System.exit(-1);
			}

			DatabaseMetaData dbmd = conexion.getMetaData();// Creamos
			// objeto DatabaseMetaData
			ResultSet resul = null;

			String nombre = dbmd.getDatabaseProductName();
			String driver = dbmd.getDriverName();
			String url = dbmd.getURL();
			String usuario = dbmd.getUserName();

			System.out.println("INFORMACI�N SOBRE LA BASE DE DATOS:");
			System.out.println("===================================");
			System.out.printf("Nombre : %s %n", nombre);
			System.out.printf("Driver : %s %n", driver);
			System.out.printf("URL    : %s %n", url);
			System.out.printf("Usuario: %s %n", usuario);

			// Obtener información de las tablas y vistas que hay
			// Declaramos un array de tipos para obtener una lista filtrada
			// de los tipos especificados
			String[] tipos = {"TABLE", "VIEW"};
			resul = dbmd.getTables("EJEMPLO", "PUBLIC", null, tipos);
			//getTables(catalogo, esquema, patronDeTabla, tipos[])

			while (resul.next()) {
				String catalogo = resul.getString(1);// columna 1
				String esquema = resul.getString(2); // columna 2
				String tabla = resul.getString(3); // columna 3
				String tipo = resul.getString(4); // columna 4
				System.out.printf("%s - Catalogo: %s, Esquema: %s, Nombre: %s %n", tipo, catalogo, esquema, tabla);
			}
			conexion.close(); // Cerrar conexion
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// fin de main
}// fin de la clase
