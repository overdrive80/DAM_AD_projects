//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.4 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package clases;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para tipoCentro complex type.</p>
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.</p>
 * 
 * <pre>{@code
 * <complexType name="tipoCentro">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="datoscentro" type="{}tipoDatosCentro"/>
 *         <element name="profesores" type="{}tipoProfesores"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoCentro", propOrder = {
    "datoscentro",
    "profesores"
})
public class Centro {

    @XmlElement(required = true)
    protected DatosCentro datoscentro;
    @XmlElement(required = true)
    protected Profesores profesores;

    /**
     * Obtiene el valor de la propiedad datoscentro.
     * 
     * @return
     *     possible object is
     *     {@link DatosCentro }
     *     
     */
    public DatosCentro getDatoscentro() {
        return datoscentro;
    }

    /**
     * Define el valor de la propiedad datoscentro.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosCentro }
     *     
     */
    public void setDatoscentro(DatosCentro value) {
        this.datoscentro = value;
    }

    /**
     * Obtiene el valor de la propiedad profesores.
     * 
     * @return
     *     possible object is
     *     {@link Profesores }
     *     
     */
    public Profesores getProfesores() {
        return profesores;
    }

    /**
     * Define el valor de la propiedad profesores.
     * 
     * @param value
     *     allowed object is
     *     {@link Profesores }
     *     
     */
    public void setProfesores(Profesores value) {
        this.profesores = value;
    }

}
