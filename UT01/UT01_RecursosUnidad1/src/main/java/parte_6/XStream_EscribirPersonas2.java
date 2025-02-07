package parte_6;

import java.io.*;
import com.thoughtworks.xstream.XStream;

public class XStream_EscribirPersonas2 {
	public static void main(String[] args) {
		File fichero = new File("FichPersona.dat");
		FileInputStream fis;
		try {
			fis = new FileInputStream(fichero);// crea el flujo de entrada
			ObjectInputStream ois = new ObjectInputStream(fis);
			System.out.println("Comienza el proceso de creaci�n del fichero a XML ...");
			ListaPersonas listaper = new ListaPersonas();

			try {
				while (true) { // lectura del fichero
					Persona persona = (Persona) ois.readObject(); // leer una Persona
					listaper.add(persona); // a�adir persona a la lista
				} // del while
			} catch (EOFException eo) {// fin de fichero bo hago nada
			} catch (ClassNotFoundException cn) {
				cn.printStackTrace();
			} // final bloque try interno

			ois.close(); // cerrar stream de entrada

			XStream xstream = new XStream();
			xstream.alias("ListaPersonasMunicipio", ListaPersonas.class);
			xstream.alias("DatosPersona", Persona.class);
			xstream.addImplicitCollection(ListaPersonas.class, "lista");
			xstream.toXML(listaper, new FileOutputStream("Personas.xml"));
			System.out.println("Creado fichero XML....");

		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} // final bloque try mas externo
	} // fin main
} // fin EscribirPersonas
