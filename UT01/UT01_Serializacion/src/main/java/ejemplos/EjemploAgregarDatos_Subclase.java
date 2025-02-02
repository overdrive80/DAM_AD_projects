package ejemplos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import clases.MiObjectOutputStream;
import clases.Persona;

public class EjemploAgregarDatos_Subclase {

	public static void main(String[] args) {

		File f = new File("personas.dat");
		FileOutputStream fos;
		ObjectOutputStream oos;
		
		Persona persona = new Persona("Nuevo");
		
		//Escribir
		try {
			if (!f.exists()) {

				fos = new FileOutputStream(f);
				oos = new ObjectOutputStream(fos);
				
				Persona personaTmp = new Persona("Persona_1");
				oos.writeObject(personaTmp);
				personaTmp = new Persona("Persona_2");
				oos.writeObject(personaTmp);

			} else {

				oos = new MiObjectOutputStream(new FileOutputStream(f, true));
			}
			
			oos.writeObject(persona);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Leer
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
}