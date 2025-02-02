package ejemplos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import clases.Persona;

public class EjemploAgregarDatos {

	public static void main(String[] args) {

		File f = new File("personas.dat");

		// Escribir
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			Persona persona = new Persona("Persona_1");
			oos.writeObject(persona);

			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Añadir contenido nuevo
		// PASO1. Leer LOS OBJETOS DEL ARCHIVO
		List<Persona> listaPersonas = new ArrayList<Persona>();
		try {
			FileInputStream fis = new FileInputStream(f);

			try (ObjectInputStream ois = new ObjectInputStream(fis)) {

				while (true) {
					Persona elemento = (Persona) ois.readObject();
					listaPersonas.add(elemento);
				}
			}

		} catch (EOFException e) {
			System.out.println("FIN DE LECTURA.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// PASO2. AÑADIMOS EL NUEVO OBJETO A LA LISTA
		// Escribir
		Persona otraPersona = new Persona("Persona_2");
		listaPersonas.add(otraPersona);

		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for (Persona persona : listaPersonas) {
				oos.writeObject(persona);
			}
			
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
