package clases;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder={"codigoProfesor", "nombreProfe" , "salario" })
public class Profesor {

	private int codigoProfesor;
	private String nombreProfe;
	private double salario;
	
	public Profesor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Profesor(int codigoProfesor, String nombreProfe, double salario) {
		super();
		this.codigoProfesor = codigoProfesor;
		this.nombreProfe = nombreProfe;
		this.salario = salario;
	}
	
	@XmlElement(name = "codigoprofesor" )
	public int getCodigoProfesor() {
		return codigoProfesor;
	}
	public void setCodigoProfesor(int codigoProfesor) {
		this.codigoProfesor = codigoProfesor;
	}
	
	@XmlElement(name = "nombreprofe" )
	public String getNombreProfe() {
		return nombreProfe;
	}
	public void setNombreProfe(String nombreProfe) {
		this.nombreProfe = nombreProfe;
	}
	
	@XmlElement
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	
}
