package dto;

public class Entidad {

	private Long codigo;
	private String descripcion;
	private String telefono;
	private String direccion;
	private String contacto;
	private final int numColumnas = 5;

	public Entidad() {
	}

	public Entidad(Long codigo, String descripcion, String telefono, String direccion, String contacto) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.telefono = telefono;
		this.direccion = direccion;
		this.contacto = contacto;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String[] toArray() {

		String[] campos = new String[numColumnas];

		campos[0] = codigo.toString();
		campos[1] = descripcion;
		campos[2] = telefono;
		campos[3] = direccion;
		campos[4] = contacto;

		return campos;
	}
}
