package dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Estudiante {

	private Long codigo;
	private String nombre;
	private String direccion;
	private String telefono;
	private java.util.Date fechaAlta;
	private final int numColumnas = 5;

	public Estudiante() {
		super();
	}

	public Estudiante(Long codigo, String nombre, String direccion, String telefono, Date fechaAlta) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fechaAlta = fechaAlta;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	/*
	 * Asignamos la fecha que procede de una base de datos (java.sql.Date) a una del
	 * tipo java.util.Date. Como la primera es una subclase y hereda de
	 * java.util.Date no hay ningún error de tipado.
	 * 
	 * Convertimos la fecha al tipo UTIL.DATE (no debemos usar SQL.DATE)
	 * */ 
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String[] toArray() {

		String[] campos = new String[numColumnas];

		campos[0] = codigo.toString();
		campos[1] = nombre;
		campos[2] = direccion;
		campos[3] = telefono;

		// Usamos un patrón de fecha
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm");

		// Asignamos la fecha a otra del tipo Date para comprobar la instancia
		Date fechaTemporal = fechaAlta;

		// Comprobamos si la fecha es una instancia de java.sql.Date y la convertimos a
		// java.util.Date
		if (fechaAlta instanceof java.sql.Date) {
			fechaTemporal = new Date(fechaAlta.getTime());
		}

		// Con el tipo convertido a java.util.Date si podemos usar métodos para poder
		// usar la clase LocalDateTime
		LocalDateTime ldt = LocalDateTime.ofInstant(fechaTemporal.toInstant(), ZoneId.systemDefault());
		String fechaFormateada = ldt.format(dtf);

		campos[4] = fechaFormateada;

		return campos;
	}

}
