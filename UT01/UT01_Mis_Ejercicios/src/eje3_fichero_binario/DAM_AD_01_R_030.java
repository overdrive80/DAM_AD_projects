package eje3_fichero_binario;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * OPERACIONES CON UN FICHERO BINARIO.
 */
@SuppressWarnings("unused")
public class DAM_AD_01_R_030 {
	// fichero binario de Articulos
	public static final String nombreFichero = "ArticBinario.dat";
	public static final String ficheAuxiliar = "auxiliar.dat";
	public static final String rutaNombreFichero = "datos" + File.separator + nombreFichero;
	public static final String rutaFicheAuxiliar = "datos" + File.separator + ficheAuxiliar;
	// public static final String rutaNombreFichero = "auxiliar.dat";
	static Scanner teclado = new Scanner(System.in);
	static File ficherosal = null;
	static File fichero = null;

	public static void main(String[] args) {
		try {

			int op = 0, n = 0;
			double salario = 0;
			do {
				dibujamenu();
				System.out.println("TECLEA OPERACIÓN: ");
				op = teclado.nextInt();
				switch (op) {
				case 1:
					crear();
					break;
				case 2:
					visualizar();
					break;
				case 3:
					System.out.println("Teclea el número de artículo a consultar: ");
					n = teclado.nextInt();
					consultar(n);
					break;
				case 4: // insertar n registros
					System.out.println("Teclea número de artículos a insertar: ");
					n = teclado.nextInt();
					insertarregistros(n);
					break;
				case 5: // subir el precio de un artículo
					while (true) {
						System.out.println("Teclea el código de artículo a actualizar: ");
						n = teclado.nextInt();
						teclado.nextLine();
						if (comprobarsiexiste(n)) {
							actualizararticulo(n);
							break;
						} else
							System.out.println("El artículo tecleado no existe. \n" + "Teclea de nuevo: ");
					}
					ficherosal = new File(rutaFicheAuxiliar);
					fichero = new File(rutaNombreFichero);

					break;
				case 6:
					visualizartotales();
					break;
				case 7: // Borrar un artículo
					while (true) {
						System.out.println("Teclea el código de artículo a borrar: ");
						n = teclado.nextInt();
						teclado.nextLine();
						if (comprobarsiexiste(n)) {
							borrararticulo(n);
							break;
						} else
							System.out.println("El artículo tecleado no existe. \n" + "Teclea de nuevo: ");
					}
					ficherosal = new File(rutaFicheAuxiliar);
					fichero = new File(rutaNombreFichero);

					break;
				} // switch
			} while (op != 0);

		} catch (IOException e) {
			System.out.println("ERROR DE E/S ");
		}

	} // fin main
////////////////////////////////////////////////////////////////////////////////////

	private static void dibujamenu() {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(
				"\t...............................................\n" + "\t.  1 Crear fichero articulos binario. \n"
						+ "\t.  2 Visualizar los registros.  \n" + "\t.  3 Consultar un registro.\n"
						+ "\t.  4 Insertar n registros.\n" + "\t.  5 Actualizar los datos de un artículo.\n"
						+ "\t.  6 Visualizar totales unidades e importe.\n" + "\t.  7 Borrar un artículo.\n"
						+ "\t.  0 SALIR.\n" + "\t...............................................");
		System.out.println("-------------------------------------------------------------------------");

	} // fin dibujamenu
////////////////////////////////////////////////////////////////////////////////////

