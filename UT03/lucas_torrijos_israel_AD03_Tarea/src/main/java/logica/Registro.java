package logica;

import javax.swing.JTextArea;

public class Registro {

	private static JTextArea areaTexto = new JTextArea();
	public static final String sepLineas = "-".repeat(110);
	private static StringBuilder logging = new StringBuilder();

	public static void setLogger(JTextArea logger) {

		areaTexto = logger;
	}

	private static void reiniciarLogging() {
		logging.setLength(0);
	}

	public static void append(String texto) {

		// Primero lo reiniciamos a 0
		reiniciarLogging();


		// Recuperamos el valor que tenía
		logging.append(areaTexto.getText());


		// Añadimos el texto indicado
		logging.append(texto).append("\n");


		// Y ahora insertamos el texto actualizado
		areaTexto.setText(logging.toString());

	}

	public static void append(String texto, boolean salto) {

		logging.append(texto);

		if (salto) {
			logging.append("\n");
		}

		areaTexto.setText(logging.toString());
	}
	
	public static void clear() {
		areaTexto.setText("");
	}

	public static StringBuilder getLogging() {
		return logging;
	}

}
