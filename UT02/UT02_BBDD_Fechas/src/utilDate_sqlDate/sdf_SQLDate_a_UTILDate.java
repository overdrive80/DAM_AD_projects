package utilDate_sqlDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class sdf_SQLDate_a_UTILDate {
	
	public static void main(String[] args) {
        // Obtener la fecha y hora actuales como un objeto Instant
        Instant instant = Instant.now();
        
        // Crear un objeto java.sql.Date a partir de un Instant
        java.sql.Date fechaSQL = new java.sql.Date(instant.toEpochMilli());

        // Imprimir la fecha actual
        System.out.println("La fecha con java.sql.Date: " + fechaSQL);
		
        // Crear objeto java.util.Date
		java.util.Date fechaDate = new java.util.Date();
		System.out.println("La fecha con java.util.Date: " + fechaDate);
				
		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("La fecha con java.util.Date con formato 'dd/MM/yyyy': " +formato.format(fechaDate));
		System.out.println("La fecha con java.sql.Date con formato 'dd/MM/yyyy': " +formato.format(fechaSQL));

	}

}
