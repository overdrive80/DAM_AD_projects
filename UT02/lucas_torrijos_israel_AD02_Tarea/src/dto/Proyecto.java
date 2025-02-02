package dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Proyecto {
	private Long codProyecto;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private double presupuesto;
	private double extraAportacion;
	private final int numColumnas = 6;

	public Proyecto() {
	};

	public Proyecto(Long codProyecto, String nombre, Date fechaInicio, Date fechaFin, double presupuesto,
			double extraAportacion) {
		super();
		this.codProyecto = codProyecto;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.presupuesto = presupuesto;
		this.extraAportacion = extraAportacion;
	}

	public Long getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(Long codProyecto) {
		this.codProyecto = codProyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public double getExtraAportacion() {
		return extraAportacion;
	}

	public void setExtraAportacion(double extraAportacion) {
		this.extraAportacion = extraAportacion;
	}

	public String[] toArray() {

		String[] campos = new String[numColumnas];

		campos[0] = codProyecto.toString();
		campos[1] = nombre;

		// Usamos un patrón de fecha
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm");

		Date fechaTemporal = fechaInicio;

		// Comprobamos si la fecha es una instancia de java.sql.Date y la convertimos a
		// java.util.Date
		if (fechaInicio instanceof java.sql.Date) {
			fechaTemporal = new Date(fechaTemporal.getTime());
		}

		// Con el tipo convertido a java.util.Date si podemos usar métodos para poder
		// usar la clase LocalDateTime
		LocalDateTime ldt = LocalDateTime.ofInstant(fechaTemporal.toInstant(), ZoneId.systemDefault());
		String fechaFormateada = ldt.format(dtf);

		campos[2] = fechaFormateada;

		fechaTemporal = fechaFin;

		// Comprobamos si la fecha es una instancia de java.sql.Date y la convertimos a
		// java.util.Date
		if (fechaFin instanceof java.sql.Date) {
			fechaTemporal = new Date(fechaTemporal.getTime());
		}

		// Con el tipo convertido a java.util.Date si podemos usar métodos para poder
		// usar la clase LocalDateTime
		ldt = LocalDateTime.ofInstant(fechaTemporal.toInstant(), ZoneId.systemDefault());
		fechaFormateada = ldt.format(dtf);

		campos[3] = fechaFormateada;

		campos[4] = String.valueOf(presupuesto);
		campos[5] = String.valueOf(extraAportacion);

		return campos;
	}
}
