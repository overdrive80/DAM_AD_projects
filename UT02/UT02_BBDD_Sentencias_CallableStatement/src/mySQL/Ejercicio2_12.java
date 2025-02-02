package mySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class Ejercicio2_12 {

	public static void main(String[] args) {

		solucion("10");
	}

	private static void solucion(String dept_no) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

			// Comprobamos si existe conexión
			if (conexion == null) {
				System.out.println("No hay conexión con la base de datos.");
			}
			
			String SQL_Departamento = "SELECT * FROM DEPARTAMENTOS";
			Statement stmt = conexion.createStatement();
			
			ResultSet rs = stmt.executeQuery(SQL_Departamento);
			
			System.out.printf("%-6s %-15s %-15s %-6s %-15s%n", "DEPT-NO", "NOMBRE", "LOCALIDAD", "MEDIASALARIO", "CONTADOREMPLES");
			System.out.printf("%-6s %-15s %-15s %-6s %-15s%n", "-------", "---------------", "---------------", "------------", "--------------");
			
			String SQL_Procedure = "{call SALARIO_MEDIO( ?, ?, ?) }";
			//Recorremos los departamentos
			while (rs.next()) {
				
				int numDept = rs.getInt(1);
				
				CallableStatement callstmt = conexion.prepareCall(SQL_Procedure);
				
				callstmt.setInt(1, numDept);
				
				// Registramos los parametros out del procedimiento
				callstmt.registerOutParameter(2, Types.FLOAT);
				callstmt.registerOutParameter(3, Types.INTEGER);
				
				callstmt.executeUpdate();
				
				System.out.printf("%-7d %-15s %-15s %12.2f %14d%n" , numDept, rs.getString(2), rs.getString(3), callstmt.getFloat(2), callstmt.getInt(3));
				
			}
			
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
