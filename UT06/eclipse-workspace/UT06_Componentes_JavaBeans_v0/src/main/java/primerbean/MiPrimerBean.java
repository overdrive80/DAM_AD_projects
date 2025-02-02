package primerbean;
import java.io.Serializable;

public class MiPrimerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String propiedad;

	public MiPrimerBean() {
		this.propiedad = "";
	}

	public String getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}
}
