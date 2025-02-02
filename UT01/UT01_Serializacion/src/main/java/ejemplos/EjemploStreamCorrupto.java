package ejemplos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import clases.Persona;

public class EjemploStreamCorrupto {

	public static void main(String[] args) {

		File f = new File("personas.dat");
		// crearArchivo(f);
		Persona persona = new Persona("Persona_1");

		// Escribir
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(persona);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Añadir contenido nuevo
		try {
			FileOutputStream fos = new FileOutputStream(f, true);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			
			persona = new Persona("Persona_2");
			oos.writeObject(persona);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Leer
		try {
			FileInputStream fis = new FileInputStream(f);
			
			try (ObjectInputStream ois = new ObjectInputStream(fis)) {
				Persona personaLeida = new Persona();

				while (true) { // lectura del fichero
					personaLeida = (Persona) ois.readObject(); // leer una Persona
					System.out.printf("Nombre: %s%n", personaLeida.getNombre());
				}
			}

		} catch (EOFException e) {
			System.out.println("FIN DE LECTURA.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} 
	}

	@SuppressWarnings("unused")
	private static void crearArchivo(File f) {
		if (!f.exists()) {

			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
