package eje1_directorios_ficheros;


import java.io.File;
import java.io.IOException;

public class DAM_AD_01_R_001 {
	
	private static final String rutaDatos = System.getProperty("user.dir");

	/* Un objeto File en Java actúa como un vínculo entre
	 *  tu programa Java y el sistema de archivos del sistema operativo. 
	 *  No realiza operaciones de lectura o escritura directamente 
	 *  en el archivo, pero proporciona una interfaz para interactuar con el sistema de archivos
	 */
	
	public static void main(String[] args) {
		File directorio;
		File archivo1;
		File archivo2;
		

		// Creamos una referencia
		directorio = new File(rutaDatos+"\\nuevo_directorio");

		// Se crea el directorio referenciado
		directorio.mkdir();

		// Creamos las referencias a los archivos indicando la carpeta padre
		archivo1 = new File(directorio, "archivo_1.txt");
		archivo2 = new File(directorio, "archivo_2.txt");

		// Creamos los archivos.
		prc_creacion_archivo_en_directorio(directorio, archivo1);
		prc_creacion_archivo_en_directorio(directorio, archivo2);

		// Renombramos el archivo2
		File archivo_renombrado = new File(directorio, "archivo_2_renombrado.txt");

		if (!existeArchivo(archivo_renombrado)){
			archivo2.renameTo(archivo_renombrado);
		}
	}

	public static void prc_creacion_archivo_en_directorio(File directorio, File archivo) {
		try {
			// Si existe archivo lo indicamos. Si no, iniciamos la creación.
			if (!existeArchivo(archivo)){

				// Creamos el archivo y lo informamos. Si no se pudo se muestra en pantalla.
				if (archivo.createNewFile()) {
					System.out.println("El archivo " + archivo.getName() + " se ha creado correctamente.");
				} else {

					System.out.println("El archivo " + archivo.getName() + " no se pudo crear.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static boolean existeArchivo (File archivo) {
		
		if (archivo.exists()) {
			System.out.println("El archivo " + archivo.getName() + " ya existe en el directorio "
					+ archivo.getParentFile().getName() + ".");
			
			return true;
		}
		
		return false;
	}
}
