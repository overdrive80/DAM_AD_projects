package uso_SimpleDateFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Fecha_a_String {
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDateTime ldt = LocalDateTime.now();
		
		Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();	
		Date fechaDate = Date.from(instant);

		System.out.println(sdf.format(fechaDate));

	}

}
