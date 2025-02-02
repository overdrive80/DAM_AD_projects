package clases;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Participa implements Serializable{
	private int codparticipacion;
	private Estudiante estudiante;
	// Objeto estudiante
	private Proyecto proyecto;
	// Objeto proyecto
	private String tipoparticipacion;
	private int numaportaciones;

	public Participa() {
		super();
	}

	public Participa(int codparticipacion, Estudiante estudiante, Proyecto proyecto, String tipoparticipacion,
			int numaportaciones) {
		super();
		this.codparticipacion = codparticipacion;
		this.estudiante = estudiante;
		this.proyecto = proyecto;
		this.tipoparticipacion = tipoparticipacion;
		this.numaportaciones = numaportaciones;
	}

	public int getCodparticipacion() {
		return codparticipacion;
	}

	public void setCodparticipacion(int codparticipacion) {
		this.codparticipacion = codparticipacion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getTipoparticipacion() {
		return tipoparticipacion;
	}

	public void setTipoparticipacion(String tipoparticipacion) {
		this.tipoparticipacion = tipoparticipacion;
	}

	public int getNumaportaciones() {
		return numaportaciones;
	}

	public void setNumaportaciones(int numaportaciones) {
		this.numaportaciones = numaportaciones;
	}

}
