package menu;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import logica.Operaciones;

public class MenuApp {

	// Variable escanner que recoge las entradas del teclado
	private static Scanner teclado = new Scanner(System.in);

	// Opcion salida y opcion regenerar menu
	private static final int salir = 0;
	private static final int iniciar = -1;

	// Titulo menú
	private static final String titulo = "OPERACIONES PROYECTOS";

	// Array de constantes para generar dinamicamente el menu principal
	private static final String[] OPCIONES = { 
			"Crear BBDD.", 
			"Listar un proyecto.", 
			"Insertar participación.", 
			"Salir." }; 

	public static void main(String[] args) {
		crearConexion("PROYECTOS", "proyectos");
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

		int opcion;

		do {
			opcion = iniciar;

			//imprimirMenu(); Cambiar valor variable "salir" 
			imprimirMenuSalirCero();

			// Solicita la opción
			printSelection();

			// Salvamos error por entrada distinta a un número
			opcion = analizarValorDado();
			System.out.println();

			switch (opcion) {
			case 1:
				Operaciones.crearBBDD();
				break;
			case 2:
				Operaciones.listarProyecto(3);    // Existe proyecto y tiene participantes
				//Operaciones.listarProyecto(30); // No existe proyecto
				//Operaciones.listarProyecto(5);  // Existe proyectos, no tienen particpantes
				break;
			case 3:
				Operaciones.insertarParticipacion(5, 23, "Ayudante nivel 2", 3);
				//Operaciones.insertarParticipacion(19, 23, "Ayudante nivel 2", 3); // No existe proyecto
				//Operaciones.insertarParticipacion(5, 24, "Ayudante nivel 2", 3); // No existe estudiante
				break;
			case 4:

				System.out.println();
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

			if (i == OPCIONES.length-1) {
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

	
	private static void crearConexion(String usuario, String password)   {

		try {

			Class.forName("oracle.jdbc.OracleDriver");

			Operaciones.setConexion(DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", usuario, password));
			
			//System.out.println("Hay conexion");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}

	}
}
