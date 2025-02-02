package utilDate_sqlDate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import funciones.GestorFechas;

public class localdate_SQLDate_a_UTILDate {
	
	public static void main(String[] args) {
		
		//Creamos una fecha de tipo SQL Date
		LocalDate locaDate = LocalDate.of(2024, 02, 13);
		java.sql.Date fechaSQL = java.sql.Date.valueOf(locaDate);
		
		java.util.Date fechaUtil = java.util.Date.from(Instant.now());
		System.out.println(fechaUtil);
		
		//Modo de comprobación para convertir a UTIL Date
		if (fechaSQL instanceof java.sql.Date) {
			fechaUtil = GestorFechas.toDate(fechaSQL);
		}
		
		//Le damos un formato. Usamos LocalDate
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate newLocalDate = LocalDate.ofInstant(fechaUtil.toInstant(), ZoneId.systemDefault());
						
		System.out.println(newLocalDate.format(dtf2));
		
		
		
	}

}
