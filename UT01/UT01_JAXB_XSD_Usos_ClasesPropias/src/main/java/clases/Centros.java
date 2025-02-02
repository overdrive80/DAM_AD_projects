package clases;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "centros")
@XmlRegistry
public class Centros {
    private static final QName _Centros_QNAME = new QName("", "centros");
	private List<Centro> centros = new ArrayList<Centro>();

	public Centros(List<Centro> centros) {
		super();
		this.centros = centros;
	}

	public Centros() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* Aunque es una lista de elementos no hay que perder
	 * la perspectiva, el elemento es Centro y no la lista.
	 */
	@XmlElement(name = "centro")
	public List<Centro> getCentros() {
		return centros;
	}

	public void setCentros(List<Centro> centros) {
		this.centros = centros;
	}
	
	//Necesario si al hacer el unmarshall (leer) queremos asociar el objeto raíz a un objeto JAXBElement
    @XmlElementDecl(namespace = "", name = "centros")
    public JAXBElement<Centros> createCentros(Centros value) {
        return new JAXBElement<>(_Centros_QNAME, Centros.class, null, value);
    }
}
