package ejemplo;

public class Casting_Primitivos {

	/*
	 * Aunque las conversiones primitivas y la conversión de variables de referencia
	 * pueden parecer similares, son conceptos bastante diferentes. En ambos casos,
	 * estamos "convirtiendo" un tipo en otro. Pero, de manera simplificada, una
	 * variable primitiva contiene su valor, y la conversión de una variable
	 * primitiva significa cambios irreversibles en su valor:
	 */

	public static void main(String[] args) {
		// Casting
		// Downcasting. Debe ser explicito
		double miDecimal = 1.1;
		int miEntero = (int) miDecimal;

		//Upcasting implicito
		miEntero = 5;
		miDecimal = miEntero;

		//Upcasting explicito
		miDecimal = (double) miEntero;
	}

}
