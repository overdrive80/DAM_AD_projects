package gestiondatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import clases.Alumno;
import clases.Asignatura;
import clases.Secretaria;
import gestionXML.GenerarXML;

/**
 * Clase que centraliza la gestión de los datos solicitados en el ejercicio
 */

public class GestionDatos {

	private static final String rutaDatos = System.getProperty("user.dir") + File.separator + "src\\main\\resources";

	private static final String archivoXML = "./asignaturas.xml";

	// Estructura de los archivos de datos
	private static final String strAlumnos = "alumnos.dat";
	private static final int regAlumno = 68;
	private static final String strAsignaturas = "asignaturas.dat";
	private static final int regAsigna = 42;
	private static final String strNotas = "notas.dat";
	private static final int regNotas = 12;
	private static final int intNombreAsigna = 15;
	private static final int intNombreAlumno = 20;
	private static final int intCursoAlumno = 10;

	// Archivos que apuntan a los dat
	private File alumnos, asignaturas, notas;

	public GestionDatos() {
		super();
		boolean mostrarArchivos = false;
		Locale.setDefault(Locale.US);

		alumnos = new File(rutaDatos, strAlumnos);
		asignaturas = new File(rutaDatos, strAsignaturas);
		notas = new File(rutaDatos, strNotas);

		if (mostrarArchivos == true) {
			verContenidoAlumnos(false);
			verContenidoAsignaturas(false);
			verContenidoNotas(true);

		}
	}

	/**
	 * Método que determina si existe un archivo en el sistema de archivos del OS
	 * 
	 * @param strDirectorio
	 * @param strNombre
	 * @return boolean
	 */
	protected boolean existeArchivo(String strDirectorio, String strNombre) {

		// Si el directorio es nulo tomamos la ruta actual
		if (strDirectorio == null) {
			strDirectorio = System.getProperty("user.dir");
		}

		File directorio = new File(strDirectorio);
		File file = new File(directorio, strNombre);

		if (file.exists()) {

			return true;
		}

		return false;

	}

	/***************************************
	 * METODOS ARCHIVOS ALEATORIOS
	 ********************************/
	/**
	 * Método que devuelve una posición en base cero
	 * 
	 * @param clave
	 * @param medidaReg
	 * @return int
	 */
	private int getPosicion(int clave, int medidaReg) {

		if (clave <= 0) {
			clave = 0;
		}

		return (clave - 1) * medidaReg;
	}

