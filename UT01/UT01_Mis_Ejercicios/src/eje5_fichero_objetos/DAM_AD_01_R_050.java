package eje5_fichero_objetos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * OPERACIONES CON UN FICHERO DE OBJETOS.
 */
public class DAM_AD_01_R_050 {
	static Scanner teclado = new Scanner(System.in);
	public static final String nombreFichero = "FichObjArtic.dat";
	public static final String nombreFicheroOut = "FichObjArticAct.dat"; // fichero con las actualizaciones
	public static final String rutaNombreFichero = "datos" + File.separator + nombreFichero;
	public static final String rutaNombreFicheroOut = "datos" + File.separator + nombreFicheroOut;

////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		int op = 0, n = 0;
		do {
			dibujamenu();
			System.out.println("TECLEA OPERACIÓN: ");

			op = teclado.nextInt();
			switch (op) {
			case 1:
				creararticobj();
				break;
			case 2:
				visualizar(rutaNombreFichero);
				break;
			case 3:
				visualizar(rutaNombreFicheroOut);
				break;
			case 4:
				System.out.println("Teclea el número de artículo a consultar: ");
				n = teclado.nextInt();
				consultar(n);
				break;
			case 5: // insertar nodo
				System.out.println("Teclea número de artículos a insertar: ");
				n = teclado.nextInt();
				insertarregistros(n);
				break;
			case 6: // modificar un nodo artículo
				while (true) {
					System.out.println("Teclea el código de artículo a actualizar: ");
					n = teclado.nextInt();
					teclado.nextLine();
					if (comprobarsiexiste(n) != null) {
						actualizararticulo(n);
						break;
					} else
						System.out.println("El artículo tecleado no existe. \n" + "Teclea de nuevo: ");
				}
				break;
			case 7:
				visualizartotales();
				break;
			case 8: // Borrar un artículo
				while (true) {
					System.out.println("Teclea el código de artículo a BORRAR: ");
					n = teclado.nextInt();
					if (comprobarsiexiste(n) != null) {
						borrararticulo(n);
						break;
					} else
						System.out.println("El artículo tecleado no existe. \n" + "Teclea de nuevo: ");
				}

				break;
			} // switch
		} while (op != 0);

	} // fin main
////////////////////////////////////////////////////////////////////////////////////

	private static void dibujamenu() {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("\t...............................................\n"
				+ "\t.  1 Crear fichero OBJETOS de artículos. \n" + "\t.  2 Visualizar los datos.  \n"
				+ "\t.  3 Visualizar los datos actualizados (Modificaciones y Borrados).  \n"
				+ "\t.  4 Consultar un artículo.\n" + "\t.  5 Insertar n artículos.\n"
				+ "\t.  6 Modificar los datos de un artículo.\n" + "\t.  7 Visualizar totales unidades e importe.\n"
				+ "\t.  8 Borrar un objeto artículo.\n" + "\t.  0 SALIR.\n"
				+ "\t...............................................");
		System.out.println("-------------------------------------------------------------------------");

	} // fin dibujamenu
