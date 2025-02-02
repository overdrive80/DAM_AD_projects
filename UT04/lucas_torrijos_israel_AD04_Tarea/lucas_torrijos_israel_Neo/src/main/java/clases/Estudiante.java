package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Estudiante implements Serializable{

	private int codestudiante;
	private String nombre;
	private String direccion;
	private String tlf;
	private Date fechaalta;
	private List<Participa> participaen = new ArrayList<Participa>();
	// lista con los proyectos en los que participa el estudiante, tipo Participa

	public Estudiante() {
		super();
	}

	public Estudiante(int codestudiante, String nombre, String direccion, String tlf, Date fechaalta,
			List<Participa> participaen) {
		super();
		this.codestudiante = codestudiante;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tlf = tlf;
		this.fechaalta = fechaalta;
		this.participaen = participaen;
	}

	public int getCodestudiante() {
		return codestudiante;
	}

	public void setCodestudiante(int codestudiante) {
		this.codestudiante = codestudiante;
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

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public Date getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
	}

	public List<Participa> getParticipaen() {
		return participaen;
	}

	public void setParticipaen(List<Participa> participaen) {
		this.participaen = participaen;
	}
	
	public void addParticipaen(Participa participa) {
		this.participaen.add(participa);
	}

}
