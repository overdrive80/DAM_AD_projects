package eje1_directorios_ficheros;

import java.io.*;
import java.util.Scanner;

public class DAM_AD_01_R_010 {
	
	private static final String rutaDatos = System.getProperty("user.dir");

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		File directorio;
		File archivo;
		String strDirectorio = rutaDatos + "\\directorio_ejer_R10";
		String strArchivo = "archivo_R10.txt";

		directorio = crearDir(strDirectorio);

		archivo = crearFichero(directorio, strArchivo);

		verFichero(directorio, strArchivo);

		renombrarFichero(directorio, strArchivo, "archivo_R10_renombrado.txt");

	}

	public static File crearDir(String nombre) {
		// Creamos una referencia en memoria
		File dir = new File(nombre);

		// Si existe el directorio se avisa, si no, se crea el directorio. Al final se
		// devuelve la referencia
		if (dir.exists()) {
			System.out.println("El directorio " + dir.getName() + " ya existe.");

		} else {
			dir.mkdir();
			System.out.println("Se ha creado el directorio " + dir.getName() + ".");
		}

		return dir;

	}

	public static File crearFichero(File directorio, String nombre) {

		File archivo = new File(directorio, nombre);
		Scanner teclado = new Scanner(System.in);

		// Si el archivo ya existe preguntamo si se quiere eliminar
		if (archivo.exists()) {
			char respuesta;
			System.out.println("El archivo " + archivo.getName() + " ya existe. ¿Quiere eliminarlo (S/N)?: ");

			respuesta = teclado.next().toLowerCase().charAt(0);

			if (respuesta == 's') {

				archivo.delete();
				crearFichero(directorio, nombre);
			}

		} else {
			// Establecemos el flujo de salida de datos
			/*
			 * La razón por la que necesitas un objeto FileWriter junto con un objeto
			 * BufferedWriter es que FileWriter se encarga de abrir el archivo y escribir
			 * caracteres en él, mientras que BufferedWriter se encarga de almacenar
			 * temporalmente los datos en un búfer interno antes de escribirlos en el
			 * archivo.
			 */

			// En definitiva, FileWriter escribe y BufferedWriter le suministra los datos de
			// forma eficiente
			FileWriter fw;
			BufferedWriter bw;

			try {
				fw = new FileWriter(archivo);
				bw = new BufferedWriter(fw);

				System.out.println("Para crear el archivo se pide por consola el texto a contener.");
				System.out.println("Introduzca el texto que contendrá el archivo. "
						+ "Para finalizar escriba * en una nueva línea y pulse Intro: ");

				// Almacenamos la primera linea
				String cadena = teclado.nextLine();

				// Establecemos un bucle que capture cada línea
				while (!cadena.equalsIgnoreCase("*")) {
					// Guardamos la linea leida
					bw.write(cadena);
					// Salto de linea
					bw.newLine();
					// Solicitamos una nueva linea
					cadena = teclado.nextLine();
				}

				System.out.println("FICHERO CREADO.");

				/*
				 * es una buena práctica en Java para asegurarse de que todos los datos
				 * pendientes en el búfer se escriban en el archivo antes de liberar los
				 * recursos. Además, el cierre del BufferedWriter también cierra el FileWriter
				 * subyacente, lo que a su vez cierra el archivo.
				 */
				bw.close();

			} catch (FileNotFoundException fn) {
				System.out.println("ERROR. FICHERO NO SE ENCUENTRA");
			} catch (IOException io) {
				System.out.println("ERROR DE E/S ");
			} finally {
				teclado.close();
			}
		}

		return archivo;
	}

	public static void verFichero(File directorio, String nombre) {

		File archivo = new File(directorio, nombre);

		FileReader fr;
		BufferedReader br;

		// Si no existe el archivo se va
		if (!archivo.exists()) {
			System.out.println("El archivo " + archivo.getName() + " no existe.");

			// Si existe el archivo muestra el contenido en pantalla
		} else {
			try {
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);
				String linea;

				while ((linea = br.readLine()) != null) {
					System.out.println(linea);
				}
				
				// Muy importante!!
				br.close();

			} catch (FileNotFoundException fn) {
				System.out.println("ERROR. FICHERO NO SE ENCUENTRA");
			} catch (IOException io) {
				System.out.println("ERROR DE E/S ");
			}
		}
	}

	public static void renombrarFichero(File directorio, String nombre, String nuevoNombre) {

		// Renombramos el archivo2
		File archivo = new File(directorio, nombre);
		File archivo_renombrado = new File(directorio, nuevoNombre);
		boolean resOpe = false;

		if (archivo.exists()) {
			resOpe = archivo.renameTo(archivo_renombrado);
			
			if (resOpe) {
				System.out.println("El fichero " + archivo.getName() + " se ha renombrado a: " + nuevoNombre);
			}else{
				System.out.println("El fichero " + archivo.getName() + " no se pudo renombrar por que está abierto.");
			}
			
			
		} else {
			System.out.println("El fichero " + archivo.getName() + " no existe.");
		}
	}
}
