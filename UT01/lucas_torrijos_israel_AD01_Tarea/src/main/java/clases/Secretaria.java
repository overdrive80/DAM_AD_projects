package clases;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "asignaturas")
public class Secretaria {
	// private List<Alumno> alumnos;

	private List<Asignatura> asignaturas = new ArrayList<Asignatura>();

	public Secretaria() {
		super();
	}

	public Secretaria(List<Asignatura> asignaturas) {
		super();
		// this.alumnos = alumnos;
		this.asignaturas = asignaturas;

	}

	/* @XmlElementWrapper(name = "asig") 
       Para envolver las asignaturas en un elemento "asig" */
	@XmlElement(name = "asig") // Cada elemento de la lista devuelta recibirá el nombre "asig"
	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

}
