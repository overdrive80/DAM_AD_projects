package funciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/***
 * SimpleDateFormat:
 * - format: fecha a texto
 * - parse: texto a fecha
 **/

public class GestorFechas {

	public static java.util.Date toDate(java.sql.Date fecha) {

		java.util.Date nuevaFecha = null;

		if (fecha != null) {
			nuevaFecha = new java.util.Date(fecha.getTime());
		}

		return nuevaFecha;
	}

	public static java.sql.Date toSQLDate(java.util.Date fecha) {

		java.sql.Date nuevaFecha = null;

		if (fecha != null) {
			nuevaFecha = new java.sql.Date(fecha.getTime());
		}

		return nuevaFecha;
	}

	public static String applyPattern(java.util.Date fecha, String patron) {
		SimpleDateFormat sdf = null;
		String fechaFormateada = "";
		String patronDefecto = "dd-MM-yyyy";

		try {
			sdf = new SimpleDateFormat(patron);
			fechaFormateada = sdf.format(fecha);

		} catch (NullPointerException | IllegalArgumentException e) {

			if (fecha != null) {
				sdf = new SimpleDateFormat(patronDefecto);
				fechaFormateada = sdf.format(fecha);
			}
			
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());

		}

		return fechaFormateada;

	}

	public static java.util.Date toDate(String sFecha, String patron) {
		java.util.Date fecha = null;
		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat(patron);

			// Verificar fecha correcta
			sdf.setLenient(false);
			fecha = sdf.parse(sFecha);

		} catch (NullPointerException e) {
			System.err.println("El patrón no puede ser un valor nulo.");

		} catch (ParseException e) {

			System.err.println("La fecha en texto: " + sFecha + ", no se ajusta al patrón: " + patron);

		}

		return fecha;

	}

	/*
	 * G: Era (designación de era, como "AD")
	 * y: Año (año completo)
	 * M: Mes en año (como "julio" o "07")
	 * d: Día en mes
	 * k: Hora en día (1-24)
	 * H: Hora en día (0-23)
	 * m: Minutos en hora
	 * s: Segundos en minuto
	 * S: Milisegundos
	 * E: Día en semana (texto completo, como "domingo" o "lunes")
	 * D: Día en año (número de día del año)
	 * F: Semana en mes
	 * w: Semana en año
	 * W: Semana en mes
	 * a: AM/PM (indicador de mañana o tarde)
	 * h: Hora en AM/PM (1-12)
	 * K: Hora en AM/PM (0-11)
	 * z: Zona horaria (por ejemplo, "PST" para Hora Estándar del Pacífico)
	 * Z: Desplazamiento de zona horaria (en formato ±HHmm)
	 */

}
