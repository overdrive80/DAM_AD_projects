package eje2_fichero_aleatorio_menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * OPERACIONES CON UN FICHERO ALEATORIO.
 */
@SuppressWarnings("unused")
public class DAM_AD_01_R_020 {

	public static final String nombrefichero = "AleatorioEmple.dat";
	static Scanner teclado = new Scanner(System.in);

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
					visualizartodos();
					break;
				case 3:
					visualizarsinhuecos();
					break;
				case 4: // insertar n registros
					System.out.println("Teclea número de registros a insertar: ");
					n = teclado.nextInt();
					insertarregistros(n);
					break;
				case 5: // consultar un número de empleado
					System.out.println("Teclea el número de empleado a consultar: ");
					n = teclado.nextInt();
					consultar(n);
					break;
				case 6: // subir salario a un empleado
					System.out.println("Teclea el número de empleado a subir salario: ");
					n = teclado.nextInt();
					System.out.println("Teclea el salario: ");
					salario = teclado.nextFloat();
					subirelsalario(n, salario);
					break;
				case 7: // borrar un empleado
					// Físicamente el registro no se puede borrar, lo que se hace es poner
					// a 0 los campos numéricos y a espacios las cadenas.
					// un registro se considera borrado cuando el código del empleado sea 0.
					System.out.println("Teclea el número de empleado a borrar: ");
					n = teclado.nextInt();
					borrarregistro(n);
					break;

				}// cierra Sub menu

			} while (op != 0);

		} catch (IOException e) {
			System.out.println("ERROR DE E/S ");
		}

	}// fin main
////////////////////////////////////////////////////////////////////////////////////

	private static void dibujamenu() {
		System.out.println("..................................\n" + ".  1 Crear fichero directo. \n"
				+ ".  2 Visualizar todos (huecos y borrados).  \n"
				+ ".  3 Visualizar registros (sin huecos y sin los borrados).\n" + ".  4 Insertar n registros.\n"
				+ ".  5 Consultar registro.\n" + ".  6 Subir salario a un empleado.\n" + ".  7 Borrar un registro.\n"
				+ ".  0 SALIR.\n" + "..................................\n");

	} // fin dibujamenu

////////////////////////////////////////////////////////////////////////////////////
	private static void borrarregistro(int num) {
		File fichero = new File(nombrefichero);
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(fichero, "rw");
			int pos;
			if (comprobarsiexiste(num)) { // borro el registro, lo marco a 0 los numéricos y a espacios las cadenas
				pos = (num - 1) * 36;
				file.seek(pos);
				file.writeInt(0);
				String nombre = "    ";
				StringBuffer buffer = new StringBuffer(nombre);
				buffer.setLength(10);
				file.writeChars(buffer.toString());
				file.writeInt(0);
				file.writeDouble(0);
				System.out.println("---------------------------------------------------------");
				System.out.println("------------ EMPLEADO BORRADO  " + num + " -----------------");
			} else {
				System.out.println("----------------------------------------------------------------");
				System.out.println("EL NUMERO DE EMPLEADO " + num + " NO SE ENCUENTRA EN EL ARCHIVO.");
				System.out.println("----------------------------------------------------------------");
			}
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println(" ERROR NO ENCONTRADO EL FICHERO. ");
		} catch (IOException e) {
			System.out.println("ERROR DE E/S ");
		}
	}// borrarregistro
