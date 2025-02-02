package clases;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder={"codigoCentro", "nombreCentro" , "direccion", "director" })
public class DatosCentro {

	private int codigoCentro;
	private String nombreCentro;
	private String direccion;
	private Profesor director;
	
	public DatosCentro() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DatosCentro(int codigoCentro, String nombreCentro, String direccion, Profesor director) {
		super();
		this.codigoCentro = codigoCentro;
		this.nombreCentro = nombreCentro;
		this.direccion = direccion;
		this.director = director;
	}

	@XmlElement(name = "codigocentro" )
	public int getCodigoCentro() {
		return codigoCentro;
	}

	public void setCodigoCentro(int codigoCentro) {
		this.codigoCentro = codigoCentro;
	}
	
	@XmlElement(name = "nombrecentro" )
	public String getNombreCentro() {
		return nombreCentro;
	}

	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	@XmlElement(name = "direccion" )
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@XmlElement(name = "director" )
	public Profesor getDirector() {
		return director;
	}

	public void setDirector(Profesor director) {
		this.director = director;
	}
	
	
}
