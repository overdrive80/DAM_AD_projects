package uso_SimpleDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Texto_a_Fecha {
	
	public static void main(String[] args) {

		try {
			String fecha = "01/03/2022";
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			//Verificar fecha correcta
			sdf.setLenient(false);
			Date fechaDate = sdf.parse(fecha);
			
			System.out.println(sdf.format(fechaDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
