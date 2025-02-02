package beansaeropuerto;

import java.io.Serializable;

public class Pasaje implements Serializable{

	private static final long serialVersionUID = 7205071770284961652L;
	private long idPasaje;
	private long pasajeroCod;
	private String nombrePasajero;
	private String paisPasajero;
	private long numAsiento;
	private String clase;
	private double pvp;
	private String identificador;
	
	public Pasaje() {}

	public Pasaje(long idPasaje, long pasajeroCod, String identificador, long numAsiento, String clase, double pvp) {
		super();
		this.idPasaje = idPasaje;
		this.pasajeroCod = pasajeroCod;
		this.identificador = identificador;
		this.numAsiento = numAsiento;
		this.clase = clase;
		this.pvp = pvp;
	}

	public long getIdPasaje() {
		return idPasaje;
	}

	public void setIdPasaje(long idPasaje) {
		this.idPasaje = idPasaje;
	}

	public long getPasajeroCod() {
		return pasajeroCod;
	}

	public void setPasajeroCod(long pasajeroCod) {
		this.pasajeroCod = pasajeroCod;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public long getNumAsiento() {
		return numAsiento;
	}

	public void setNumAsiento(long numAsiento) {
		this.numAsiento = numAsiento;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public double getPvp() {
		return pvp;
	}

	public void setPvp(double pvp) {
		this.pvp = pvp;
	}

	public String getNombrePasajero() {
		return nombrePasajero;
	}

	public void setNombrePasajero(String nombrePasajero) {
		this.nombrePasajero = nombrePasajero;
	}

	public String getPaisPasajero() {
		return paisPasajero;
	}

	public void setPaisPasajero(String paisPasajero) {
		this.paisPasajero = paisPasajero;
	}

	@Override
	public String toString() {
		return "Pasaje [idPasaje=" + idPasaje + ", pasajeroCod=" + pasajeroCod + ", nombrePasajero=" + nombrePasajero
				+ ", paisPasajero=" + paisPasajero + ", numAsiento=" + numAsiento + ", clase=" + clase + ", pvp=" + pvp
				+ ", identificador=" + identificador + "]";
	}
	

}
