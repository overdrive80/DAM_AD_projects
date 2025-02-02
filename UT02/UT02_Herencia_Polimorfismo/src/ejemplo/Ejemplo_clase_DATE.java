package ejemplo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ejemplo_clase_DATE {
	public static void main(String[] args) {

		// Declaracion de variables
		java.sql.Date fechaSQL = java.sql.Date.valueOf("2022-03-01");
		// Upcasting implicito
		java.util.Date fecha = fechaSQL;
		// Declaramos un objeto LocalDate
		LocalDate localdate = LocalDate.now();

		// Comprobamos la instancia de la variable
		if (fecha instanceof java.sql.Date) {
			// Downcasting implicito
			localdate = ((java.sql.Date) fecha).toLocalDate();
		}
	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // ,new Locale("es", "ES"));
		System.out.println(localdate.format(dtf).toString());
	}
	
}
