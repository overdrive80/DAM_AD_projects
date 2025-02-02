package menu;

import java.util.Scanner;

import gestiondatos.GestionDatos;

public class MenuApp {

	// Variable escanner que recoge las entradas del teclado
	private static Scanner teclado = new Scanner(System.in);

	// Array de constantes para generar dinamicamente el menu principal
	private static final String[] OPCIONES = { 
			"Listar el fichero notas con nombre de alumno y nombre de asignatura.", // CASO 1
			"Actualizar el fichero asignaturas.dat y mostrar mensajes.", // CASO 2
			"Visualizar los datos del fichero asignaturas.dat", // CASO 3
			"Generar el XML asignaturas.xml y mostrar datos", // CASO 4
			"Salir" }; // CASO FINAL

	private static final GestionDatos factoria = new GestionDatos();
	
	public static void main(String[] args) {
		
		new MenuApp();
	}
	
	/**
	 * Constructor
	 */
	public MenuApp() {
		mostrarMenu();
	}

	/**
	 * Método sin valor de retorno que inicia el proceso de la app e invoca la
	 * construcción del menu
	 */

	private void mostrarMenu() {

		int opcion = 0;

		do {

			imprimirMenu();

			// Solicita la opción
			printSelection();

			// Salvamos error por entrada distinta a un número
			opcion = analizarValorDado();
			System.out.println();

			switch (opcion) {
			case 1:
				factoria.listarFicheroNotas();
				break;
			case 2:
				factoria.modificarContenidoAsignaturas();
				break;
			case 3:
				factoria.verContenidoAsignaturasMedia(false);
				break;
			case 4:
				factoria.exportarXML();
				System.out.println();
				break;
			case 5:
				System.exit(0);
				break;
			default:
				opcionIncorrecta();
				break;

			}

			pause();
			opcion = 0;

		} while (opcion != 5);

	}

	//////////////////// METODOS PARA EL MENU ////////////////////
	
	/**
	 * Método sin valor de retorno que imprime las opciones del menu
	 */
	protected void imprimirMenu() {

		System.out.println("OPERACIONES CON ALUMNOS");

		for (int i = 0; i < OPCIONES.length; i++) {

			System.out.println("  " + (i + 1) + ". " + OPCIONES[i]);
		}

		System.out.println();
	}

	/**
	 * Método sin valor de retorno que solicita la opción del usuario
	 */
	protected void printSelection() {
		System.out.print("Introduzca una opción: ");// Pedimos la opcion variable
	}

	/**
	 * Método que imprime el mensaje de opción incorrecta en la selección
	 */
	protected void opcionIncorrecta() {
		System.out.print("Opción incorrecta. ");
		// pause();
	}

	/**
	 * Metodo que permite crear una pausa en el sistema.
	 */
	protected void pause() {
		System.out.println("Pulse enter para continuar.");
		teclado.nextLine();
	}

	/**
	 * Método que permite comprobar si el valor insertado es númerico en aquellas
	 * preguntas en las que es necesario que el usuario sólo inserte caracteres
	 * numéricos.
	 * 
	 * @return int Devuelve un valor entero que permite saber la opcion elegida por
	 *         el usuario. Si no es un número devuelve cero que nunca sera un valor
	 *         admitido como opción.
	 */
	protected int analizarValorDado() {
		int opcion = 0;

		// Las opciones solo admiten valor númerico
		try {
			opcion = teclado.nextInt();
			teclado.nextLine();
		} catch (java.util.InputMismatchException e) // error con otros caracteres
		{
			teclado.nextLine();
			return 0;
		}
		return opcion;
	}

	
}
