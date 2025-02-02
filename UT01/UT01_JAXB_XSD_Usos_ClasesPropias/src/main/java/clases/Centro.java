package clases;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"datosCentro", "profesores"})
public class Centro {

	private DatosCentro datosCentro;
	private List<Profesor> profesores = new ArrayList<Profesor>();
	
	public Centro(DatosCentro datosCentro, List<Profesor> profesores) {
		super();
		this.datosCentro = datosCentro;
		this.profesores = profesores;
	}

	public Centro() {
		super();
		// TODO Auto-generated constructor stub
	}

	@XmlElement(name= "datoscentro")
	public DatosCentro getDatosCentro() {
		return datosCentro;
	}

	public void setDatosCentro(DatosCentro datosCentro) {
		this.datosCentro = datosCentro;
	}
	
	@XmlElementWrapper(name = "profesores") 
	@XmlElement(name= "profe")
	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}
	
	
}