	/////////////////////////////////////////// METODOS DE CONSULTA
	/////////////////////////////////////////// ///////////////////////////////////////////////
	/**
	 * Método para realizar la lectura secuencial de un archivo binario
	 * 
	 * @param medidaReg Este de tipo enterio y representa el tamaño en bytes de cada
	 *                  registro
	 */
	private void verContenidoAlumnos(boolean mostrarHuecos) {

		int codAlumno;
		String nombre, curso;
		float notaMedia;

		// Enlazamos con el archivo
		try {
			RandomAccessFile raf = new RandomAccessFile(alumnos, "r");

			// Encabezado de la tabla
			System.out.printf("%-8s %-20s %-11s %-11s%n", "Cód Alum", "Nombre", "Curso", "Nota Media");
			System.out.printf("%-8s %-20s %-11s %-11s%n", "--------", "--------------------", "-----------",
					"----------");

			// Recorremos el archivo
			for (int i = 0; raf.getFilePointer() != raf.length(); i++) {

				// Nos posicionamos al principio de cada registro
				raf.seek(i * regAlumno);

				// Comprobamos si el registro está vacio en caso de que el codAlumno sea 0
				codAlumno = raf.readInt();

				if (mostrarHuecos || codAlumno != 0) {
					nombre = leerCadena(raf, intNombreAlumno);
					curso = leerCadena(raf, intCursoAlumno);
					notaMedia = raf.readFloat();

					// Imprimimos el contenido por pantalla
					System.out.printf("%8d %16s %10s %11.1f%n", codAlumno, nombre, curso, notaMedia);
				}
			}
			System.out.println();

			// Cerramos el archivo
			raf.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para realizar la lectura secuencial de un archivo binario
	 * 
	 * @param mostrarHuecos boolean Indicamos por parametros si queremos mostrar
	 *                      huecos
	 */
	private void verContenidoAsignaturas(boolean mostrarHuecos) {

		int codAsigna, numAlumnos;
		String nombre;
		float sumaNotas;

		// Enlazamos con el archivo
		try {
			RandomAccessFile raf = new RandomAccessFile(asignaturas, "r");

			// Encabezado de la tabla
			System.out.printf("%-8s %-15s %-11s %-14s%n", "Cód Asig", "Nombre", "Num alumnos", "Suma de Notas");
			System.out.printf("%-8s %-15s %-11s %-14s%n", "--------", "---------------", "-----------",
					"-------------");

			// Recorremos el archivo
			for (int i = 0; raf.getFilePointer() != raf.length(); i++) {

				// Nos posicionamos al principio de cada registro
				raf.seek(i * regAsigna);

				codAsigna = raf.readInt();

				if (mostrarHuecos || codAsigna != 0) {
					nombre = leerCadena(raf, intNombreAsigna);
					numAlumnos = raf.readInt();
					sumaNotas = raf.readFloat();

					// Imprimimos el contenido por pantalla
					System.out.printf("%8d %15s %11s %13.1f%n", codAsigna, nombre, numAlumnos, sumaNotas);
				}
			}
			System.out.println();

			// Cerramos el archivo
			raf.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para realizar la lectura secuencial de un archivo binario
	 * 
	 * @param medidaReg Este de tipo enterio y representa el tamaño en bytes de cada
	 *                  registro
	 */
	private void verContenidoNotas(boolean mostrarHuecos) {

		int codAlumno, codAsigna;
		float nota;

		// Enlazamos con el archivo
		try {
			RandomAccessFile raf = new RandomAccessFile(notas, "r");

			// Encabezado de la tabla
			System.out.printf("%-10s %-8s %5s%n", "Cód Alumno", "Cód Asig", "Nota");
			System.out.printf("%-10s %-8s %-5s%n", "----------", "--------", "-----");

			// Recorremos el archivo
			for (int i = 0; raf.getFilePointer() != raf.length(); i++) {

				// Nos posicionamos al principio de cada registro
				raf.seek(i * regNotas);

				codAlumno = raf.readInt();
				codAsigna = raf.readInt();

				// Existe una clave compuesta que determina si hay huecos o no.
				if (mostrarHuecos || (codAlumno != 0 && codAsigna != 0)) {

					nota = raf.readFloat();

					// Imprimimos el contenido por pantalla
					System.out.printf("%10d %8d %5.1f%n", codAlumno, codAsigna, nota);
				}
			}
			System.out.println();

			// Cerramos el archivo
			raf.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método que lee una cadena de datos binarios
	 * 
	 * @param archivo
	 * @param longitud
	 * @return String
	 * @throws IOException
	 */
	private String leerCadena(RandomAccessFile archivo, int longitud) throws IOException {

		char cad[] = new char[longitud], aux;
		// String s = "";

		for (int i = 0; i < cad.length; i++) {
			aux = archivo.readChar();

			// Si se usa null para rellenar lo convertimos en espacio
			if (aux == 0) {
				aux = ' ';
			}
			cad[i] = aux;
			// s = s + aux;
		}

		return new String(cad);
		// return s;
	}

	@SuppressWarnings({ "resource" })
	/**
	 * Método que determina si existe un registro en el archivo pasado por argumento
	 * 
	 * @param archivo
	 * @param numRegistro
	 * @param magnitudReg
	 * @return boolean
	 */
	private boolean existeRegistro(File archivo, int numRegistro, int magnitudReg) {

		try {
			RandomAccessFile raf = new RandomAccessFile(archivo, "r");

			// Si no tiene contenido
			if (raf.length() <= 0) {
				System.out.println("El archivo está vacío.");
				return false;
			}

			// Si la longitud del archivo es menor al tamaño del registro
			if (raf.length() < (numRegistro * magnitudReg)) {
				// System.out.println("El número de registro no existe.");
				return false;

			}

			// Comprobamos si es un hueco
			int pos = getPosicion(numRegistro, magnitudReg);

			raf.seek(pos);
			int codigo = raf.readInt();

			if (codigo != numRegistro) {
				/*
				 * System.out.println("El número de registro no existe y se encontró un hueco."
				 * );
				 */
				return false;
			}

			raf.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return true;
	}

	/////////////////////////////////////////// METODOS DE MODIFICACION
	/////////////////////////////////////////// ///////////////////////////////////////////////
	/**
	 * Método para incrememtar en 1 el contador de alumnos de asignaturas.dat
	 * 
	 * @param numRegistro
	 */
	private void incrementarAlumnoAsignatura(int numRegistro) {
		int colNumAlumnoAsign = 34;
		int numAlumnos;

		try {
			RandomAccessFile raf = new RandomAccessFile(asignaturas, "rw");

			int pos = getPosicion(numRegistro, regAsigna);

			// Buscamos el registro en asignaturas.dat
			boolean existeReg = existeRegistro(asignaturas, numRegistro, regAsigna);

			if (existeReg) {

				raf.seek(pos + colNumAlumnoAsign);
				numAlumnos = raf.readInt();
				raf.seek(pos + colNumAlumnoAsign);

				raf.writeInt(numAlumnos + 1);
				// raf.writeInt(0);
			}

			raf.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Método para incrementar el contador de notas de cada asignatura
	 * 
	 * @param numRegistro
	 * @param nota
	 */
	private void incrementarSumaNotasAsignatura(int numRegistro, double nota) {
		int colNotasAsign = 38;

		float sumaNotas;

		try {
			RandomAccessFile raf = new RandomAccessFile(asignaturas, "rw");

			int pos = getPosicion(numRegistro, regAsigna);

			// Buscamos el registro en asignaturas.dat
			boolean existeReg = existeRegistro(asignaturas, numRegistro, regAsigna);

			if (existeReg) {

				raf.seek(pos + colNotasAsign);
				sumaNotas = raf.readFloat();
				raf.seek(pos + colNotasAsign);

				raf.writeFloat(sumaNotas + (float) nota);
				// raf.writeFloat(0 );
			}

			raf.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Método que pone a cero el valor de los contadores de alumnos y notas de
	 * asignaturas.dat
	 */
	private void ponerCeroAsignatura() {
		int colNotasAsign = 38;
		int colNumAlumnoAsign = 34;

		try {
			RandomAccessFile raf = new RandomAccessFile(asignaturas, "rw");

			for (int i = 0; raf.getFilePointer() != raf.length(); i++) {

				// Obtenemos la posicion al inicio de cada registro
				int pos = i * regAsigna;

				// Nos situamos en el campo Num Alumno
				raf.seek(pos + colNumAlumnoAsign);

				raf.writeInt(0);

				// Nos situamos en el campo Num Alumno
				raf.seek(pos + colNotasAsign);

				raf.writeInt(0);

			}

			raf.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/*
	 * Método que calcula la media
	 * 
	 * @param num, den. Numerador y denominador.
	 * 
	 * @return float
	 */
	private float getMedia(float num, float den) {
		// Calculo de nota media
		if (den != 0) {
			return (float) num / den;
		}

		return 0;

	}

	/* METODOS CON LA SOLUCION A LOS EJERCICIOS */
	/**************************************************
	 * EJERCICIO 1
	 **************************************************/
	/**
	 * Procedimiento que muestra las el fichero notas. Resulta ser una tabla
	 * intermedia de relación entre dos tablas: alumnos y asignaturas En este método
	 * hay que interrelacionar la información para mostrarla en pantalla
	 */
	public void listarFicheroNotas() {

		File alumnos = new File(rutaDatos, strAlumnos);
		File asignaturas = new File(rutaDatos, strAsignaturas);
		File notas = new File(rutaDatos, strNotas);

		int codAlumno, codAsigna;
		float nota;
		String nomAlumno, nomAsigna;
		int byteCodigo = 4;

		try {
			RandomAccessFile rafAlumnos = new RandomAccessFile(alumnos, "r");
			RandomAccessFile rafAsignaturas = new RandomAccessFile(asignaturas, "r");
			RandomAccessFile rafNotas = new RandomAccessFile(notas, "r");

			// Encabezado de la tabla
			System.out.printf("%-10s %-20s %-8s %-15s %5s%n", "Cód Alumno", "Nombre alum", "Cód Asig", "Nombre asi",
					"Nota");
			System.out.printf("%-10s %-20s %-8s %-15s %5s%n", "----------", "--------------------", "--------",
					"---------------", "-----");

			for (int i = 0; rafNotas.getFilePointer() != rafNotas.length(); i++) {

				// Nos posicionamos al principio del archivo
				rafNotas.seek(i * regNotas);

				// Mostramos todo el contenido aunque haya huecos
				// Indice compuesto
				codAlumno = rafNotas.readInt();
				codAsigna = rafNotas.readInt();
				nota = rafNotas.readFloat();

				// Buscamos el registro en alumnos.dat
				boolean existeReg = existeRegistro(alumnos, codAlumno, regAlumno);

				// Existe alumno
				if (existeReg) {
					rafAlumnos.seek(getPosicion(codAlumno, regAlumno) + byteCodigo);
					nomAlumno = leerCadena(rafAlumnos, intNombreAlumno);
				} else {

					nomAlumno = "SIN ALUMNO";
				}

				// Buscamos el registro en asignaturas.dat
				existeReg = existeRegistro(asignaturas, codAsigna, regAsigna);

				// Existe asignatura
				if (existeReg) {
					rafAsignaturas.seek(getPosicion(codAsigna, regAsigna) + byteCodigo);
					nomAsigna = leerCadena(rafAsignaturas, intNombreAsigna);
				} else {

					nomAsigna = "SIN ASIGNATURA";
				}

				// Imprimimos el contenido por pantalla
				System.out.printf("%10d %-20s %8d %-15s %5.1f%n", codAlumno, nomAlumno, codAsigna, nomAsigna, nota);

			}

			System.out.println();

			// Creamos los archivos
			rafAlumnos.close();
			rafAsignaturas.close();
			rafNotas.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**************************************************
	 * EJERCICIO 2
	 **************************************************/
	/**
	 * Procedimiento que actualiza y muestra un registro de acciones sobre el
	 * archivo asignaturas.dat
	 * 
	 */
	public void modificarContenidoAsignaturas() {

		int codAlumno, codAsigna;
		float nota;
		boolean bActualiza;

		// Enlazamos con el archivo
		try {
			RandomAccessFile raf = new RandomAccessFile(notas, "r");

			// Recorremos el archivo notas.dat
			for (int i = 0; raf.getFilePointer() != raf.length(); i++) {

				System.out.println("------------------------------------------------------------------");

				// Indicamos que se va a actualizar
				bActualiza = true;

				// Nos posicionamos al principio de cada registro
				raf.seek(i * regNotas);

				codAlumno = raf.readInt();
				codAsigna = raf.readInt();
				nota = raf.readFloat();

				System.out.printf("Registro de la asignatura=%d, cod_alumno=%d, nota=%2.1f%n", codAsigna, codAlumno,
						nota);

				// Buscamos el registro en asignaturas.dat. Si no existe no actualizamos
				boolean existeReg = existeRegistro(asignaturas, codAsigna, regAsigna);

				if (!existeReg) {
					System.out.printf("Error en el código asignatura, sobrepasa: %4d%n", codAsigna);
					bActualiza = false;
				}

				// Buscamos el registro en alumnos.dat. Si no existe no actualizamos
				existeReg = existeRegistro(alumnos, codAlumno, regAlumno);

				if (!existeReg) {
					System.out.printf("Error en el código alumno, sobrepasa: %4d%n", codAlumno);
					bActualiza = false;
				}

				// Si no hay que actualizar pasamos al siguiente registro.
				if (bActualiza == false) {
					System.out.println("NO SE ACTUALIZA");

				} else {
					/*
					 * En el caso de tengamos que actualizar ponemos a cero los valores de la tabla
					 * para evitar que siga creciendo de forma erronea
					 */
					if (i == 0) {
						ponerCeroAsignatura(); }

						System.out.printf("Se suma la nota %2.1f y se incrementa en 1 el contador de la asignatura: %d%n", nota, codAsigna);
						System.out.println("SE ACTUALIZA");
						incrementarAlumnoAsignatura(codAsigna);
						incrementarSumaNotasAsignatura(codAsigna, nota);
					
				}

			}
			System.out.println("------------------------------------------------------------------\n");

			// Cerramos el archivo
			raf.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**************************************************
	 * EJERCICIO 3
	 **************************************************/
	/**
	 * Procedimiento para mostrar el contenido de la asignaturas.dat con la nota
	 * media
	 * 
	 */

	public void verContenidoAsignaturasMedia(boolean mostrarHuecos) {

		int codAsigna, numAlumnos;
		String nombre;
		float sumaNotas, notaMedia;

		// Enlazamos con el archivo
		try {
			RandomAccessFile raf = new RandomAccessFile(asignaturas, "r");

			// Encabezado de la tabla
			System.out.printf("%-8s %-15s %-11s %-14s %-9s%n", "Cód Asig", "Nombre", "Num alumnos", "Suma de Notas",
					"Nota Media");
			System.out.printf("%-8s %-15s %-11s %-14s %-9s%n", "--------", "---------------", "-----------",
					"-------------", "----------");

			// Recorremos el archivo asignaturas
			for (int i = 0; raf.getFilePointer() != raf.length(); i++) {

				// Nos posicionamos al principio de cada registro
				raf.seek(i * regAsigna);

				codAsigna = raf.readInt();

				if (mostrarHuecos || codAsigna != 0) {
					nombre = leerCadena(raf, intNombreAsigna);
					numAlumnos = raf.readInt();
					sumaNotas = raf.readFloat();

					notaMedia = getMedia(sumaNotas, numAlumnos);

					// Imprimimos el contenido por pantalla
					System.out.printf("%8d %15s %11s %13.1f %8.2f%n", codAsigna, nombre, numAlumnos, sumaNotas,
							notaMedia);
				}
			}
			System.out.println();

			// Cerramos el archivo
			raf.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**************************************************
	 * EJERCICIO 4
	 **************************************************/
	/**
	 * Método que permite exportar y mostrar en pantalla el contenido del XML
	 * generado
	 * 
	 */
	public void exportarXML() {

		int codAsigna, numAlumnos;
		String nombreAsigna;
		float sumaNotas;

		// Creamos el objeto Secretaria que contiene el ArrayList de asignaturas
		Secretaria secre = new Secretaria();

		/* Recorremos el archivo asignaturas.dat */
		try {
			RandomAccessFile rafAsignaturas = new RandomAccessFile(asignaturas, "r");

			for (int i = 0; rafAsignaturas.getFilePointer() != rafAsignaturas.length(); i++) {
				// Nos posicionamos al principio de cada registro
				rafAsignaturas.seek(i * regAsigna);

				codAsigna = rafAsignaturas.readInt();

				// Saltamos los huecos
				if (codAsigna != 0) {
					nombreAsigna = leerCadena(rafAsignaturas, intNombreAsigna).trim();
					numAlumnos = rafAsignaturas.readInt();
					sumaNotas = rafAsignaturas.readFloat();

					Asignatura nuevaAsignatura = new Asignatura(codAsigna, nombreAsigna, numAlumnos, sumaNotas);

					// Ahora una llamada a un método que pasandole el código de asignatura nos
					// devuelva un array de alumnos
					nuevaAsignatura.setAlumnos(getListaAlumnos(codAsigna));

					// Añadimos la asignatura al objeto Secretaría
					secre.getAsignaturas().add(nuevaAsignatura);

				}
			}

			rafAsignaturas.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Esto es siempre lo mismo se puede agrupar en un método de clase
		GenerarXML.run(Secretaria.class, secre, archivoXML);
	}

	/**
	 * Método que retorna una lista de alumnos de una clase en específico
	 * 
	 * @param codAsignatura
	 * @return List<Alumno>
	 */
	private List<Alumno> getListaAlumnos(int codAsignatura) {
		List<Alumno> listaAlumnos = new ArrayList<Alumno>();

		File alumnos = new File(rutaDatos, strAlumnos);
		File notas = new File(rutaDatos, strNotas);

		int codAlumno, codAsigna, offsetBytes = 4;
		String nomAlumno, cursoAlumno;

		// No comprobamos si existe la asignatura ya que llamamos a este método
		// pasando un número de asignatura, extraido de la lista de asignaturas.
		try {
			RandomAccessFile rafAlumnos = new RandomAccessFile(alumnos, "r");
			RandomAccessFile rafNotas = new RandomAccessFile(notas, "r");

			// Con esta condición de terminación nos aseguramos que lea registros enteros
			for (int i = 0; rafNotas.getFilePointer() + regNotas <= rafNotas.length(); i++) {
				// System.out.printf("iteracion: %d, posicion: %d, tamaño: %d%n", i,
				// rafNotas.getFilePointer(), rafNotas.length());
				// Nos posicionamos al principio del archivo
				rafNotas.seek(i * regNotas);

				// Leemos el código de alumno y asignatura
				codAlumno = rafNotas.readInt();
				codAsigna = rafNotas.readInt();

				// Si coincide con el valor del parámetro aplicamos la lógica
				if (codAsignatura == codAsigna) {
					// Buscamos el registro en alumnos.dat
					boolean existeReg = existeRegistro(alumnos, codAlumno, regAlumno);

					// Si existe alumno en alumnos.dat
					if (existeReg) {
						// Leemos el nombre
						rafAlumnos.seek(getPosicion(codAlumno, regAlumno) + offsetBytes);
						nomAlumno = leerCadena(rafAlumnos, intNombreAlumno).trim();

						// Leemos el curso
						cursoAlumno = leerCadena(rafAlumnos, intCursoAlumno).trim();
						Alumno alumno = new Alumno(codAlumno, nomAlumno, cursoAlumno);

						listaAlumnos.add(alumno);
					}
				}

			}

			// Cerramos los archivos
			rafAlumnos.close();
			rafNotas.close();

			// } catch (EOFException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaAlumnos;

	}
}