////////////////////////////////////////////////////////////////////////////////////

	private static void insertarregistros(int n2) {

		File fichero = new File(nombrefichero);
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(fichero, "rw");
			int num = 0, dep;
			String nombre;
			double salario;
			StringBuffer buffer = null;
			System.out.println(" -----------------------------------------------------------------");
			System.out.println(" ENTRADA DE DATOS, SE LEERÁN Y SE GRABARÁN " + n2 + " registros. ");
			System.out.println(" -----------------------------------------------------------------");
			long posicion = 0;
			for (int i = 0; i < n2; i++) {
				do { // El código de empleado 0 no se admite.
						// Un código de empleado 0 indica que no hay registro, o es un hueco o se ha
						// borrado
					System.out.println(" Teclea el número (entre 1 y 200): ");
					try {
						num = teclado.nextInt();
					} catch (InputMismatchException e) {
						num = -1;
						System.out.println("El dato debe ser numérico.\n ");
					}
					teclado.nextLine();
				} while ((num > 200) || (num < 0));
				posicion = (num - 1) * 36;
				file.seek(posicion);
				file.writeInt(num);
				System.out.println(" Teclea el nombre: ");
				nombre = teclado.nextLine();
				buffer = new StringBuffer(nombre);
				buffer.setLength(10);
				file.writeChars(buffer.toString());
				System.out.println(" Teclea el número de departamento: ");
				dep = teclado.nextInt();
				teclado.nextLine();
				file.writeInt(dep);
				System.out.println(" Teclea el salario: ");
				salario = teclado.nextInt();
				teclado.nextLine();
				file.writeDouble(salario);
			} // fin for
			file.close();

		} catch (InputMismatchException e) {
			System.out.println("El dato debe ser numérico.\n ");
		} catch (FileNotFoundException e) {
			System.out.println(" ERROR NO ENCONTRADO EL FICHERO. ");
		} catch (IOException io) {
			System.out.println("ERROR DE E/S ");
		}

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
		}

	} // insertarregistros

////////////////////////////////////////////////////////////////////////////////////
	public static boolean comprobarsiexiste(int num) throws IOException {

		File fichero = new File(nombrefichero);
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		boolean retorno = false;
		if (file.length() <= 0) {
			System.out.println("**EL FICHERO ESTÄ VACÍO.");
			retorno = false;
		} else if (file.length() < (num * 36)) {
			System.out.println("**EL NUMERO DE EMPLEADO NO EXISTE, SOBREPASA LA LONGITUD..");
			retorno = false;
		} else {
			long pos = (num - 1) * 36;
			file.seek(pos);
			int nume = file.readInt();
			if (nume != num) {
				System.out.println("**EL NUMERO DE EMPLEADO NO EXISTE, ES UN HUECO.");
				retorno = false;
			} else // EXISTEE
				retorno = true;
		}
		file.close();
		return retorno;
	}// fin comprobarsiexiste

/////////////////////////////////////////////////////////////////////////////////////		 
	public static void consultar(int num) throws IOException {
		File fichero = new File(nombrefichero);
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		double salario;
		int pos, dep, nume;
		char aux;
		String nom = "";
		if (comprobarsiexiste(num)) {
			pos = (num - 1) * 36;
			file.seek(pos);
			nume = file.readInt();
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				nom = nom + aux;
			}
			dep = file.readInt();
			salario = file.readDouble();
			System.out.println("---------------------------------------------------------");
			System.out.println("------------- LOS DATOS DEL EMPLEADO --------------------");
			System.out.println("Empleado: " + nom + ", numero:" + num + ", dep: " + dep + ", salario: " + salario);
			System.out.println("---------------------------------------------------------");
		} else {
			System.out.println("----------------------------------------------------------------");
			System.out.println("EL NUMERO DE EMPLEADO " + num + " NO SE ENCUENTRA EN EL ARCHIVO.");
			System.out.println("----------------------------------------------------------------");
		}
		file.close();
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
		}
	}// fin consultar
////////////////////////////////////////////////////////////////////////////////////

	public static void subirelsalario(int num, double importe) throws IOException {
		File fichero = new File(nombrefichero);
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		double salario, nuevosalario;
		int pos, dep, nume;
		char aux;
		String nom = "";
		if (comprobarsiexiste(num)) {
			pos = (num - 1) * 36;
			file.seek(pos);
			nume = file.readInt();
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				nom = nom + aux;
			}
			dep = file.readInt();
			salario = file.readDouble();
			System.out.println("---------------------------------------------------------");
			System.out.println("------------ LOS DATOS DEL EMPELADO A ACTUALIZAR SON ----");
			System.out.println("Empleado: " + nom + ", numero:" + num + ", dep: " + dep + ", salario: " + salario);
			nuevosalario = salario + importe;
			System.out.println("Nuevo salario =  " + nuevosalario);
			System.out.println("---------------------------------------------------------");
			pos = ((num - 1) * 36) + 28;
			file.seek(pos);
			// nos colocamos en salario, 8 bytes atrás y actualizamos
			file.writeDouble(nuevosalario);
		} else {
			System.out.println("----------------------------------------------------------------");
			System.out.println("EL NUMERO DE EMPLEADO " + num + " NO SE ENCUENTRA EN EL ARCHIVO.");
			System.out.println("----------------------------------------------------------------");
		}
		file.close();
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
		}
	}// fin subirelsalario
