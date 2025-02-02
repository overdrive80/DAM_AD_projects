package menu;

import java.util.Scanner;

import logica.Operaciones;

public class MenuApp {

	// Variable escanner que recoge las entradas del teclado
	protected static Scanner teclado = new Scanner(System.in);
	protected Operaciones operaciones;

	// Opcion salida y opcion regenerar menu
	protected static final int salir = 0;
	protected static final int iniciar = -1;

	// Titulo menú
	private static final String titulo = "Tarea 5. Realizada por: Israel Lucas Torrijos";

	// Array de constantes para generar dinamicamente el menu principal
	private static final String[] OPCIONES = { "Subir colecciones a exist (ColeccionPruebas, ColeccionVentas)",
			"Realizar ejercicio 1", "Realizar ejercicio 2", "Salir." };

	/**
	 * Constructor
	 */
	public MenuApp() {

	}

	/**
	 * Constructor
	 */
	public MenuApp(Operaciones ope) {
		this.operaciones = ope;
	}

	public Operaciones getOperaciones() {
		return operaciones;
	}

	public void setOperaciones() {
		if (operaciones == null) {
			this.operaciones = new Operaciones();
		}
	}

	public void mostrarMenu() {

		int opcion;

		do {
			opcion = iniciar;

			// imprimirMenu(); Cambiar valor variable "salir"
			imprimirMenuSalirCero();

			// Solicita la opción
			printSelection();

			// Salvamos error por entrada distinta a un número
			opcion = analizarValorDado();
			System.out.println();

			switch (opcion) {
			case 1:
				operaciones.ejercicio0();
				break;
			case 2:
				operaciones.ejercicio1();
				break;
			case 3:
				SubMenu submenu = new SubMenu(this);
				submenu.mostrarMenu();
				break;
			case salir:
				System.exit(0);
				break;
			default:
				opcionIncorrecta();
				break;

			}

			pause();

		} while (opcion != salir);

	}

	//////////////////// METODOS PARA EL MENU ////////////////////

	/**
	 * Método sin valor de retorno que imprime las opciones del menu
	 */
	protected void imprimirMenu() {

		System.out.println(titulo);

		for (int i = 0; i < OPCIONES.length; i++) {

			System.out.println("  " + (i + 1) + ". " + OPCIONES[i]);
		}

		System.out.println();
	}

	/**
	 * Método sin valor de retorno que imprime las opciones del menu
	 */
	protected void imprimirMenuSalirCero() {

		System.out.println(titulo);

		for (int i = 0; i < OPCIONES.length; i++) {

			if (i == OPCIONES.length - 1) {
				System.out.println("  0. " + OPCIONES[i]);
			} else {
				System.out.println("  " + (i + 1) + ". " + OPCIONES[i]);
			}
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
		int opcion;

		// Las opciones solo admiten valor númerico
		try {
			opcion = teclado.nextInt();
			teclado.nextLine();
		} catch (java.util.InputMismatchException e) // error con otros caracteres
		{
			teclado.nextLine();
			return iniciar;
		}
		return opcion;
	}

	protected int analizarCaracterDado() {
		char  opcion;

		// Las opciones solo admiten valor númerico
		try {
			opcion = teclado.nextLine().toLowerCase().charAt(0);
			//teclado.nextLine();
		} catch (StringIndexOutOfBoundsException e) // error con otros caracteres
		{
			teclado.nextLine();
			return iniciar;
		}

		switch (opcion) {
		case 'a':
			return 1;
		case 'b':
			return 2;
		case 'c':
			return 3;
		case 'd':
			return 4;
		case '0':
			return 0;
		default:
			return iniciar;
		}
	}
}
