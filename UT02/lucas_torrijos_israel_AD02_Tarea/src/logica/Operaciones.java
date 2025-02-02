package logica;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import gui.Main_lucas_torrijos_israel_AD02_Tarea;
import gui.VentanaTablas;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Operaciones {

	private Connection conexion;
	private Main_lucas_torrijos_israel_AD02_Tarea formulario;
	private JTextField nombre;
	private JTextField direccion;
	private JTextField telefono;
	private JComboBox<String> proyecto;
	private final String sinConexion = "No hay conexión con la base de datos.";
	private String strNombre, strDireccion, strTelefono, fecha;

	public Operaciones(Main_lucas_torrijos_israel_AD02_Tarea formulario) {
		this.formulario = formulario;
	}

	private boolean existeConexion() {

		if (conexion == null) {
			Registro.append(Registro.sepLineas);
			Registro.append(sinConexion);
			return false;
		}

		return true;
	}
	
	public void setConexion() {
		
		this.conexion = formulario.getConexion();
	}

	/********************************************
	 * EJERCICIO 1
	 *********************************************/
	public void InsertarAlumno() {

		// Variables de objeto formulario
		nombre = formulario.getTxtNombre();
		direccion = formulario.getTxtDireccion();
		telefono = formulario.getTxtTelefono();

		// Variables de texto de los objetos del formulario
		strNombre = nombre.getText();
		strDireccion = direccion.getText();
		strTelefono = telefono.getText();
		fecha = formulario.getTxtFecAlta().getText();

		// Comprobamos que exista conexión
		if (!existeConexion()) {
			return;
		}

		Registro.append(Registro.sepLineas);

		try {
			// Primero. Comprobamos que los campos tienen algún valor. Si no hay valores
			// volvemos.
			if (!hayValoresCampos())
				return;

			// Segundo. En el caso de que los campos excedan de tamaño se recortan
			adaptarValores();

			// Tercero. Comprobaciones con la base de datos.
			// 3.1. El nombre no puede repetirse
			if (esNombreRepetido()) {
				return;
			}

			// En este punto ya podríamos insertar el dato. Recuperamos el siguiente código
			// de estudiante
			long codigo = siguienteCodigoAlumno();

			// Cuarto. Insertamos el alumno
			String sqlQuery = String.format(
					"INSERT INTO ESTUDIANTES VALUES (%d, '%s', '%s', '%s', TO_DATE('%s', 'DD-MM-YYYY') )", codigo,
					strNombre, strDireccion, strTelefono, fecha);

			Statement stmt = conexion.createStatement();

			long resultado = stmt.executeUpdate(sqlQuery);

			if (resultado > 0) {
				Registro.append("Estudiante insertado correctamente con el código " + codigo);

			}

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}
	}

	private long siguienteCodigoAlumno() throws SQLException {

		String sqlQuery = "SELECT MAX(CODESTUDIANTE) FROM ESTUDIANTES";

		Statement stmt = conexion.createStatement();

		ResultSet resultado = stmt.executeQuery(sqlQuery);

		resultado.next();
		long ultimoCodigo = resultado.getLong(1);

		stmt.close();

		return ultimoCodigo + 1;

	}

	private Boolean esNombreRepetido() throws SQLException {

		String sqlQuery = "SELECT COUNT(*) FROM ESTUDIANTES E WHERE E.NOMBRE = ?";

		PreparedStatement stmt = conexion.prepareStatement(sqlQuery);

		stmt.setString(1, strNombre);

		ResultSet resultado = stmt.executeQuery();

		resultado.next();
		int coincidencias = resultado.getInt(1);

		if (coincidencias > 0) {

			Registro.append("Ya existe un estudiante con ese nombre, no se ha podido insertar.");

			return true;
		}

		return false;
	}

	private Boolean hayValoresCampos() throws Exception {

		Boolean esNombreVacio = strNombre.equals("");
		Boolean esDireccionVacia = strDireccion.equals("");
		Boolean esTelefonoVacio = strTelefono.equals("");
		Boolean falloInsertar = false;

		/*****************************
		 * CONTROL DE DATOS
		 ********************************/
		if (esNombreVacio || esDireccionVacia || esTelefonoVacio)
			falloInsertar = true;

		// SI FALTA NOMBRE
		if (esNombreVacio) {
			Registro.append("El campo nombre no tiene valor.");

		}
		// SI FALTA DIRECCION
		if (esDireccionVacia) {
			Registro.append("El campo direccion no tiene valor.");

		}
		// SI FALTA TELEFONO
		if (esTelefonoVacio) {
			Registro.append("El campo telefono no tiene valor.");

		}
		if (falloInsertar) {
			Registro.append("No se ha insertado ningún registro.");

			return false;
		}

		return true;
	}

	private void adaptarValores() throws Exception {

		if (strNombre.length() > 30)
			strNombre = strNombre.substring(0, 30); // 30 caracteres
		if (strDireccion.length() > 40)
			strDireccion = strDireccion.substring(0, 40); // 40 caracteres
		if (strTelefono.length() > 10)
			strTelefono = strTelefono.substring(0, 10); // 10 caracteres

	}

	/********************************************
	 * EJERCICIO 2
	 *********************************************/
	public void mostrarProyecto() {

		try {

			// Comprobamos que exista conexión
			if (!existeConexion()) {
				return;
			}

			Registro.append(Registro.sepLineas);

			int codProyecto = getNumeroProyecto();

			mostrarDatosProyecto(codProyecto);
			mostrarEntidades(codProyecto);
			mostrarAlumnos(codProyecto);

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

	}

	private void mostrarAlumnos(int codProyecto) throws SQLException, Exception {
		// COMPROBAMOS SI ALGUN ESTUDIANTE PERTENECE AL PROYECTO
		String sqlQuery = "SELECT COUNT(*) FROM PARTICIPA PAR WHERE PAR.CODIGOPROYECTO = " + codProyecto;

		PreparedStatement stmt = conexion.prepareStatement(sqlQuery);

		ResultSet rs = stmt.executeQuery();

		rs.next();
		// SI NO HAY ENTIDADES LO IMPRIMIMOS
		if (rs.getInt(1) == 0) {
			Registro.append("\tNINGÚN ESTUDIANTE PERTENECE A ESTE PROYECTO.");

		} else {
			Registro.append("\nLISTA DE ESTUDIANTES QUE PARTICIPAN EN EL PROYECTO:");
			Registro.append(String.format("%-4s %-25s %-37s %6s %-17s %6s %8s%n", "Cód", "Nombre", "Dirección",
					"CodPar", "Tipo aportación", "NumApt", "TotAport"), false);
			Registro.append(String.format("%-4s %-25s %-37s %6s %17s %6s %8s%n", "====", "=========================",
					"=====================================", "======", "=================", "======", "========"),
					false);

			sqlQuery = """
					SELECT ES.CODESTUDIANTE, ES.NOMBRE, ES.DIRECCION, PAR.CODPARTICIPACION AS CODPAR, PAR.TIPOPARTICIPACION, PAR.NUMAPORTACIONES, PAR.NUMAPORTACIONES*P.EXTRAAPORTACION
					FROM PROYECTOS P
					    INNER JOIN PARTICIPA PAR ON (P.CODIGOPROYECTO = PAR.CODIGOPROYECTO)
					    INNER JOIN ESTUDIANTES ES ON (PAR.CODESTUDIANTE = ES.CODESTUDIANTE)
					WHERE P.CODIGOPROYECTO = """
					+ codProyecto;
			stmt = conexion.prepareStatement(sqlQuery);

			rs = stmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nombre = rs.getString(2);
				String direccion = rs.getString(3);
				int codPar = rs.getInt(4);
				String tipoApor = rs.getString(5);
				int numApt = rs.getInt(6);
				int totApor = rs.getInt(7);

				Registro.append(String.format("%4s %-25s %-37s %6s %17s %6s %8s%n", codigo, nombre, direccion, codPar,
						tipoApor, numApt, totApor), false);
			}

			Registro.append(String.format("%-4s %-25s %-37s %6s %17s %6s %8s%n", "====", "=========================",
					"=====================================", "======", "=================", "======", "========"),
					false);

			sqlQuery = """
					SELECT SUM(PAR.NUMAPORTACIONES), SUM(PAR.NUMAPORTACIONES*P.EXTRAAPORTACION)
					FROM PROYECTOS P
						INNER JOIN PARTICIPA PAR ON (P.CODIGOPROYECTO = PAR.CODIGOPROYECTO)
						INNER JOIN ESTUDIANTES ES ON (PAR.CODESTUDIANTE = ES.CODESTUDIANTE)
					WHERE P.CODIGOPROYECTO = ? """;

			PreparedStatement prst = conexion.prepareStatement(sqlQuery);

			prst.setInt(1, codProyecto);

			rs = prst.executeQuery();

			rs.next();
			Registro.append(String.format("TOTALES: %91s %8s%n", rs.getInt(1), rs.getInt(2)), false);

		}

	}

	private void mostrarEntidades(int codProyecto) throws SQLException, Exception {

		// COMPROBAMOS SI ALGUNA ENTIDAD PATROCINA
		String sqlQuery = "SELECT COUNT(*) FROM PATROCINA PA WHERE PA.CODIGOPROYECTO = " + codProyecto;

		PreparedStatement stmt = conexion.prepareStatement(sqlQuery);

		ResultSet rs = stmt.executeQuery();

		rs.next();
		// SI NO HAY ENTIDADES LO IMPRIMIMOS
		if (rs.getInt(1) == 0) {
			Registro.append("\tNINGUNA ENTIDAD PATROCINA ESTE PROYECTO.");

		} else {

			Registro.append("LISTA DE ENTIDADES QUE PATROCINA EL PROYECTO:");
			Registro.append(String.format("%-7s %-30s %-18s %-15s%n", "Código", "Descripción", "Importe aportación",
					"Fecha aportación"), false);
			Registro.append(String.format("%-7s %-30s %-18s %-15s%n", "=======", "==============================",
					"==================", "================"), false);

			sqlQuery = """
					SELECT E.CODENTIDAD, E.DESCRIPCION, PA.IMPORTEAPORTACION, PA.FECHAAPORTACION
					FROM PROYECTOS P
						INNER JOIN PATROCINA PA ON P.CODIGOPROYECTO = PA.CODIGOPROYECTO
						INNER JOIN ENTIDADES E ON  PA.CODENTIDAD = E.CODENTIDAD
						WHERE P.CODIGOPROYECTO = """ + codProyecto;
			stmt = conexion.prepareStatement(sqlQuery);

			rs = stmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String descripcion = rs.getString(2);
				Float importe = rs.getFloat(3);
				Date fechaApor = rs.getDate(4);

				Registro.append(String.format("%7s %-30s %18s %16s%n", codigo, descripcion, importe, fechaApor), false);
			}

			Registro.append(String.format("%-7s %-30s %-18s %-15s%n", "=======", "==============================",
					"==================", "================"), false);
			sqlQuery = """
					SELECT P.PRESUPUESTO+SUM(PA.IMPORTEAPORTACION), SUM(PA.IMPORTEAPORTACION)
					FROM PROYECTOS P
					    INNER JOIN PATROCINA PA ON P.CODIGOPROYECTO = PA.CODIGOPROYECTO
					    INNER JOIN ENTIDADES E ON PA.CODENTIDAD = E.CODENTIDAD
					WHERE P.CODIGOPROYECTO = ?
					GROUP BY P.CODIGOPROYECTO,P.PRESUPUESTO""";
			PreparedStatement prst = conexion.prepareStatement(sqlQuery);

			prst.setInt(1, codProyecto);

			rs = prst.executeQuery();

			rs.next();
			Registro.append(String.format("%-48s %8s%n", "TOTAL APORTACIONES: ", rs.getFloat(2)), false);
			Registro.append(String.format("%-48s %8s%n", "PRESUPUESTO TOTAL: ", rs.getFloat(1)), false);

		}
		Registro.append(Registro.sepLineas);

	}

	private void mostrarDatosProyecto(int codProyecto) throws SQLException, Exception {

		String sqlQuery = """
				SELECT P.CODIGOPROYECTO, P.NOMBRE, P.FECHAINICIO,P.FECHAFIN, P.PRESUPUESTO, P.EXTRAAPORTACION
				FROM PROYECTOS P
				WHERE P.CODIGOPROYECTO = ? """;

		PreparedStatement stmt = conexion.prepareStatement(sqlQuery);

		stmt.setInt(1, codProyecto);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			Registro.append(String.format("COD-PROYECTO: %d   NOMBRE: %s", codProyecto, rs.getString(2)));

			String fechaInicio = rs.getDate(3).toString();
			String fechaFin = rs.getDate(4).toString();

			Registro.append(String.format("FECHA INICIO: %s, FECHA FIN: %s", fechaInicio, fechaFin));

			DecimalFormat formatoDecimal = new DecimalFormat("#.0", new DecimalFormatSymbols(Locale.US));

			String presupuestoFormato = formatoDecimal.format(rs.getFloat(5));
			String extraFormato = formatoDecimal.format(rs.getFloat(6));

			Registro.append(String.format("PRESUPUESTO: %s, EXTRAAPORTACIÓN: %s", presupuestoFormato, extraFormato));
			Registro.append(Registro.sepLineas);

		}

	}

	private int getNumeroProyecto() throws Exception {

		proyecto = formulario.getCboProyecto();
		String seleccion = (String) proyecto.getSelectedItem();

		String[] partesSeleccion = seleccion.split("-");

		String strCodigo = partesSeleccion[0].trim();

		return Integer.parseInt(strCodigo);

	}

	/********************************************
	 * EJERCICIO 3
	 *********************************************/
	public void insertarColumnas() {

		try {

			// Comprobamos que exista conexión
			if (!existeConexion()) {
				return;
			}
			Registro.append(Registro.sepLineas);

			crearColumnas();
			actualizarColumnasCalculadas();
			mostrarProyectosActualizados();

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

	}

	private void mostrarProyectosActualizados() throws SQLException, Exception {

		Registro.append("MOSTRANDO TABLA PROYECTOS");
		Registro.append(Registro.sepLineas);

		String sqlQuery = "SELECT * FROM PROYECTOS";
		Statement stmt = conexion.createStatement();

		ResultSet rs = stmt.executeQuery(sqlQuery);

		Registro.append(String.format("%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s%n", "COD", "NOMBRE",
				"FECHAINI", "FECHAFIN", "PRESUPUEST", "EXTRAAPORT", "NUMEMPRE", "IMPORTEEMP", "NUMALUM", "GASTOALUM",
				"GASTORECUR"), false);

		Registro.append(String.format("%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s%n", "-----",
				"---------------------------------------------", "----------", "----------", "----------", "----------",
				"----------", "----------", "----------", "----------", "----------"), false);
		while (rs.next()) {
			int cod = rs.getInt(1);
			String name = rs.getString(2);
			Date fechaini = rs.getDate(3);
			Date fechafin = rs.getDate(4);
			float presupuesto = rs.getFloat(5);
			float extraAport = rs.getFloat(6);
			int numEmpre = rs.getInt(7);
			float importeEmp = rs.getFloat(8);
			int numAlum = rs.getInt(9);
			float gastoAlum = rs.getFloat(10);
			float gastoRecur = rs.getFloat(11);

			Registro.append(String.format("%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s%n", cod, name,
					fechaini.toString(), fechafin.toString(), presupuesto, extraAport, numEmpre, importeEmp, numAlum,
					gastoAlum, gastoRecur), false);

		}
		Registro.append(String.format("%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s%n", "-----",
				"---------------------------------------------", "----------", "----------", "----------", "----------",
				"----------", "----------", "----------", "----------", "----------"), false);

	}

	private void actualizarColumnasCalculadas() throws SQLException {

		String sqlProyectos = "SELECT P.CODIGOPROYECTO, P.EXTRAAPORTACION FROM PROYECTOS P";
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(sqlProyectos);

		int numEmpresas = 0;
		float importeAportado = 0;
		int numAlum = 0;
		float gastosAlum = 0;
		float gastosRecur = 0;

		// Cargamos los proyectos desde el resulset
		while (rs.next()) {
			Long codProyecto = rs.getLong(1);
			Double extraAporta = rs.getDouble(2);

			// NUMERO EMPRESAS
			String sqlQuery = "SELECT COUNT(P.CODPATROCINIO) FROM PATROCINA P WHERE P.CODIGOPROYECTO = " + codProyecto;

			Statement stmtColumna = conexion.createStatement();
			ResultSet rsColumna = stmtColumna.executeQuery(sqlQuery);
			rsColumna.next();
			numEmpresas = rsColumna.getInt(1);

			// IMPORTE APORTADO EMPRESAS
			sqlQuery = "SELECT NVL(SUM(P.IMPORTEAPORTACION),0) FROM PATROCINA P WHERE P.CODIGOPROYECTO = "
					+ codProyecto;

			rsColumna = stmtColumna.executeQuery(sqlQuery);
			rsColumna.next();
			importeAportado = rsColumna.getFloat(1);

			// ALUMNOS PARTICIPAN
			sqlQuery = "SELECT COUNT(PAR.CODESTUDIANTE) FROM PARTICIPA PAR WHERE PAR.CODIGOPROYECTO = " + codProyecto;

			rsColumna = stmtColumna.executeQuery(sqlQuery);
			rsColumna.next();
			numAlum = rsColumna.getInt(1);

			// GASTOS DE ALUMNOS
			sqlQuery = "SELECT NVL(SUM(PAR.NUMAPORTACIONES*" + extraAporta.toString()
					+ "),0) FROM PARTICIPA PAR WHERE PAR.CODIGOPROYECTO = " + codProyecto;

			rsColumna = stmtColumna.executeQuery(sqlQuery);
			rsColumna.next();
			gastosAlum = rsColumna.getFloat(1);

			// GASTOS RECURSOS
			sqlQuery = "SELECT NVL(SUM(U.CANTIDAD*R.PVP), 0) FROM USA U LEFT JOIN RECURSOS R ON (U.CODRECURSO = R.CODRECURSO) WHERE U.CODIGOPROYECTO = "
					+ codProyecto;

			rsColumna = stmtColumna.executeQuery(sqlQuery);
			rsColumna.next();
			gastosRecur = rsColumna.getFloat(1);

			/* CONSULTA DE ACTUALIZACIÓN DE FILA */
			sqlQuery = "UPDATE PROYECTOS P SET NUMEMPRE = ?, IMPORTEEMP = ?, NUMALUM = ?, GASTOALUM = ?, GASTORECUR = ? WHERE P.CODIGOPROYECTO = "
					+ codProyecto;

			PreparedStatement prst = conexion.prepareStatement(sqlQuery);

			prst.setInt(1, numEmpresas);
			prst.setFloat(2, importeAportado);
			prst.setInt(3, numAlum);
			prst.setFloat(4, gastosAlum);
			prst.setFloat(5, gastosRecur);
			prst.executeUpdate();

			rsColumna.close();
			prst.close();
			stmtColumna.close();
		}

		Registro.append("Tabla PROYECTOS actualizada.");
		Registro.append(Registro.sepLineas);

	}

	private void crearColumnas() throws SQLException, Exception {
		String[] nuevasColumnas = { "NUMEMPRE", "IMPORTEEMP", "NUMALUM", "GASTOALUM", "GASTORECUR" };
		String[] tiposDatos = { "NUMBER(6)", "NUMBER(10,2)", "NUMBER(6)", "NUMBER(10,2)", "NUMBER(10,2)" };

		Boolean existeColumna;

		// Hacemos una consulta genérica
		String sqlQuery = "SELECT * FROM PROYECTOS";
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(sqlQuery);

		String nombreColumna;

		List<String> columnasCreadas = new ArrayList<String>();
		int indiceCreadas = 0;

		List<String> columnasOmitidas = new ArrayList<String>();
		int indiceOmitidas = 0;

		for (int i = 0; i < nuevasColumnas.length; i++) {

			nombreColumna = nuevasColumnas[i];

			existeColumna = existeColumna(rs, nombreColumna);

			if (!existeColumna) {

				// Se añade la columna
				String tipoDato = tiposDatos[i];
				insertarColumna(nombreColumna, tipoDato);

				columnasCreadas.add(nombreColumna);
				indiceCreadas++;
			} else {
				columnasOmitidas.add(nombreColumna);
				indiceOmitidas++;
			}

		}

		// CONTROL DE SALIDA DE CREACIÓN DE COLUMNAS
		if (indiceCreadas > 0) {
			String contenido = String.join(", ", columnasCreadas);
			Registro.append("COLUMNAS añadidas a la tabla PROYECTOS: " + contenido);

		}

		if (indiceOmitidas > 0) {
			String contenido = String.join(", ", columnasOmitidas);
			Registro.append("COLUMNAS NO añadidas porque ya existen: " + contenido);

		}

		Registro.append(Registro.sepLineas);

		rs.close();
		stmt.close();
	}

	private void insertarColumna(String nombreColumna, String tipoDato) throws SQLException {

		String sqlQuery = "ALTER TABLE PROYECTOS ADD (" + nombreColumna + " " + tipoDato + ")";

		Statement stmt = conexion.createStatement();

		stmt.executeUpdate(sqlQuery);

	}

	private boolean existeColumna(ResultSet rs, String nombreColumna) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();

		int columnas = rsmd.getColumnCount();

		for (int i = 1; i <= columnas; i++) {

			String tmpColumna = rsmd.getColumnName(i);

			if (nombreColumna.equals(tmpColumna)) {

				return true;
			}
		}

		return false;
	}

	/********************************************
	 * EJERCICIO 4
	 *********************************************/
	public void generarInforme() {

		String proyecto = System.getProperty("user.dir");

		String srcPlantillas = proyecto + File.separator + "plantillas";
		String pltInforme = srcPlantillas + File.separator + "plantilla.jrxml";

		String srcInformes = proyecto + File.separator + "informes";
		String reportPDF = srcInformes + File.separator + "Informe.pdf";

		// Comprobamos que exista conexión
		if (!existeConexion()) {
			return;
		}

		// Objetos de control del log
		Registro.append(Registro.sepLineas);

		try {
			if (!existeArchivo(pltInforme)) {
				Registro.append("El archivo de plantilla no existe.");

				return;
			}

			// Creamos los parámetros del informe
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("titulo", "LISTADO DE EMPRESAS Y SUS APORTACIONES");
			params.put("autor", "Israel Lucas Torrijos");

			// Establecemos la hora actual en formato 24h
			LocalDateTime fechaCompletaAhora = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
			String horaActual = fechaCompletaAhora.format(formatter);
			params.put("hora", horaActual);

			// Establecemos la fecha actual
			formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String fechaActual = fechaCompletaAhora.format(formatter);
			params.put("fecha", fechaActual);

			// Compilamos la plantilla
			JasperReport jasperReport = JasperCompileManager.compileReport(pltInforme);

			// Rellemos con datos la plantilla
			JasperPrint informeEntidades = JasperFillManager.fillReport(jasperReport, params, conexion);

			// Exportamos los resultados. False para evitar cerrar toda la aplicacion al cerrar el visor.
			JasperViewer.viewReport(informeEntidades, false);
			JasperExportManager.exportReportToPdfFile(informeEntidades, reportPDF);

			// Mensaje de salida
			Path fullPath = Paths.get(reportPDF).toAbsolutePath();

			Registro.append("Se ha creado el informe en la ruta: \n" + fullPath.toString());

		} catch (Exception e) {

			GestionErrores.controlErroresGenericos(e);
		}

	}

	private Boolean existeArchivo(String rutaArchivo) throws Exception {
		File file = new File(rutaArchivo);

		if (!file.exists()) {
			return false;
		}

		return true;

	}

	/********************************************
	 * EJERCICIO 5
	 *********************************************/
	public void verTablas() {

		// Comprobamos que exista conexión
		if (!existeConexion()) {
			return;
		}

		VentanaTablas ventanaTablas = new VentanaTablas(formulario);

		ventanaTablas.setVisible(true);

	}

}
