package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.*;

@SuppressWarnings("serial")

@XmlType(name = "asigType", propOrder = { "codasig", "nombre", "numalumnos", "notamedia", "alumnos"})
public class Asignatura implements Serializable{
	
	// Campos que iran en el XML
	private int codasig;
	private String nombre;
	private int numalumnos;
	private float notamedia;
	private List<Alumno> alumnos =new ArrayList<Alumno>();; // Agregamos la lista de alumnos
	
	//Campos que no será incluido en el XML
	private float sumanotas;

	public Asignatura() {
		super();
	}

	public Asignatura(int codasig, String nombre, int numalumnos, float sumanotas) {
		super();
		this.codasig = codasig;
		this.nombre = nombre;
		this.numalumnos = numalumnos;
		this.sumanotas = sumanotas;
					
		//Calculamos la notamedia con los valores dados en su constructor
		this.notamedia = getMedia(this.sumanotas, this.numalumnos);
	}

	public int getCodasig() {
		return codasig;
	}
	
	// El elemento @XmlElement permite renombrar. En este caso no serían necesarias y se dejan por documentación.
	// suele emplearse en el método set
	@XmlElement
	public void setCodasig(int codasig) {
		this.codasig = codasig;
	}

	public String getNombre() {
		return nombre;
	}

	@XmlElement
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumalumnos() {
		return numalumnos;
	}

	@XmlElement
	public void setNumalumnos(int numalumnos) {
		this.numalumnos = numalumnos;
	}

	
	public float getNotamedia() {
		return notamedia;
	}
	
	@XmlElement
	public void setNotamedia(float notamedia) {
		this.notamedia = notamedia;
	}
	

	public float getSumanotas() {
		return sumanotas;
	}
	
	// Con una única anotación @XmlTransient es suficiente para indicarle que omita el campo
	@XmlTransient
	public void setSumanotas(float sumanotas) {
		this.sumanotas = sumanotas;
	}
	

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	@XmlElementWrapper(name = "alumnos") // Para envolver los alumnos en un elemento "alumnos"
    @XmlElement(name = "alum")           // Cada elemento de la lista devuelta recibirá el nombre "alum"
	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	
	/*
	 * Método que calcula la media
	 * @param num, den. Numerador y denominador. 
	 * @return float
	 */
	private float getMedia(float num, float den) {
		//Calculo de nota media
		if (den != 0) {

			return (float) num / den;
		}

		return 0;

	}

}
