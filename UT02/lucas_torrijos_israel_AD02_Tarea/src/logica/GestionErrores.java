package logica;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

public class GestionErrores {


	public static void controlErroresSQL(SQLException e) {

		int codError = e.getErrorCode();

		// Controlamos segun el codigo de error lanzado
		switch (codError) {
		case 1049:
			Registro.append("La base de datos no existe." + " (" + e.getMessage() + ")");
			break;
		case 1017:
			Registro.append("Conexión denegada. El nombre de usuario/contraseña es incorrecto." + " (" + e.getErrorCode()
					+ ")");
			break;
		default:
			Registro.append(e.getMessage() + " (" + codError + ")");
		}

	}

	public static void controlErroresGenericos(Exception e) {

		if (e instanceof NullPointerException) {

			Registro.append("No se ha encontrado el driver.");

		} else {

			Registro.append(e.getMessage() + " (" + e.getCause() + ")");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			Registro.append(sw.toString());

		}

	}

}
