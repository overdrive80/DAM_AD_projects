package main;

import logica.Operaciones;
import menu.MenuApp;

public class Main {

	public static void main(String[] args) {

		Operaciones operaciones = new Operaciones();
		MenuApp menuPrincipal = new MenuApp(operaciones);
		menuPrincipal.mostrarMenu();

	}

}
