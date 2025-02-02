package menu;

import ejercicios.Ejercicio02;

public class SubMenu extends MenuApp {
	
	// Titulo menú
	private static final String titulo = "Ejercicio 2. Operaciones con Colección Ventas";
	private MenuApp menuPrincipal;
	private Ejercicio02 ejercicio;
	
	// Array de constantes para generar dinamicamente el menu principal
	private static final String[] OPCIONES = { 
			"Actualizar Facturas.", 
			"Insertar en DetalleFacturas.", 
			"Ver en una factura.", 
			"Ver detalles de una factura.",
			"Volver." }; 
	
	public SubMenu(MenuApp menuapp) {

		this.menuPrincipal = menuapp;
		ejercicio = menuapp.getOperaciones().ejercicio2();
		
	}
	
	public void mostrarMenu() {

		int opcion;

		do {
			opcion = iniciar;

			//imprimirMenu(); Cambiar valor variable "salir" 
			imprimirSubMenuSalirCero();

			// Solicita la opción
			printSelection();

			// Salvamos error por entrada distinta a un número
			opcion = analizarCaracterDado();
			System.out.println();

			switch (opcion) {
			case 1:
				ejercicio.apartadoA();
				break;
			case 2:
				ejercicio.apartadoB();
				break;
			case 3:
				ejercicio.apartadoC();
				break;
			case 4:
				ejercicio.apartadoD();
				break;
			case salir:
				menuPrincipal.mostrarMenu();
				break;
			default:
				opcionIncorrecta();
				break;

			}

			pause();

		} while (opcion != salir);

	}

	/**
	 * Método sin valor de retorno que imprime las opciones del menu
	 */
	private void imprimirSubMenuSalirCero() {

		System.out.println(titulo);

		for (int i = 0; i < OPCIONES.length; i++) {

			if (i == OPCIONES.length-1) {
				System.out.println("  0. " + OPCIONES[i]);
			} else {
				System.out.println("  " + (char)(97 + i) + ". " + OPCIONES[i]);
			}
		}

		System.out.println();
	}
	

}
