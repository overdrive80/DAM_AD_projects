//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.4 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package clases;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the clases package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Centros_QNAME = new QName("", "centros");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: clases
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Centros }
     * 
     * @return
     *     the new instance of {@link Centros }
     */
    public Centros createCentros() {
        return new Centros();
    }

    /**
     * Create an instance of {@link Centro }
     * 
     * @return
     *     the new instance of {@link Centro }
     */
    public Centro createCentro() {
        return new Centro();
    }

    /**
     * Create an instance of {@link DatosCentro }
     * 
     * @return
     *     the new instance of {@link DatosCentro }
     */
    public DatosCentro createDatosCentro() {
        return new DatosCentro();
    }

    /**
     * Create an instance of {@link Profesor }
     * 
     * @return
     *     the new instance of {@link Profesor }
     */
    public Profesor createProfesor() {
        return new Profesor();
    }

    /**
     * Create an instance of {@link Profesores }
     * 
     * @return
     *     the new instance of {@link Profesores }
     */
    public Profesores createProfesores() {
        return new Profesores();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Centros }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Centros }{@code >}
     */
    @XmlElementDecl(namespace = "", name = "centros")
    public JAXBElement<Centros> createCentros(Centros value) {
        return new JAXBElement<>(_Centros_QNAME, Centros.class, null, value);
    }

}
