package uso_LocalDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class String_a_Fecha {
	
	public static void main(String[] args) {

		String fecha = "01/03/2022";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //,new Locale("es", "ES"));
		LocalDate ldate = LocalDate.parse(fecha, dtf);
		
		System.out.println(ldate.format(dtf));

	}

}
