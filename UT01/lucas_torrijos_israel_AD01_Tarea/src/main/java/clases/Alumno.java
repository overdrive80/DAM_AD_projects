package clases;

import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "alumno", propOrder = { "codigo", "nombre", "curso"})
public class Alumno {
	private int codigo;
	private String nombre;
	private String curso;
	private float notamedia;
	
	
	public Alumno() {
		super();
	}


	public Alumno(int codigo, String nombre, String curso) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.curso = curso;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}

	// Con una única anotación @XmlTransient es suficiente para indicarle que omita el campo
	@XmlTransient
	public float getNotamedia() {
		return notamedia;
	}


	public void setNotamedia(float notamedia) {
		this.notamedia = notamedia;
	}
	
	
}
