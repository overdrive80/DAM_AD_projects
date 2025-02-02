package ejemploLibro;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings({"deprecation","unused"})

public class fechasss {
	public static void main(String[] args) {
		System.out.println("FECHA DEL DÍA");
		
		// Cargar la fecha de hoy con formato dd-MM-yyyy
		
		Date fechahoy = new Date();
		
		// Poniendo formato a la fecha con
		// new SimpleDateFormat("").format devuelve un string
		System.out.println("Fecha de hoy sin formato: " + fechahoy);
		System.out.println("Fecha de hoy con formato dd-MM-yyyy: " + new SimpleDateFormat("dd-MM-yyyy").format(fechahoy));
		System.out.println("Fecha de hoy con formato dd/MM/yyyy: " + new SimpleDateFormat("dd/MM/yyyy").format(fechahoy));
		
		// Extraer año, mes día, funciones obsoletas.
		
		int anio = fechahoy.getYear() + 1900; // se suma 1900
		int mes = fechahoy.getMonth() + 1; // el mes empieza en 0
		int diahoy = fechahoy.getDate();
		
		System.out.println("Fecha de hoy con funciones obsoletas año, mes, día : " + anio + "--" + mes + "--" + diahoy);
		System.out.println("");
		
		// Convertimos cadenas a fechas
		// Utilizamos SimpleDateFormat()
		System.out.println("Convertir cadena de fecha a fecha con formato");
		String fecha = "21-12-2007";
		
		try // se captura la excepción ParseException
		{
			// hay que poner MM que es el mes, mm son minutos
			DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			
			// Para validar fecha correcta, si no es correcta dispara ParseException
			formato.setLenient(false);
			
			Date mifecha = (Date) formato.parse(fecha);

			System.out.println("LA FECHA cadena: " + fecha + " se ha creado como: " + mifecha);
			System.out.println("LA FECHA cadena: " + fecha + " con localestring (obsoleto): " + mifecha.toLocaleString());
			
			// creamos una cadena y que devuelva la fecha
			// formateada segun formato, se utiliza .format
			String date2 = formato.format(mifecha);
			
			System.out.println("LA FECHA cadena: " + fecha + " con formato.format(date): " + date2);
			System.out.println("");
			
			// Pasar la fecha a sqlDate,
			// convertimos mifecha
			System.out.println("Convertir a sql.Date");
			
			java.sql.Date sqlDate1 = new java.sql.Date(mifecha.getTime());
			
			System.out.println("LA FECHA cadena: " + fecha + " en sql es: " + sqlDate1);
			
			// Pasar la fecha de hoy a sqlDate
			sqlDate1 = new java.sql.Date(fechahoy.getTime());
			
			System.out.println("LA FECHA hoy: " + fechahoy + " en sql es: " + sqlDate1);
			System.out.println("");
			System.out.println("Probando milisegundos");
			
			// Pasar la fecha a milisegundos con date.getTime();
			long dateInLong = mifecha.getTime();
			System.out.println("LA FECHA: " + fecha + " en milisegundos:" + dateInLong);
			
			// Paso de milisegundos a SQL.DATE
			java.sql.Date fechasql = new java.sql.Date(dateInLong);
			System.out.println("LA FECHA EN SQL ES: " + fechasql + " en milisegundos:" + dateInLong);
			
			// Para pasar de java.util.Date a java.sql.Date se deben tomar
			// los milisegundos de la primera y pasarlos al constructor de la segunda:
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
			// Con formato de java.sql.Date
			System.out.println(" SQLDATE: " + sqlDate);
			
			/*
			 * 1 Día = 86,400,000 Milisegundo 10 Días = 864,000,000 Milisegundos 50 Días =
			 * 4,320,000,000 Milisegundos 100 Días = 8,640,000,000 Milisegundos 500 Días =
			 * 43,200,000,000 Milisegundos • 1000 Días = 86,400,000,000 Milisegundos 10,000
			 * Días = 864,000,000,000 Milisegundos • 100,000 Días = 8,640,000,000,000
			 * Milisegundos
			 */
			// UN DIA SON 86400000 milisegundos, añadir días a las fechas
			long timeNow = System.currentTimeMillis(); // fecha actual en milisegundos
			System.out.println("Fecha actual en milisegundos: " + timeNow);
			
			java.sql.Date hoy = new java.sql.Date(timeNow); // pasar el long a fecha sql
			System.out.println("FECHA DE HOY: " + hoy);
			
			long dia = 86400000; // milisegundos de un día
			hoy = new java.sql.Date(timeNow - dia * 3); // hoy - 3 días
			System.out.println("fecha menos tres días: " + hoy);
			
			hoy = new java.sql.Date(timeNow - dia * 10); // hoy - 10 días
			System.out.println("fecha menos 10 días: " + hoy);
			
			hoy = new java.sql.Date(timeNow + dia * 30); // hoy + 30 días
			System.out.println("fecha mas 30 días: " + hoy);
			System.out.println("");
			System.out.println("Probando Calendar");
			
			// PARA OBTENER DÍA MES AÑO se utiliza
			// un objeto calendar
			// o con c.setTimeInMillis(long)
			// o con c.setTime(fecha.sql);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);
			int mHour = c.get(Calendar.HOUR_OF_DAY);
			int mMinute = c.get(Calendar.MINUTE);
			
			System.out.println("AÑO: " + mYear + ", MES:" + mMonth + ", DIA : " + mDay);
			
			hoy = new java.sql.Date(timeNow); // fecha de hoy
			
			c.setTime(hoy);
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			
			System.out.println("AÑO: " + mYear + ", MES: " + mMonth + ", DIA: " + mDay);
			
			// con las otras fechas mifecha
			c.setTime(mifecha);
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			
			System.out.println("Con mi fecha cadena convertida. AÑO: " + mYear + ", MES:" + mMonth + ", DIA : " + mDay);
			
		} catch (ParseException e) {
			System.out.println("LA FECHA NO TIENE FORMATO dd-MM-yyyy: " + fecha);
		}
		
		String fechaprueba = "10/10dfsdt/10";
		DateFormat formato2 = new SimpleDateFormat("dd/MM/yy");
		java.util.Date mifecha2 = null;
		java.sql.Date fecha2 = null;
		
		try {
			mifecha2 = (java.util.Date) formato2.parse(fechaprueba);
			fecha2 = new java.sql.Date(mifecha2.getTime());
			System.out.println("FECCHA OK: " + fecha2);
		} catch (ParseException e) {
			System.out.println("Error en formato de la fecha, debe ser dd/mm/yy");
			// e.printStackTrace();
		}
		
		// CADENA A SQL.DATE
		String fechaprueba1 = "10/10/10";
		DateFormat formato21 = new SimpleDateFormat("dd/MM/yy");
		java.util.Date mifecha21 = null;
		java.sql.Date fecha21 = null;
		
		try {
			mifecha21 = (java.util.Date) formato21.parse(fechaprueba1);
			fecha21 = new java.sql.Date(mifecha21.getTime());
			System.out.println("FECCHA OK: " + fecha21);
		} catch (ParseException e) {
			System.out.println("Error en formato de la fecha, debe ser dd/mm/yy");
			// e.printStackTrace();
		}
	}
}