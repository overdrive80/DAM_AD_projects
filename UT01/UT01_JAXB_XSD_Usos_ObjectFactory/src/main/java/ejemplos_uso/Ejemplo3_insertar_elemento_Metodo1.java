package ejemplos_uso;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import clases.Centro;
import clases.Centros;
import clases.DatosCentro;
import clases.ObjectFactory;
import clases.Profesor;
import clases.Profesores;
import gestionXML.LeerXML;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class Ejemplo3_insertar_elemento_Metodo1 {
	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaXMLCentrosProfes = rutaDatos + File.separator + "centrosprofes.xml";
	private static final String rutaNuevoXMLCentrosProfes = rutaDatos + File.separator + "centrosprofes_nuevo.xml";

	public static void main(String[] args) {
		// Mostramos el contenido del XML
		insertarElementoXML();

	}

	public static void insertarElementoXML() {

		/*
		 * Para insertar un elemento XML primero tenemos que leer el XML. Obtenemos un
		 * objeto JAXBElement que está mapeado con elemento raíz y contiene la
		 * estructura del XML Estamos haciendo un unmarshaller
		 */
		JAXBElement<?> jaxbElement = (JAXBElement<?>) LeerXML.run(ObjectFactory.class, rutaXMLCentrosProfes, false);

		// Ahora extraemos el contenido del contenedor JAXBElement:
		// El objeto de la clase que fue mapeada al elemento raiz
		Centros todosCentros = (Centros) jaxbElement.getValue();

		// Supongamos que queremos añadir un nuevo profesor al IES LA CORCHUELA
		Profesor nuevoProfe = new Profesor();
		nuevoProfe.setCodigoprofesor(BigInteger.valueOf(3499));
		nuevoProfe.setNombreprofe("Israel Lucas");
		nuevoProfe.setSalario(1150);

		List<Centro> listaCentros = todosCentros.getListaCentros();

		// Recorremos todos los centros y si se corresponde con el buscado le añadimos
		// el profesor
		for (Centro objCentro : listaCentros) {

			DatosCentro datosCentro = objCentro.getDatoscentro();

			// Si es el IES Corchuela tiene el codigo 5
			if (datosCentro.getCodigocentro().equals(BigInteger.valueOf(5))) {

				Profesores profesores = objCentro.getProfesores();
				List<Profesor> listaProfes = profesores.getListaProfesores();
				listaProfes.add(nuevoProfe);
				break;
			}
		}

		// Supongamos que queremos añadir un nuevo centro con sus profesores
		// Creamos los datos especificos del centro
		DatosCentro nuevoDatosCentro = new DatosCentro();

		nuevoDatosCentro.setCodigocentro(BigInteger.valueOf(7));
		nuevoDatosCentro.setDireccion("CALLE RIO LLOBREGAT 13");
		nuevoDatosCentro.setNombrecentro("IES MOSTOLES");
		nuevoDatosCentro.setDirector(nuevoProfe); // Reutilizamos el profesor anteriormente creado

		// Creamos un nuevo Centro
		Centro nuevoCentro = new Centro();
		nuevoCentro.setDatoscentro(nuevoDatosCentro);

		// Añadimos el nuevo Centro a la lista del objeto raíz
		listaCentros.add(nuevoCentro);

		// Tras añadir el nuevo profesor tenemos que crear el XML con un marshaller
		// GenerarXML.run(ObjectFactory.class, jaxbElement, rutaNuevoXMLCentrosProfes);
		try {
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Lo mostramos por pantalla, se puede añadir un parametro para controlar este
			// comportamiento
			m.marshal(jaxbElement, System.out);

			// Generar en disco el archivo XML
			m.marshal(jaxbElement, new File(rutaNuevoXMLCentrosProfes));
		} catch (JAXBException e) {

			e.printStackTrace();
		}
	}
}