////////////////////////////////////////////////////////////////////////////////////

	private static void borrararticulo(int codigoarti) {
		// para borrar un registro se crea un fichero nuevo FichObjArticAct.obj que no
		// incluye el que se borra
		try {
			Articulos articulo = null;
			File fichero = new File(rutaNombreFichero);
			FileInputStream filein = new FileInputStream(fichero);
			ObjectInputStream dataIS = new ObjectInputStream(filein);
			// fichero que contendrá las actualizaciones
			File ficheroout = new File(rutaNombreFicheroOut);
			FileOutputStream fileout = new FileOutputStream(ficheroout);
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
			try {
				while (true) {
					articulo = (Articulos) dataIS.readObject();
					if (articulo.codigo == codigoarti) {
						// no se graba en el nuevo
					} // fin if
					else {
						Articulos art = new Articulos(articulo.codigo, articulo.deno, articulo.getPvp(), articulo.uni,
								articulo.zon);
						dataOS.writeObject(art);
					}
				} // fin while

			} catch (EOFException eo) {
				System.out.println("**REGISTRO BORRADO, LA ACTUALIZACIÓN SE ENCUENTRA EN FichObjArticAct.obj");
			} catch (ClassNotFoundException e) {
				System.out.println("ErrorClas no encontrada.");
			}
			dataIS.close(); // cerrar stream de entrada
			dataOS.close(); // cerrar stream de salida

		} catch (FileNotFoundException fn) {
			System.out.println("Error al crear FileInputStream o FileOutputStream. NO SE ENCUENTRA EL FICHERO.");
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectInputStream / FileOutputStream. ");
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	} // fin borrararticulo
////////////////////////////////////////////////////////////////////////////////////

	private static void visualizartotales() {
		String articcaro = "", articvend = "";
		int totaluni = 0, masuni = 0;
		float totalimport = 0, importe = 0, mascaro = 0;

		try {
			Articulos articulo = null;
			File fichero = new File(rutaNombreFichero);
			FileInputStream filein = new FileInputStream(fichero);
			// conecta el flujo de bytes al flujo de datos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
			try { // para el FF
				System.out.println("");
				System.out
						.println("--------- --------------------------- --------- --------- --------- --------------");
				System.out.println("COD ARTI  NOMBRE ART                   UNIDADES  PRECIO    IMPORTE     ZONA");
				System.out
						.println("--------- --------------------------- --------- --------- --------- --------------");
				while (true) {
					articulo = (Articulos) dataIS.readObject();
					importe = articulo.pvp * articulo.uni;
					System.out.printf("%-10s %-25s %8s %9s %9s %15s \n", articulo.codigo, articulo.deno, articulo.uni,
							articulo.pvp, importe, articulo.zon);

					totaluni = totaluni + articulo.uni;
					totalimport = totalimport + importe;
					if (articulo.pvp > mascaro) {
						articcaro = articulo.deno;
						mascaro = articulo.pvp;
					}
					if (articulo.uni > masuni) {
						articvend = articulo.deno;
						masuni = articulo.uni;
					}
				} // fin while
			} catch (EOFException eo) {
				/* FIN DE FICHERO */}
			System.out.println("--------- --------------------------- --------- --------- --------- --------------");
			System.out.printf("%-10s %-25s %8s %9s %9s %15s \n", "TOTALES: ", " ", totaluni, "  ", totalimport, " ");
			System.out.println("Artículo más caro : " + articcaro);
			System.out.println("Artículo más vendido : " + articvend);
			System.out.println("--------- --------------------------- --------- --------- --------- --------------");
			dataIS.close();
		} catch (ClassNotFoundException e) {
			System.out.println("ErrorClas no encontrada.");
		} catch (FileNotFoundException fn) {
			System.out.println("Error al crear FileInputStream. NO SE ENCUENTRA EL FICHERO.");
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectInputStream. ");
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}// fin visualizartotales
////////////////////////////////////////////////////////////////////////////////////

	private static Articulos comprobarsiexiste(int codi) {
		Articulos artic = null;
		try {
			Articulos articulo = null;
			File fichero = new File(rutaNombreFichero);
			FileInputStream filein = new FileInputStream(fichero);
			// conecta el flujo de bytes al flujo de datos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
			try {

				while (true) {
					articulo = (Articulos) dataIS.readObject();
					if (articulo.codigo == codi) {
						artic = articulo;
						break;
					}
				}
			} catch (EOFException eo) {
				/* FIN DE FICHERO. */} catch (ClassNotFoundException e) {
				System.out.println("ErrorClas no encontrada.");
			}
			dataIS.close();

		} catch (FileNotFoundException fn) {
			System.out.println("Error al crear FileInputStream. NO SE ENCUENTRA EL FICHERO.");
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectInputStream. ");
			e.printStackTrace();
		}
		return artic;
	} // fin comprobarsiexiste
////////////////////////////////////////////////////////////////////////////////////

	private static void actualizararticulo(int codigoarti) {
		int unidades = 0;
		String nombresartic = "", zona = "";
		float precio = 0;
		Articulos articulonuevo = null;
		// para modificar un registro se va creando un fichero nuevo y se van añadiendo
		// los regustros

		try {
			Articulos articulo = null;
			File fichero = new File(rutaNombreFichero);
			FileInputStream filein = new FileInputStream(fichero);
			ObjectInputStream dataIS = new ObjectInputStream(filein);
			// fichero que contendrá las actualizaciones
			File ficheroout = new File(rutaNombreFicheroOut);
			FileOutputStream fileout = new FileOutputStream(ficheroout);
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
			System.out.println("------------Actualizar-----------------");
			try {
				while (true) {
					articulo = (Articulos) dataIS.readObject();
					if (articulo.codigo == codigoarti) {
						// teclear los datos nuevos
						System.out.println("-----------------------------------------------------------------");
						System.out.println("------------ARTÍCULO A MODIFICAR --------------------------------");
						System.out.println("Código: " + articulo.codigo);
						System.out.println("Denominación: " + articulo.deno);
						System.out.println("Unidades: " + articulo.pvp);
						System.out.println("Precio: " + articulo.uni);
						System.out.println("Zona: " + articulo.zon);
						System.out.println("-----------------------------------------------------------------");
						System.out.println("Teclea el nuevo nombre de artículo " + codigoarti);
						nombresartic = teclado.nextLine();
						// leemos el precio
						do {
							System.out.println("Teclea el nuevo precio de artículo: ");
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
							System.out.println("Teclea las nuevas unidades de artículo: ");
							try {
								unidades = teclado.nextInt();
								teclado.nextLine();
								break;
							} catch (InputMismatchException e) {
								System.out.println(">>>LAS UNIDADES DEBEN SER NUMÉRICÁS");
								teclado.nextLine();
							}
						} while (true);
						// Leemos zona
						System.out.println("Teclea el nuevo nombre de la zona: ");
						zona = teclado.nextLine();
						System.out.println("LOS DATOS A ACTUALIZAR SON: " + "COD: " + codigoarti + ", NOMBRE: "
								+ nombresartic + ", UNI: " + unidades + ", PVP: " + precio + ", zona = " + zona);
						// modificamos el objeto
						articulonuevo = new Articulos(codigoarti, nombresartic, precio, unidades, zona);
						dataOS.writeObject(articulonuevo);
					} // fin if
					else {
						Articulos art = new Articulos(articulo.codigo, articulo.deno, articulo.getPvp(), articulo.uni,
								articulo.zon);
						dataOS.writeObject(art);
					}
				} // fin while

			} catch (EOFException eo) {
				System.out.println("--------- --------------------------- --------- --------- ----------------");
			} catch (ClassNotFoundException e) {
				System.out.println("ErrorClas no encontrada.");
			}
			dataIS.close(); // cerrar stream de entrada
			dataOS.close(); // cerrar stream de salida

		} catch (FileNotFoundException fn) {
			System.out.println("Error al crear FileInputStream o FileOutputStream. NO SE ENCUENTRA EL FICHERO.");
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectInputStream / FileOutputStream. ");
			e.printStackTrace();
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}
	}// fin actualizararticulo
////////////////////////////////////////////////////////////////////////////////////

	private static void insertarregistros(int n) {
		int codigoarti = 0, unidades = 0;
		String nombresartic = "", zona = "";
		float precio = 0;
		System.out.println("-----------------------------------------------------");
		System.out.println(" ENTRADA DE DATOS: teclea " + n + " registros.");
		System.out.println("-----------------------------------------------------");
		Articulos articulo = null;
		// declara el fichero
		try {
			File fichero = new File(rutaNombreFichero);
			ObjectOutputStream dataOS;
			if (!fichero.exists()) // crea un ObjectOutputStream la primera vez
			{
				FileOutputStream fileout;

				fileout = new FileOutputStream(fichero);

				dataOS = new ObjectOutputStream(fileout);
			} else { // si ya existe el fichero creará un ObjectOutputStream con el método
						// writeStreamHeader redefinido ( sin hacer nada)
				dataOS = new MiObjectOutputStream(new FileOutputStream(fichero, true));
			} // fin if
// entrada de artículos
			for (int i = 1; i <= n; i++) {
				System.out.println(">>>>>> Registro: " + i);
				do {
					System.out.println("Teclea el número de artículo: ");
					try {
						codigoarti = teclado.nextInt();
						teclado.nextLine();
						if (comprobarsiexiste(codigoarti) != null)
							System.out.println(
									">>>El CODIGO DE ARTÍCULO YA EXISTE: " + codigoarti + ", teclea de nuevo.");
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
				// Leemos zona
				System.out.println("Teclea el nombre de la zona: ");
				zona = teclado.nextLine();

				articulo = new Articulos(codigoarti, nombresartic, precio, unidades, zona);
				dataOS.writeObject(articulo);
				System.out.println("REGISTRO GRABADO, los datos son: " + "COD: " + codigoarti + ", NOMBRE: "
						+ nombresartic + ", UNI: " + unidades + ", PVP: " + precio + ", zona = " + zona);
			}
			dataOS.close(); // cerrar stream de salida
		} catch (FileNotFoundException e1) {
			System.out.println("Error al crear FileOutputStream. NO SE ENCUENTRA EL FICHERO ");
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectOutputStream. ");
			e.printStackTrace();
		}
		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e);
		}

	}// fin insertar n registros
////////////////////////////////////////////////////////////////////////////////////

	private static void consultar(int codi) {

		Articulos articulo = comprobarsiexiste(codi);
		if (articulo != null) {
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Código: " + articulo.codigo);
			System.out.println("Denominación: " + articulo.deno);
			System.out.println("Unidades: " + articulo.uni);
			System.out.println("Precio: " + articulo.pvp);
			System.out.println("Zona: " + articulo.zon);
			System.out.println("-----------------------------------------------------------------");
		} else {
			System.out.println("-----------------------------------------");
			System.out.println("** ARTICULO : " + codi + " , NO EXISTE. ** ");
			System.out.println("-----------------------------------------");
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}// fin consultar
////////////////////////////////////////////////////////////////////////////////////

	private static void visualizar(String nombrefic) {

		try {
			Articulos articulo = null;
			File fichero = new File(nombrefic);
			FileInputStream filein = new FileInputStream(fichero);
			// conecta el flujo de bytes al flujo de datos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
			try {
				System.out.println("");
				System.out.println("COD ARTI  NOMBRE ART                   UNIDADES  PRECIO       ZONA");
				System.out.println("--------- --------------------------- --------- --------- ----------------");
				while (true) {
					articulo = (Articulos) dataIS.readObject();
					System.out.printf("%-10s %-25s %8s %9s %15s \n", articulo.codigo, articulo.deno, articulo.pvp,
							articulo.uni, articulo.zon);
				}
			} catch (EOFException eo) {
				System.out.println("--------- --------------------------- --------- --------- ----------------");
			} catch (ClassNotFoundException e) {
				System.out.println("ErrorClas no encontrada.");
			}
			dataIS.close(); // cerrar stream de entrada

		} catch (FileNotFoundException fn) {
			System.out.println("Error al crear FileInputStream. NO SE ENCUENTRA EL FICHERO.");
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectInputStream. ");
			e.printStackTrace();
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}// fin visualizar
////////////////////////////////////////////////////////////////////////////////////

	private static void creararticobj() {
		// Datos a insertar en el fichero
		/* codigoartic denominacion precio unidades zona */
		int codigoartic[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String denominacion[] = { "Pala Padel", "Portátil Acer", "Calendario Gregoriano", "Tablet SamSung",
				"Portatil MAC", "Bolsa Padel", "Bolsa Portatil", "Lapiceros", "Ratón Optico", "Equipo Música" };
		float precio[] = { 100, 500, 10, 300, 1000, 15, 20, 10, 15, 300 };
		int unidades[] = { 4, 2, 4, 5, 7, 10, 10, 10, 5, 4 };
		String zona[] = { "Madrid", "Toledo", "Madrid", "Ávila", "Cáceres", "Madrid", "Cáceres", "Toledo", "Madrid",
				"Cáceres" };
		Articulos articulo = null;
		// declara el fichero
		File fichero = new File(rutaNombreFichero);
		FileOutputStream fileout = null;
		try {
			fileout = new FileOutputStream(fichero);
			ObjectOutputStream dataOS = null;
			dataOS = new ObjectOutputStream(fileout);
			for (int i = 0; i < codigoartic.length; i++) {
				articulo = new Articulos(codigoartic[i], denominacion[i], precio[i], unidades[i], zona[i]);
				dataOS.writeObject(articulo);
			}
			dataOS.close(); // cerrar stream de salida
			System.out.println("********* FICHERO CREADO CON 10 REGISTROS.");
			// borramos el actualizado si existe
			File ficheroout = new File(rutaNombreFicheroOut);
			if (ficheroout.delete()) {
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Error al crear FileOutputStream. NO SE ENCUENTRA EL FICHERO ");
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error E/S al operar con  ObjectOutputStream. ");
			e.printStackTrace();
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			System.in.read();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	} // fin crear
////////////////////////////////////////////////////////////////////////////////////
}// fin clase
