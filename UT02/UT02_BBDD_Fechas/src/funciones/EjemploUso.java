package funciones;

import java.time.Instant;

public class EjemploUso {
	public static void main(String[] args) {
		Instant instant = Instant.now();
		java.sql.Date fechaSQL = new java.sql.Date(instant.toEpochMilli());

		/*** SQL Date a Util Date ***/
		java.util.Date nuevaFecha = GestorFechas.toDate(fechaSQL);
		System.out.println(nuevaFecha);

		/*** Util Date a SQL Date ***/
		fechaSQL =  GestorFechas.toSQLDate(new java.util.Date());
		System.out.println(fechaSQL);
		
		/*** Formatear Fechas SQL/Util Date ***/
		String fechaFormateada = GestorFechas.applyPattern(fechaSQL, "dd/MM/yyyy KK:mm");
		System.out.println(fechaFormateada);
		
		fechaFormateada = GestorFechas.applyPattern(nuevaFecha, "dd/MM/yyyy kk:mm");
		System.out.println(fechaFormateada);

		/*** Convertir texto a fecha ***/
		String sFecha = "06/04/1980";
		String patron = "dd/MM/yyyy";
		java.util.Date fechadeString = GestorFechas.toDate(sFecha, patron);
		
		System.out.println( GestorFechas.applyPattern(fechadeString, patron));
		
	}
}
