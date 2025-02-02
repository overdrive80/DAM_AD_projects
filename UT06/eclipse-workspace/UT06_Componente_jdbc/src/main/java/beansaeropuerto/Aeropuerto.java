package beansaeropuerto;

import java.io.Serializable;

public class Aeropuerto implements Serializable {
	
	private static final long serialVersionUID = -7898927314238174369L;
	private String codAeropuerto; // 5 caracteres
	private String nombre; 		  // 10 caracteres
	private String ciudad;		  // 10 caracteres
	private String pais;		  // 10 caracteres
	private long tasa;
	
	public Aeropuerto() {}

	public Aeropuerto(String codAeropuerto, String nombre, String ciudad, String pais, long tasa) {
		super();
		this.codAeropuerto = codAeropuerto;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.pais = pais;
		this.tasa = tasa;
	}

	public String getCodAeropuerto() {
		return codAeropuerto;
	}

	public void setCodAeropuerto(String codAeropuerto) {
		this.codAeropuerto = codAeropuerto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public long getTasa() {
		return tasa;
	}

	public void setTasa(long tasa) {
		this.tasa = tasa;
	}
	
	public String toString() {
		
		
		return String.format("Cod. aeropuerto: %s, nombre: %s, ciudad: %s, pais: %s, tasa: %d", this.codAeropuerto, this.nombre, this.ciudad, this.pais, this.tasa);
		
	}

}
