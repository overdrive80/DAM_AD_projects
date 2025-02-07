package clases;
// Generated 1 ene 2024 11:36:06 by Hibernate Tools 6.3.1.Final

/**
 * Evaluaciones generated by hbm2java
 */
@SuppressWarnings("serial")
public class Evaluaciones implements java.io.Serializable {
	//Un atributo de la clase creada con sufijo ID que está compuesta por la clave principal
	private EvaluacionesId id;
	//Un atributo con cada clase que se relaciona
	private Asignaturas asignaturas;
	private Alumnos alumnos;
	//Atributo de la tabla intermedia en la relación N:N que motivó la creación de dos clases Evaluaciones 
	private Double nota;

	public Evaluaciones() {
	}

	public Evaluaciones(EvaluacionesId id, Asignaturas asignaturas, Alumnos alumnos, Double nota) {
		this.id = id;
		this.asignaturas = asignaturas;
		this.alumnos = alumnos;
		this.nota = nota;
	}

	public EvaluacionesId getId() {
		return this.id;
	}

	public void setId(EvaluacionesId id) {
		this.id = id;
	}

	public Asignaturas getAsignaturas() {
		return this.asignaturas;
	}

	public void setAsignaturas(Asignaturas asignaturas) {
		this.asignaturas = asignaturas;
	}

	public Alumnos getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(Alumnos alumnos) {
		this.alumnos = alumnos;
	}

	public Double getNota() {
		return this.nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

}