////////////////////////////////////////////////////////////////////////////////////

	public static void visualizartodos() throws IOException {
		File fichero = new File(nombrefichero);
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		char cad[] = new char[10], aux;
		String nom;
		double salario;
		int pos, num, dep;
		if (file.length() > 0) {
			pos = 0;
			System.out.println(" ------------------------------------------------------------------------------");
			System.out.println(" ----- VISUALIZACIÓN DE DATOS. LECTURA SECUENCIAL. Se visualizan TODOS ------ ");
			for (;;) {
				file.seek(pos);
				num = file.readInt();
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();
					cad[i] = aux;
				}
				nom = new String(cad);
				dep = file.readInt();
				salario = file.readDouble();
				System.out.println("Empleado: " + nom + ", numero:" + num + ", dep: " + dep + ", salario: " + salario);
				pos = pos + 36;
				if (file.getFilePointer() == file.length())
					file.close();
					break; // fin de fichero
			} // fin for
			System.out.println(" ------------------------------------------\n");
		} else
			System.out.println(" ---------FICHERO VACIÍIOOOO --------------------\n");

		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
		}
		
		file.close();
	}// fin visualizartodos
////////////////////////////////////////////////////////////////////////////////////

	public static void visualizarsinhuecos() throws IOException {
		File fichero = new File(nombrefichero);
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		char cad[] = new char[10], aux;
		String nom;
		double salario;
		int pos = 0, num, dep;
		if (file.length() > 0) {
			System.out.println(" ----------------------------------------------------------------------");
			System.out.println(" ---------- VISUALIZACIÓN DE DATOS. NO SE VISUALIZAN LOS HUECOS -------\n ");
			for (;;) {
				// NO SE VISUALIZAN LOS HUECOS
				file.seek(pos);
				num = file.readInt(); // me desplazo 4 bytes
				if (num != 0) {
					for (int i = 0; i < cad.length; i++) {
						aux = file.readChar();
						cad[i] = aux;
					}
					nom = new String(cad);
					dep = file.readInt();
					salario = file.readDouble();
					System.out.println(
							"Empleado: " + nom + ", numero:" + num + ", dep: " + dep + ", salario: " + salario);
				}
				pos = pos + 36;
				if (file.getFilePointer() == file.length())
					file.close();
					break;
			}
			System.out.println(" ----------------------------------------------------------\n");
		} else
			System.out.println(" ---------FICHERO VACIÍIOOOO --------------------\n");
		file.close();
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
		}
	}// fin visualizartodos
////////////////////////////////////////////////////////////////////////////////////

	public static void crear() throws IOException {
		File fichero = new File(nombrefichero);
		if (fichero.exists()) {
			fichero.delete(); // borro el fichero y lo creo limpio
			fichero = new File(nombrefichero);
		}
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		String apellido[] = { "FERNANDEZ", "GIL", "LOPEZ", "RAMOS", "SEVILLA", "CASILLA", "REY" };
		int dep[] = { 10, 20, 10, 10, 30, 30, 20 };
		Double salario[] = { 1000.45, 2400.60, 3000.0, 1500.56, 2200.0, 1435.87, 2000.0 };

		StringBuffer buffer = null;
		int n = apellido.length;

		for (int i = 0; i < n; i++) {
			file.writeInt(i + 1);
			buffer = new StringBuffer(apellido[i]);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			file.writeInt(dep[i]);
			file.writeDouble(salario[i]);
		}
		file.close();
		System.out.println("\n----- FICHERO CREADO --------------");
		try {
			System.out.println("Pulsa una tecla para volver. ");
			int c = System.in.read();
		} catch (IOException e) {
		}
	}// fin crear

}// fin class