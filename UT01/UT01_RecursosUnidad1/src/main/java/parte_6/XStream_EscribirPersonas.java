package parte_6;

import java.io.*;
import com.thoughtworks.xstream.XStream;


//Ejecutar primero EscribirFichObject en paquete parte_4
public class XStream_EscribirPersonas {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		File f = new File("FichPersona.dat");
		FileInputStream fis = new FileInputStream(f);
		
		ObjectInputStream ois = new ObjectInputStream(fis);
		System.out.println("Comienza el proceso de creación del fichero a XML ...");

		ListaPersonas listaper = new ListaPersonas();

		try {
			while (true) { 
				Persona persona = (Persona) ois.readObject();
				listaper.add(persona); 
			}
		} catch (EOFException eo) {
		}
		ois.close(); 

		try {
			XStream xstream = new XStream();

			xstream.alias("ListaPersonasMunicipio", ListaPersonas.class);
			xstream.alias("DatosPersona", Persona.class);

			//xstream.aliasField("NombreAlumno", Persona.class, "nombre");
			//xstream.aliasField("EdadAlumno", Persona.class, "edad");

			// elimina la etiqueta lista en el XML (atributo de la claseListaPersonas)
			xstream.addImplicitCollection(ListaPersonas.class, "lista");

			xstream.toXML(listaper, new FileOutputStream("Personas.xml"));
			System.out.println("Creado fichero XML....");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 
