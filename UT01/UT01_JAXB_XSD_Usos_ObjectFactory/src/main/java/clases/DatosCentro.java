//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.4 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package clases;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tipoDatosCentro complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="tipoDatosCentro">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigocentro" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         <element name="nombrecentro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="director" type="{}tipoProfe"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDatosCentro", propOrder = {
    "codigocentro",
    "nombrecentro",
    "direccion",
    "director"
})
public class DatosCentro {

    @XmlElement(required = true)
    protected BigInteger codigocentro;
    @XmlElement(required = true)
    protected String nombrecentro;
    @XmlElement(required = true)
    protected String direccion;
    @XmlElement(required = true)
    protected Profesor director;

    /**
     * Obtiene el valor de la propiedad codigocentro.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigocentro() {
        return codigocentro;
    }

    /**
     * Define el valor de la propiedad codigocentro.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigocentro(BigInteger value) {
        this.codigocentro = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrecentro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrecentro() {
        return nombrecentro;
    }

    /**
     * Define el valor de la propiedad nombrecentro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrecentro(String value) {
        this.nombrecentro = value;
    }

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Obtiene el valor de la propiedad director.
     * 
     * @return
     *     possible object is
     *     {@link Profesor }
     *     
     */
    public Profesor getDirector() {
        return director;
    }

    /**
     * Define el valor de la propiedad director.
     * 
     * @param value
     *     allowed object is
     *     {@link Profesor }
     *     
     */
    public void setDirector(Profesor value) {
        this.director = value;
    }

}
