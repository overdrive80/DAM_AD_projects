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
 * <p>Clase Java para tipoProfe complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="tipoProfe">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoprofesor" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         <element name="nombreprofe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="salario" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoProfe", propOrder = {
    "codigoprofesor",
    "nombreprofe",
    "salario"
})
public class Profesor {

    @XmlElement(required = true)
    protected BigInteger codigoprofesor;
    @XmlElement(required = true)
    protected String nombreprofe;
    protected float salario;

    /**
     * Obtiene el valor de la propiedad codigoprofesor.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigoprofesor() {
        return codigoprofesor;
    }

    /**
     * Define el valor de la propiedad codigoprofesor.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigoprofesor(BigInteger value) {
        this.codigoprofesor = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreprofe.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreprofe() {
        return nombreprofe;
    }

    /**
     * Define el valor de la propiedad nombreprofe.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreprofe(String value) {
        this.nombreprofe = value;
    }

    /**
     * Obtiene el valor de la propiedad salario.
     * 
     */
    public float getSalario() {
        return salario;
    }

    /**
     * Define el valor de la propiedad salario.
     * 
     */
    public void setSalario(float value) {
        this.salario = value;
    }

}
