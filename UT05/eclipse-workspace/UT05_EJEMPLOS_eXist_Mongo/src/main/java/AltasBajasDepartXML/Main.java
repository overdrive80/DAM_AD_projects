package AltasBajasDepartXML;
/*
 * ALTAS BAJAS Y MODIFICACION DE DEPARTAMENTOS
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	public static void main(String[] args) {

		final Pantalla ventana = new Pantalla();
		ventana.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// AQUI VA EL PROCESO A REALIZAR
				// CUANDO SE CIERRA LA VENTANA
				System.exit(0);
			}
		});
		ventana.iniciar();
	}
}// fin de la clase Main
