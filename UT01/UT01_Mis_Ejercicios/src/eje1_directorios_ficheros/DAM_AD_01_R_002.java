package eje1_directorios_ficheros;

import java.io.*;

public class DAM_AD_01_R_002 {
	
	private static final String rutaDatos = System.getProperty("user.dir");

	public static void main(String[] args) {

		File directorio = new File(rutaDatos+"\\nuevo_directorio");

		File[] archivos = directorio.listFiles();

		for (File archivo : archivos) {

			System.out.println("Nombre del archivo " + archivo.getName());
			System.out.println("Camino             " + archivo.getPath());
			System.out.println("Camino absoluto    " + archivo.getAbsolutePath());
			System.out.println("Se puede escribir  " + archivo.canRead());
			System.out.println("Se puede leer      " + archivo.canWrite());
			System.out.println("Tamaño en bytes    " + archivo.length());

		}

		System.out.println(" ******* lista de los archivos con filtro *******");

//		FileFilter logFilefilter = new FileFilter() {
//			public boolean accept(File file) {
//				if (file.getName().endsWith("renombrado.txt")) {
//					return true;
//				}
//				return false;
//			}
//		};
//		
//		archivos = directorio.listFiles(logFilefilter);
//		Usamos la sintaxis del constructor File(dir,nombre) en la expresión lambda
//		Otra opción: archivo -> archivo.getName().endsWith("renombrado.txt"));
		
		directorio = new File("src\\eje1_directorios_ficheros");
		
		archivos = directorio.listFiles((dir, nombre) -> nombre.endsWith(".java"));
		for (int i = 0; i < archivos.length; i++) {
			System.out.println(archivos[i].getAbsolutePath());
		}
	}

}
