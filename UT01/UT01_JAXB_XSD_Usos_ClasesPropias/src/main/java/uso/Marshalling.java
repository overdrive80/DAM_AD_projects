package uso;

import clases.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.*;

public class Marshalling {
	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
	private static final String rutaNuevoXMLCentrosProfes = rutaDatos + File.separator
			+ "centrosprofes_nueva_instancia.xml";

	public static void main(String[] args) {
		inicializar();
	}

	private static void inicializar() {

		Profesor profe = new Profesor();

		profe.setCodigoProfesor(1);
		profe.setNombreProfe("Israel Lucas");
		profe.setSalario(1500);

		// Creamos los datos del centro
		DatosCentro datosCentro = new DatosCentro();
		datosCentro.setCodigoCentro(1);
		datosCentro.setNombreCentro("IES Miguel de Cervantes");
		datosCentro.setDireccion("Paseo Arroyomolinos, 66; 28938 Móstoles (Madrid)");
		datosCentro.setDirector(profe);

		// Creamos el centro
		Centro centro = new Centro();

		// Añadimos los datos del Centro y añadimos el profesor a la lista de profesores
		centro.setDatosCentro(datosCentro);
		centro.getProfesores().add(profe);

		// Creamos el elemento raiz Centros
		Centros centros = new Centros();
		centros.getCentros().add(centro);

		/*
		 * USO DE JAXB PARA SERIALIZAR (MARSHALLING)
		 */
		try {
			JAXBContext contexto = JAXBContext.newInstance(Centros.class);
			Marshaller marshaller = contexto.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(centros, System.out);
			marshaller.marshal(centros, new FileOutputStream(rutaNuevoXMLCentrosProfes));

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