	private static void borrararticulo(int codigo) {
		// para borrar el artículo, se recorre el fichero y se graba en un auxiliar,
		// todos menos el que se desea borrar
		// luego se borra el fichero original y se renombre el auxiliar
		FileInputStream filein = null;
		DataInputStream fiche = null;
		FileOutputStream fileout = null;
		DataOutputStream ficheout = null;
		try { // recorremos el fichero y se crea uno nuevo auxiliar con los cambios
				// luego se borra y se renombra el auxiliar

			fichero = new File(rutaNombreFichero);
			filein = new FileInputStream(fichero);
			fiche = new DataInputStream(filein);
			ficherosal = new File(rutaFicheAuxiliar);
			if (ficherosal.exists()) {
				ficherosal.delete(); // borro el fichero auxiliar y lo creo limpio
				ficherosal = new File(rutaFicheAuxiliar);
			}
			fileout = new FileOutputStream(ficherosal, true);
			ficheout = new DataOutputStream(fileout);
			int codigoarti = 0, unidades = 0;
			String nombresartic = "";
			float precio = 0;
			while (true) {
				codigoarti = fiche.readInt();
				nombresartic = fiche.readUTF();
				precio = fiche.readFloat();
				unidades = fiche.readInt();
				if (codigoarti != codigo) {
					ficheout.writeInt(codigoarti);
					ficheout.writeUTF(nombresartic);
					ficheout.writeFloat(precio);
					ficheout.writeInt(unidades);
					System.out.println("REGISTRO GRABADO EN AUXILIAR.");
				}
			} // finwhie
		} catch (IOException io) {
			System.out.println("\n ------------ FIN DE FICHERO -------------- ");
		}
		// borramos
		try {
			ficheout.close();
			fiche.close();
			fileout.close();
			filein.close();
			// fichero.d
			if (fichero.exists())
				if (fichero.delete())
					System.out.println("----- borrado- ");
				else {
					System.out.println("---NO-- borrado- ");
				}
			if (ficherosal.renameTo(new File(rutaNombreFichero)))
				System.out.println("----- RENOMBRADO- ");
			else
				System.out.println("---NO-- RENOMBRADO- ");

		} catch (IOException e1) {
			System.out.println("----- ERROR AL CERRAR- ");
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	}

////////////////////////////////////////////////////////////////////////////////////
	private static void borrarrenombrar() {
		try {
			if (fichero.exists()) {
				System.out.println(fichero.getPath());
				String cmd = "cmd del " + fichero.getPath();
				System.out.println(cmd + " ----- borrado- ");
				Runtime.getRuntime().exec(cmd);
			}
			if (ficherosal.exists()) {
				String command = "cmd ren " + ficherosal + " " + rutaNombreFichero;
				Process child = Runtime.getRuntime().exec(command);
				child = Runtime.getRuntime().exec(command);
				System.out.println(command + " ----- renombrado- ");
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

////////////////////////////////////////////////////////////////////////////////////
	private static void actualizararticulo(int codigo) {

		FileInputStream filein = null;
		DataInputStream fiche = null;
		FileOutputStream fileout = null;
		DataOutputStream ficheout = null;
		try { // recorremos el fichero y se crea uno nuevo auxiliar con los cambios
				// luego se borra y se renombra el auxiliar

			fichero = new File(rutaNombreFichero);
			filein = new FileInputStream(fichero);
			fiche = new DataInputStream(filein);
			ficherosal = new File(rutaFicheAuxiliar);
			if (ficherosal.exists()) {
				ficherosal.delete(); // borro el fichero auxiliar y lo creo limpio
				ficherosal = new File(rutaFicheAuxiliar);
			}
			fileout = new FileOutputStream(ficherosal, true);
			ficheout = new DataOutputStream(fileout);
			int codigoarti = 0, unidades = 0;
			String nombresartic = "";
			float precio = 0;
			while (true) {
				codigoarti = fiche.readInt();
				nombresartic = fiche.readUTF();
				precio = fiche.readFloat();
				unidades = fiche.readInt();
				if (codigoarti == codigo) { // leer los datos para actualizar
					codigoarti = codigo;
					System.out.println("Teclea el nuevo nombre de artículo: ");
					nombresartic = teclado.nextLine();
					// leemos el precio
					while (true) {
						System.out.println("Teclea el nuevo precio de artículo: ");
						try {
							precio = teclado.nextInt();
							teclado.nextLine();
							break;
						} catch (InputMismatchException e) {
							System.out.println(">>>El PRECIO DEL ARTÍCULO DEBE SER NUMÉRICO. ");
							teclado.nextLine();
						}

					}
					;
					// Leemos las unidades y validamos
					while (true) {
						System.out.println("Teclea las nuevas unidades de artículo: ");
						try {
							unidades = teclado.nextInt();
							teclado.nextLine();
							break;
						} catch (InputMismatchException e) {
							System.out.println(">>>LAS UNIDADES DEBEN SER NUMÉRICÁS");
							teclado.nextLine();
						}
					}

				} // if (codigoarti==codigo)
				ficheout.writeInt(codigoarti);
				ficheout.writeUTF(nombresartic);
				ficheout.writeFloat(precio);
				ficheout.writeInt(unidades);
				System.out.println("REGISTRO GRABADO EN AUXILIAR.");

			} // finwhie
		} catch (IOException io) {
			System.out.println("\n ------------ FIN DE FICHERO -------------- ");
		}
// borramos
		try {

			ficheout.close();
			fiche.close();
			fileout.close();
			filein.close();

			// No funciona ni de una manera ni de otra.
			if (fichero.exists())
				if (fichero.delete())
					System.out.println("----- borrado- ");
				else {
					System.out.println("---NO-- borrado- ");
				}
			if (ficherosal.renameTo(new File(rutaNombreFichero)))
				System.out.println("----- RENOMBRADO- ");
			else
				System.out.println("---NO-- RENOMBRADO- ");
			borrarrenombrar(); // no funciona tampoco
		} catch (IOException e1) {
			System.out.println("----- ERROR AL CERRAR- ");
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	}// finactualizararticulo

////////////////////////////////////////////////////////////////////////////////////
	private static void consultar(int artic) {

		File fichero = null;
		FileInputStream filein = null;
		DataInputStream fiche = null;
		int codigoarti = 0, unidades = 0;
		String nombresartic = "";
		float precio = 0;
		String patron = "%-8d \t%-25s \t%5d \t\t%.2f";
		String cadena = "";
		int loca = 0;
		try {
			fichero = new File(rutaNombreFichero);
			filein = new FileInputStream(fichero);
			fiche = new DataInputStream(filein);
			System.out.println("-------------------------------------------------------------------------");
			while (true) {
				codigoarti = fiche.readInt();
				nombresartic = fiche.readUTF();
				precio = fiche.readFloat();
				unidades = fiche.readInt();
				if (artic == codigoarti) { // localizado
					System.out.println("COD ARTI      NOMBRE ART                       UNIDADES        PRECIO    ");
					System.out.println("---------    -------------------------        ------------    ---------  ");
					cadena = String.format(patron, codigoarti, nombresartic, unidades, precio);
					System.out.println(cadena);
					loca = 1;
					filein.close();
					fiche.close();
					break;
				}
			}
		} catch (IOException io) {
			/* fin fichero */}
		try {
			String mensaje = "";
			if (loca == 0)
				mensaje = "ARTICULO NUMERO : " + artic + ", NO EXISTE";
			System.out.println(mensaje + "\nPulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	}// fin consultar
////////////////////////////////////////////////////////////////////////////////////

	private static void crear() {
		File fichero = new File(rutaNombreFichero);
		if (fichero.exists()) {
			fichero.delete(); // borro el fichero y lo creo limpio
			fichero = new File(rutaNombreFichero);
		}
		FileOutputStream fileout = null;
		DataOutputStream dataOS = null;
		try {
			fileout = new FileOutputStream(fichero);
			dataOS = new DataOutputStream(fileout);
			// Creo un array para los datos del fichero.
			int codigoartic[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
			String nombresartic[] = { "Pala Padel", "Portátil Acer", "Calendario Gregoriano", "Tablet SamSung",
					"Portatil MAC", "Bolsa Padel", "Bolsa Portatil", "Lapiceros", "Ratón Optico", "Equipo Música" };

			float precio[] = { 100, 500, 10, 300, 1000, 15, 20, 10, 15, 300 };
			int unidades[] = { 4, 2, 4, 5, 7, 10, 10, 10, 5, 4 };

			for (int i = 0; i < codigoartic.length; i++) {
				dataOS.writeInt(codigoartic[i]);
				dataOS.writeUTF(nombresartic[i]);
				dataOS.writeFloat(precio[i]);
				dataOS.writeInt(unidades[i]);
			}
			dataOS.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR DE E/S, falla FileOutputStream ");
			// e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR AL OPERAR, falla .close ");
			// e.printStackTrace();
		}
		try {
			System.out.println("FICHERO CREADO. \nPulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}
	}// fin crear

////////////////////////////////////////////////////////////////////////////////////	
	@SuppressWarnings("resource")
	public static void visualizartotales() {
		int codigoarti = 0, unidades = 0;
		String nombresartic = "", articcaro = "", articvend = "";
		float precio = 0;
		String patron = "%-8d \t%-25s \t%5d \t\t%-5.1f \t\t%-6.2f";
		String cadena = "";
		int totaluni = 0, masuni = 0;
		float totalimport = 0, importe = 0, mascaro = 0;
		try {
			File fichero = new File(rutaNombreFichero);
			FileInputStream filein = new FileInputStream(fichero);
			DataInputStream fiche = new DataInputStream(filein);
			System.out.println(
					"-----------------------------------------------------------------------------------------");
			System.out
					.println("COD ARTI      NOMBRE ART                       UNIDADES        PRECIO         IMPORTE ");
			System.out
					.println("---------    -------------------------        ------------    ---------       ---------");
			while (true) {
				codigoarti = fiche.readInt();
				nombresartic = fiche.readUTF();
				precio = fiche.readFloat();
				unidades = fiche.readInt();
				importe = unidades * precio;
				totaluni = totaluni + unidades;
				totalimport = totalimport + importe;
				cadena = String.format(patron, codigoarti, nombresartic, unidades, precio, importe);
				System.out.println(cadena);
				if (precio > mascaro) {
					articcaro = nombresartic;
					mascaro = precio;
				}
				if (unidades > masuni) {
					articvend = nombresartic;
					masuni = unidades;
				}

			}
			
		} catch (IOException io) {// FIN FICHERO
			System.out.println(
					"---------    -------------------------        ------------    ---------       -----------");
			patron = "%-8s \t%-25s \t%5d  \t%8s \t\t%-7.2f";
			cadena = String.format(patron, "TOTALES: ", " ", totaluni, "       ", totalimport);
			System.out.println(cadena);
			System.out.println("Artículo más caro : " + articcaro);
			System.out.println("Artículo más vendido : " + articvend);
			System.out.println(
					"---------    -------------------------        ------------    ---------       -----------");
			try {
				System.out.println("Pulsa una tecla para volver. ");
				int c = System.in.read();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
	}// fin visualizar

////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("resource")
	public static void visualizar() {
		try {
			File fichero = new File(rutaNombreFichero);
			FileInputStream filein = new FileInputStream(fichero);
			
			DataInputStream fiche = new DataInputStream(filein);
			int codigoarti = 0, unidades = 0;
			String nombresartic = "";
			float precio = 0;
			String patron = "%-8d \t%-25s \t%5d \t\t%.2f";
			String cadena = "";

			System.out.println("-------------------------------------------------------------------------");
			System.out.println("COD ARTI      NOMBRE ART                       UNIDADES        PRECIO    ");
			System.out.println("---------    -------------------------        ------------    ---------  ");
			while (true) {
				codigoarti = fiche.readInt();
				nombresartic = fiche.readUTF();
				precio = fiche.readFloat();
				unidades = fiche.readInt();
				cadena = String.format(patron, codigoarti, nombresartic, unidades, precio);
				System.out.println(cadena);
			}
			
			
		} catch (IOException io) {
			System.out.println("\n ------------ FIN DE FICHERO -------------- ");
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
	}// fin visualizar
////////////////////////////////////////////////////////////////////////////////////

	public static boolean comprobarsiexiste(int artic) { // lectura secuencial
		int codigoarti = 0, unidades = 0;
		String nombresartic = "";
		float precio = 0;
		boolean existe = false;
		File fichero = null;
		FileInputStream filein = null;
		DataInputStream fiche = null;
		try {
			fichero = new File(rutaNombreFichero);
			filein = new FileInputStream(fichero);
			fiche = new DataInputStream(filein);
			while (true) {
				codigoarti = fiche.readInt();
				nombresartic = fiche.readUTF();
				precio = fiche.readFloat();
				unidades = fiche.readInt();
				if (artic == codigoarti) {
					existe = true;
					filein.close();
					fiche.close();
					break;
				}
			}
		} catch (IOException io) {
			/* fin fichero */ }

		return existe;

	}

////////////////////////////////////////////////////////////////////////////////////
	public static void insertarregistros(int numeroreg) throws IOException {
		int codigoarti = 0, unidades = 0;
		String nombresartic = "";
		float precio = 0;
		File fichero = new File(rutaNombreFichero);
		FileOutputStream fileout = new FileOutputStream(fichero, true);
		DataOutputStream fiche = new DataOutputStream(fileout);
		System.out.println("-----------------------------------------------------");
		System.out.println(" ENTRADA DE DATOS: teclea " + numeroreg + " registros.");
		System.out.println("-----------------------------------------------------");
		for (int i = 1; i <= numeroreg; i++) {
			System.out.println(">>>>>> Registro: " + i);
			do {
				System.out.println("Teclea el número de artículo: ");
				try {
					codigoarti = teclado.nextInt();
					teclado.nextLine();
					if (comprobarsiexiste(codigoarti))
						System.out.println(">>>El CODIGO DE ARTÍCULO YA EXISTE: " + codigoarti + ", teclea de nuevo.");
					else // sigo leyendo
						break;
				} catch (InputMismatchException e) {
					System.out.println(">>>El CODIGO DE ARTÍCULO DEBE SER NUMÉRICO. ");
					teclado.nextLine();
				}
			} while (true);
			// Leemos nombre
			System.out.println("Teclea el nombre de artículo: ");
			nombresartic = teclado.nextLine();
			// leemos el precio
			do {
				System.out.println("Teclea el precio de artículo: ");
				try {
					precio = teclado.nextInt();
					teclado.nextLine();
					break;
				} catch (InputMismatchException e) {
					System.out.println(">>>El PRECIO DEL ARTÍCULO DEBE SER NUMÉRICO. ");
					teclado.nextLine();
				}

			} while (true);
			// Leemos las unidades y validamos
			do {
				System.out.println("Teclea las unidades de artículo: ");
				try {
					unidades = teclado.nextInt();
					teclado.nextLine();
					break;
				} catch (InputMismatchException e) {
					System.out.println(">>>LAS UNIDADES DEBEN SER NUMÉRICÁS");
					teclado.nextLine();
				}

			} while (true);

			System.out.println("LOS DATOS A GRABAR SON: " + "COD: " + codigoarti + ", NOMBRE: " + nombresartic
					+ ", UNI: " + unidades + ", PVP: " + precio);
			fiche.writeInt(codigoarti);
			fiche.writeUTF(nombresartic);
			fiche.writeFloat(precio);
			fiche.writeInt(unidades);
			System.out.println("REGISTRO GRABADO.");

		} // fin for
		fiche.close();

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	}// fin insertar n registros

}// fin clase

