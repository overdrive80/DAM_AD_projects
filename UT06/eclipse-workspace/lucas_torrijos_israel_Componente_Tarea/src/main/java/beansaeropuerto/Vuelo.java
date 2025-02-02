package beansaeropuerto;

import java.io.Serializable;
import java.util.Date;

import clasesfuncionales.GestorFechas;

public class Vuelo implements Serializable{

	private static final long serialVersionUID = -1376753410997076689L;
	private String identificador;
	private String aeropuertoOrigen;
	private String nombreAeroOrigen;
	private String paisOrigen;
	private String aeropuertoDestino;
	private String nombreAeroDestino;
	private String paisDestino;
	private String tipoVuelo;
	private int numPasajeros;
	private Date fechaVuelo;
	private int descuento;
	
	public Vuelo(){}
	
	public Vuelo(String identificador, String aeropuertoOrigen, 
			String aeropuertoDestino, String tipoVuelo, 
			Date fechaVuelo, int descuento) {
		super();
		this.identificador = identificador;
		this.aeropuertoOrigen = aeropuertoOrigen;
		this.aeropuertoDestino = aeropuertoDestino;
		this.tipoVuelo = tipoVuelo;
		this.fechaVuelo = fechaVuelo;
		this.descuento = descuento;
	}
	
	

	public Vuelo(String identificador, String aeropuertoOrigen, String nombreAeroOrigen, String paisOrigen,
			String aeropuertoDestino, String nombreAeroDestino, String paisDestino, String tipoVuelo, int numPasajeros,
			Date fechaVuelo, int descuento) {
		super();
		this.identificador = identificador;
		this.aeropuertoOrigen = aeropuertoOrigen;
		this.nombreAeroOrigen = nombreAeroOrigen;
		this.paisOrigen = paisOrigen;
		this.aeropuertoDestino = aeropuertoDestino;
		this.nombreAeroDestino = nombreAeroDestino;
		this.paisDestino = paisDestino;
		this.tipoVuelo = tipoVuelo;
		this.numPasajeros = numPasajeros;
		this.fechaVuelo = fechaVuelo;
		this.descuento = descuento;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getAeropuertoOrigen() {
		return aeropuertoOrigen;
	}

	public void setAeropuertoOrigen(String aeropuertoOrigen) {
		this.aeropuertoOrigen = aeropuertoOrigen;
	}

	public String getNombreAeroOrigen() {
		return nombreAeroOrigen;
	}

	public void setNombreAeroOrigen(String nombreAeroOrigen) {
		this.nombreAeroOrigen = nombreAeroOrigen;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public String getAeropuertoDestino() {
		return aeropuertoDestino;
	}

	public void setAeropuertoDestino(String aeropuertoDestino) {
		this.aeropuertoDestino = aeropuertoDestino;
	}

	public String getNombreAeroDestino() {
		return nombreAeroDestino;
	}

	public void setNombreAeroDestino(String nombreAeroDestino) {
		this.nombreAeroDestino = nombreAeroDestino;
	}

	public String getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}

	public String getTipoVuelo() {
		return tipoVuelo;
	}

	public void setTipoVuelo(String tipoVuelo) {
		this.tipoVuelo = tipoVuelo;
	}

	public int getNumPasajeros() {
		return numPasajeros;
	}

	public void setNumPasajeros(int numPasajeros) {
		this.numPasajeros = numPasajeros;
	}

	public Date getFechaVuelo() {
		return fechaVuelo;
	}

	public void setFechaVuelo(Date fechaVuelo) {
		this.fechaVuelo = fechaVuelo;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Vuelo [identificador=" + identificador + ", aeropuertoOrigen=" + aeropuertoOrigen
				+ ", nombreAeroOrigen=" + nombreAeroOrigen + ", paisOrigen=" + paisOrigen + ", aeropuertoDestino="
				+ aeropuertoDestino + ", nombreAeroDestino=" + nombreAeroDestino + ", paisDestino=" + paisDestino
				+ ", tipoVuelo=" + tipoVuelo + ", numPasajeros=" + numPasajeros + ", fechaVuelo=" + GestorFechas.applyPattern(this.fechaVuelo, "dd/MM/yyy")
				+ ", descuento=" + descuento + "]";
	}

	

}
