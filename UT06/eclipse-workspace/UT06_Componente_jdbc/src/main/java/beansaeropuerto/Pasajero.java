package beansaeropuerto;

import java.io.Serializable;

public class Pasajero implements Serializable{

	private static final long serialVersionUID = 5869748523290136188L;
	private long pasajeroCod;
	private String nombre;
	private String telefono;
	private String direccion;
	private String pais;
	
	public Pasajero() {}

	public Pasajero(long pasajeroCod, String nombre, String telefono, String direccion, String pais) {
		super();
		this.pasajeroCod = pasajeroCod;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.pais = pais;
	}

	public long getPasajeroCod() {
		return pasajeroCod;
	}

	public void setPasajeroCod(long pasajeroCod) {
		this.pasajeroCod = pasajeroCod;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String toString() {
		
		return String.format("Codigo pasajero: %s, nombre: %s, tlf: %s, direccion: %s, pais: %s", 
				this.pasajeroCod, this.nombre, this.telefono, this.direccion, this.pais);
		
	}
	
}
