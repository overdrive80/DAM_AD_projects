package parte_4;

import java.io.*;
import otras.Persona;

public class EscribirFichObject2 {
	public static void main(String[] args) throws IOException {

		Persona persona;// defino variable persona

		File fichero = new File("FichPersona.dat");// declara el fichero
		FileOutputStream fileout;
		// conecta el flujo de bytes al flujo de datos
		ObjectOutputStream dataOS;

		if (!fichero.exists()) { // Si el fichero no existe crea un ObjectOutputStream, la primera vez
			fileout = new FileOutputStream(fichero);
			dataOS = new ObjectOutputStream(fileout);
		} else { // Si ya existe el fichero creará un ObjectOutputStream
					// con el método writeStreamHeader redefinido (sin hacer nada)
			fileout = new FileOutputStream(fichero,true);
			dataOS = (ObjectOutputStream) new MiObjectOutputStream(fileout);
		} // fin if

		String nombres[] = { "Ana", "Luis Miguel", "Alicia", "Pedro", "Manuel", "Andrés", "Julio", "Antonio",
				"María Jesús" };

		int edades[] = { 14, 15, 13, 15, 16, 12, 16, 14, 13 };
		System.out.println("GRABO LOS DATOS DE PERSONA.");
		for (int i = 0; i < edades.length; i++) { // recorro los arrays
			persona = new Persona(nombres[i], edades[i]); // creo la persona
			dataOS.writeObject(persona); // escribo la persona en el fichero
			System.out.println("GRABO LOS DATOS DE PERSONA.");
		}
		dataOS.close(); // cerrar stream de salida
	}
}