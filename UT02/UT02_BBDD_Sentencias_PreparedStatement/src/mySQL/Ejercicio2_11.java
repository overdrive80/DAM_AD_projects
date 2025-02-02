package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Ejercicio2_11 {

	public static void main(String[] args) {

		solucion("10");
	}

	private static void solucion(String dept_no) {

		try {
			// Cargamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Creamos la conexión con la base de datos
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			// Comprobamos si existe conexión
			if (conexion == null) {
				System.out.println("No hay conexión con la base de datos.");
			}

			// Definimos las sentencias SQL que necesitamos
			String SQL_Departamento = "SELECT DNOMBRE FROM DEPARTAMENTOS WHERE DEPT_NO = ?";
			String SQL_Empleados = "SELECT APELLIDO, SALARIO, OFICIO FROM EMPLEADOS WHERE DEPT_NO = ?";
			String SQL_Totales = "SELECT AVG(SALARIO), COUNT(EMP_NO) FROM EMPLEADOS WHERE DEPT_NO = ?";

			// DEPARTAMENTO
			PreparedStatement stmt = conexion.prepareStatement(SQL_Departamento);
			stmt.setInt(1, Integer.parseInt(dept_no));
			ResultSet rs = stmt.executeQuery();

			rs.next();
			System.out.println("EMPLEADOS DEL DEPARTAMENTO: " + rs.getString(1));
			System.out.println();

			// EMPLEADOS
			stmt = conexion.prepareStatement(SQL_Empleados);
			stmt.setInt(1, Integer.parseInt(dept_no));
			rs = stmt.executeQuery();

			System.out.printf("%-15s %-12s %-15s%n", "APELLIDO", "SALARIO", "OFICIO");
			System.out.printf("%-15s %-12s %-15s%n", "--------------", "-----------", "------------");

			while (rs.next()) {
                DecimalFormat formato = new DecimalFormat("##,##0.00");
                String valorFormateado = formato.format(rs.getFloat(2));
                
                System.out.printf("%-15s %-12s %-15s%n", 
                		rs.getString(1).toUpperCase(), 
                		valorFormateado, 
                		rs.getString(3));

				// OTRA MANERA
//				String formattedLine = String.format("%-15s %-12s %-15s", 
//						rs.getString(1).toUpperCase(), 
//						valorFormateado,
//						rs.getString(3));
//
//				System.out.println(formattedLine);

			}
			System.out.printf("%-15s %-12s %-15s%n", "--------------", "-----------", "------------");

			// TOTALES
			stmt = conexion.prepareStatement(SQL_Totales);
			stmt.setInt(1, Integer.parseInt(dept_no));
			rs = stmt.executeQuery();
			
			rs.next(); //Avanzamos el puntero al primer registro
            DecimalFormat formato = new DecimalFormat("##,##0.00");
            String valorFormateado = formato.format(rs.getFloat(1));
            
			System.out.println("SALARIO MEDIO: " + valorFormateado);
			System.out.println("NUM EMPLEADOS: " + rs.getInt(2));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Cargar el driver
			// Establecemos la conexion con la BD
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
