package uso_LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fecha_a_String {
	
	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm");
		String fechaHoraActual = LocalDateTime.now().format(dtf);
		
		System.out.println(fechaHoraActual);

	}

}
