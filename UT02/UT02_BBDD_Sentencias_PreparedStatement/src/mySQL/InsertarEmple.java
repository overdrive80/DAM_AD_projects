package mySQL;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InsertarEmple {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "EJEMPLO", "EJEMPLO");

//		    Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","EJEMPLO", "EJEMPLO");

			// construir orden INSERT
			String sql = "INSERT INTO empleados (emp_no, apellido, oficio,dir, fecha_alt, salario, comision, dept_no) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			System.out.println(sql);

			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setInt(1, 1001); // Integer.parseInt(string);
			sentencia.setString(2, "Lucas");
			sentencia.setString(3, "DIRECTOR");
			sentencia.setInt(4, 7852);
			sentencia.setFloat(6, 2000);
			sentencia.setNull(7, java.sql.Types.FLOAT);
			sentencia.setInt(8, 10);

//			  METODO CON LAS APIS ANTIGUAS
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            String dateInString = "01/03/1995";
//            java.sql.Date sqlDate;
//
//            try {
//                java.util.Date utilDate = formatter.parse(dateInString);
//                sqlDate = new java.sql.Date(utilDate.getTime());
//                sentencia.setDate(5, sqlDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

			// USO API JAVA.TIME. (DESDE JAVA 8)
			// Estableciendo directamente la fecha
			// Date date = Date.valueOf(LocalDate.of(1995, 03, 01));

			// Usando un formatter
			String dateInString = "01/03/1995";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Date date = Date.valueOf(LocalDate.parse(dateInString, formatter));
			sentencia.setDate(5, date);

			int filas = 0;

			filas = sentencia.executeUpdate();
			System.out.println("Filas afectadas: " + filas);

			sentencia.close(); // Cerrar Statement
			conexion.close(); // Cerrar conexión

		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (SQLException e) {
			// e.printStackTrace();

			System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
			System.out.printf("Mensaje   : %s %n", e.getMessage());
			System.out.printf("SQL estado: %s %n", e.getSQLState());
			System.out.printf("Cód error : %s %n", e.getErrorCode());
		}

	}// fin de main
}// fin de la clase
