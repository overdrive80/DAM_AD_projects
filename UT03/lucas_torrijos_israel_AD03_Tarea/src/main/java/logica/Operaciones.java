package logica;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import clases.*;
import gui.Main_lucas_torrijos_israel;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Operaciones {

	private Main_lucas_torrijos_israel ventanaPrincipal;
	private JTextField evaluacion;
	private JTextField nota;
	private JComboBox<String> cboAlumno;
	private JComboBox<String> cboAsigna;
	public static SessionFactory sesionFactory;

	public Operaciones(Main_lucas_torrijos_israel ventanaPrincipal) {

		this.ventanaPrincipal = ventanaPrincipal;
	}

	/********************************************
	 * EJERCICIO 1
	 *********************************************/
	public void insertarNota() {
		boolean todoCorrecto;
		// Reseteamos el texto del log
		Registro.clear();

		imprimirCabeceraInsertar();

		todoCorrecto = comprobarDatosEjercicio1();

		if (todoCorrecto) {
			insertarNotaEvaluacion();
		}

	}

	public static void setSesionFactory(SessionFactory sesionFactory) {
		Operaciones.sesionFactory = sesionFactory;
	}

	private void insertarNotaEvaluacion() {
		Double notaEval = Double.parseDouble(nota.getText());
		String nombreAlumno = getDatosAlumno(1);
		int numAlumno = Integer.parseInt(getDatosAlumno(0));
		String nombreAsigna = getDatosAsignatura(1);
		int numAsigna = Integer.parseInt(getDatosAsignatura(0));

		int numEval = Integer.parseInt(evaluacion.getText());

		Session session = null;
		Transaction transaction = null;

		try {
			session = sesionFactory.openSession();

			transaction = session.beginTransaction();

			Evaluaciones evaluacion = new Evaluaciones();
			EvaluacionesId id = new EvaluacionesId();

			id.setCodEvaluacion(numEval);
			id.setCodAsig((int) numAsigna);
			id.setNumAlumno((int) numAlumno);

			evaluacion.setId(id);
			evaluacion.setNota(notaEval);

			session.persist(evaluacion);

			transaction.commit();

			session.close();

			String salida = String.format(
					"Para el alumno %s se ha insertado la nota %.1f en la asignatura %s y evaluación %d.", nombreAlumno,
					notaEval, nombreAsigna, numEval);

			Registro.append(salida);

		} catch (Exception e) {
			// Deshacemos cambios
			if (transaction != null) {
				transaction.rollback();
			}
			GestionErrores.controlErroresGenericos(e);
		}

	}

	public void comprobarDatosNotas() {
		// Reseteamos el texto del log
		Registro.clear();

		imprimirCabeceraComprobar();

		comprobarDatosEjercicio1();
	}

	private void asignarVariablesGUI() {
		this.evaluacion = ventanaPrincipal.getTxtEval();
		this.nota = ventanaPrincipal.getTxtNota();
		this.cboAlumno = ventanaPrincipal.getCboAlumno();
		this.cboAsigna = ventanaPrincipal.getCboAsigna();
	}

	private boolean comprobarDatosEjercicio1() {
		boolean evalCorrecta;
		boolean notaCorrecta;
		boolean esAsignaCorrecta;
		try {
			asignarVariablesGUI();

			// CAMPO EVALUACION. Solo debe tener valores entre 1 y 3
			String sEvaluacion = evaluacion.getText();
			evalCorrecta = comprobarRangoEvaluacion(sEvaluacion);

			// CAMPO NOTA. Solo debe tener valores entre 1 y 10
			String sNota = nota.getText();
			notaCorrecta = comprobarRangoNota(sNota);

			// COMPROBAR:
			// 1. SI EXISTE NOTA DE ALUMNO Y ASIGNATURA
			// 2. SI LA ASIGNATURA ES DEL CURSO DEL ALUMNO
			if (notaCorrecta && evalCorrecta) {
				String nombreAlumno = getDatosAlumno(1);
				long numAlumno = Long.parseLong(getDatosAlumno(0));
				String nombreAsigna = getDatosAsignatura(1);
				long numAsigna = Long.parseLong(getDatosAsignatura(0));

				int numEval = Integer.parseInt(sEvaluacion);

				// CONDICION 1.
				Double notaExistente = comprobarExisteNota(numAlumno, numEval, numAsigna);

				if (notaExistente != null) {
					StringBuilder salida = new StringBuilder();
					salida.append("El alumno ya tiene nota en la evaluación.\n");
					salida.append(String.format("La nota en la asignatura %s es: %.1f%n", nombreAsigna, notaExistente));

					Registro.append(salida.toString());
					return false;
				}

				// CONDICION 2.
				esAsignaCorrecta = comprobarAsignaturaAlumno(numAsigna, numAlumno);

				if (!esAsignaCorrecta) {
					Registro.append(String.format("La asignatura %s no está dentro del curso del alumno %s%n",
							nombreAsigna, nombreAlumno));
					return false;
				}

				Registro.append("Los datos son correctos y no existe nota para esa evaluación, alumno y asignatura.");
				return true;
			}
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);

		}
		return false;

	}

	private void imprimirCabeceraComprobar() {
		String msg = "Ejercicio 1. Comprobar.";
		Registro.append(msg);
		Registro.append("-".repeat(msg.length()));
	}

	private void imprimirCabeceraInsertar() {
		String msg = "Ejercicio 1. Insertar, antes comprobar.";
		Registro.append(msg);
		Registro.append("-".repeat(msg.length()));
	}

	private boolean comprobarAsignaturaAlumno(long numAsigna, long numAlumno) {

		Session session = null;
		boolean esCorrecta = false;

		try {
			session = sesionFactory.openSession();

			// Esta consulsta devuelve las asignaturas del curso del alumno
			String strHQL = """
					select a.codAsig from Alumnos al
						join Asignaturas a on al.cursos.codCurso = a.cursos.codCurso
						where al.numAlumno= ?1
					  """;

			Query<?> query = session.createQuery(strHQL, Alumnos.class);
			query.setParameter(1, numAlumno);

			List<?> asignaturas = query.list();

			Iterator<?> it = asignaturas.iterator();

			while (it.hasNext()) {
				Integer valorLista = (Integer) it.next();

				if (valorLista == numAsigna) {
					esCorrecta = true;
					break;
				}

			}

			session.close();

		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

		return esCorrecta;

	}

	private Double comprobarExisteNota(long numAlumno, int numEval, double numAsigna) {
		Double notaExistente = null;

		try {
			Session session = sesionFactory.openSession();

			String strHQL = """
					select e.nota
					from Evaluaciones e
					where e.id.codEvaluacion = ?1 and
					      e.id.numAlumno = ?2 and
					      e.id.codAsig = ?3""";

			Query<Double> query = session.createQuery(strHQL, Double.class);
			query.setParameter(1, numEval);
			query.setParameter(2, numAlumno);
			query.setParameter(3, numAsigna);

			notaExistente = query.uniqueResult();

			session.close();
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

		return notaExistente;
	}

	private String getDatosAlumno(int indice) {

		// cboAlumno = ventanaPrincipal.getCboAlumno();
		String seleccion = (String) cboAlumno.getSelectedItem();

		String[] partesSeleccion = seleccion.split("-");

		String strCodigo = partesSeleccion[indice].trim();

		return strCodigo;

	}

	private String getDatosAsignatura(int indice) {

		// cboAsigna = ventanaPrincipal.getCboAsigna();
		String seleccion = (String) cboAsigna.getSelectedItem();

		String[] partesSeleccion = seleccion.split("-");

		String strCodigo = partesSeleccion[indice].trim();

		return strCodigo;

	}

	private boolean comprobarRangoEvaluacion(String texto) {
		try {
			int valor = Integer.parseInt(texto);

			if (valor < 1 || valor > 3) {
				Registro.append("El campo de evaluación debe tener valores entre 1 y 3");
				return false;
			}

			return true;
		} catch (NumberFormatException e) {
			Registro.append("Debe introducir un valor numérico para la evaluación");
			return false;
		}
	}

	private boolean comprobarRangoNota(String texto) {
		try {
			double valor = Double.parseDouble(texto);

			if (valor < 1 || valor > 10) {
				Registro.append("El campo de nota debe tener valores entre 1 y 10");
				return false;
			}

			return true;
		} catch (NumberFormatException e) {
			Registro.append("Debe introducir un valor numérico para la nota");
			return false;
		}
	}

	/********************************************
	 * EJERCICIO 2
	 *********************************************/
	public void actualizarContadores() {
		// Reseteamos el texto del log
		Registro.clear();

		imprimirCabeceraContadores();

		// Parte 1. Actualizamos los contadores de la tabla Centros
		actualizaCentrosAlumnos();
		actualizaCentrosCursos();

		// Parte 2. Actualizamos los contadores de la tabla Cursos
		actualizaCursosAlumnos();

		// Parte 3. Actualizamos los contadores de la tabla Departamentos
		actualizaDepartamentosAsignaturas();

		// Parte 4. Actualizamos notamedia
		actualizaNotaMediaAlumnos();
	}

	private void actualizaNotaMediaAlumnos() {
		// Paso 1: Obtener el recuento
		String strHQL = """
				select a.numAlumno,coalesce(avg(e.nota), 0)
				from Alumnos a
				    left join a.evaluacioneses e
				group by a.numAlumno
					""";

		Transaction transaction = null;
		Session session = null;

		try {
			session = sesionFactory.openSession();

			transaction = session.beginTransaction();

			Query<?> query = session.createQuery(strHQL, Object[].class);

			List<?> cuentaNotas = query.list();

			for (int i = 0; i < cuentaNotas.size(); i++) {
				Object[] linea = (Object[]) cuentaNotas.get(i);

				Integer numAlumno = (Integer) linea[0];
				Double numNota = (Double) linea[1];

				// Recuperamos un objeto de la base de datos
				Alumnos alumno = session.get(Alumnos.class, numAlumno);

				alumno.setNotaMedia(numNota);

				session.merge(alumno);

			}

			transaction.commit();

			session.close();

			Registro.append("Se han actualizado los contadores de las notas medias de los Alumnos.");

		} catch (Exception e) {
			// Deshacemos cambios
			if (transaction != null) {
				transaction.rollback();
			}

			GestionErrores.controlErroresGenericos(e);
		}

	}

	private void imprimirCabeceraContadores() {
		String msg = "Ejercicio 2. Actualizar contadores.";
		Registro.append(msg);
		Registro.append("-".repeat(msg.length()));

	}

	private void actualizaDepartamentosAsignaturas() {
		// Paso 1: Obtener el recuento
		String strHQL = """
				select dep.codDepar, coalesce(count(asi.codAsig),0)
				from Departamentos dep
					left join dep.asignaturases asi
				group by dep.codDepar
					""";

		Transaction transaction = null;
		Session session = null;

		try {
			session = sesionFactory.openSession();

			transaction = session.beginTransaction();

			Query<?> query = session.createQuery(strHQL, Object[].class);

			List<?> cuentaAsigna = query.list();

			for (int i = 0; i < cuentaAsigna.size(); i++) {
				Object[] linea = (Object[]) cuentaAsigna.get(i);

				Integer codDepar = (Integer) linea[0];
				Long numAsigna = (Long) linea[1];

				// Recuperamos un objeto de la base de datos
				Departamentos depar = session.get(Departamentos.class, codDepar);

				depar.setNumAsig(numAsigna.intValue());

				session.merge(depar);

			}

			transaction.commit();

			session.close();

			Registro.append("Se han actualizado los contadores de asignaturas por Departamentos.");

		} catch (Exception e) {
			// Deshacemos cambios
			if (transaction != null) {
				transaction.rollback();
			}

			GestionErrores.controlErroresGenericos(e);
		}

	}

	private void actualizaCursosAlumnos() {
		// Paso 1: Obtener el recuento
		String strHQL = """
				select cu.codCurso, coalesce(count(a.numAlumno),0)
				from Cursos cu
					left join cu.alumnoses a
				group by cu.codCurso
					""";

		Transaction transaction = null;
		Session session = null;

		try {
			session = sesionFactory.openSession();

			transaction = session.beginTransaction();

			Query<?> query = session.createQuery(strHQL, Object[].class);

			List<?> cuentaAlumnos = query.list();

			for (int i = 0; i < cuentaAlumnos.size(); i++) {
				Object[] linea = (Object[]) cuentaAlumnos.get(i);

				Integer codCurso = (Integer) linea[0];
				Long numAlumnos = (Long) linea[1];

				// Recuperamos un objeto de la base de datos
				Cursos curso = session.get(Cursos.class, codCurso);

				curso.setNumAlumnos(numAlumnos.intValue());

				session.merge(curso);

			}

			transaction.commit();

			session.close();

			Registro.append("Se han actualizado los contadores de alumnos por Asignaturas.");

		} catch (Exception e) {
			// Deshacemos cambios
			if (transaction != null) {
				transaction.rollback();
			}

			GestionErrores.controlErroresGenericos(e);
		}
	}

	private void actualizaCentrosCursos() {
		// Paso 1: Obtener el recuento
		String strHQL = """
				select ce.codCentro, coalesce(count(cu.codCurso),0)
				from Centros ce
					left join ce.cursoses cu
				group by ce.codCentro
					""";
		Transaction transaction = null;
		Session session = null;

		try {
			session = sesionFactory.openSession();

			transaction = session.beginTransaction();

			Query<?> query = session.createQuery(strHQL, Object[].class);

			List<?> cuentaCursos = query.list();

			for (int i = 0; i < cuentaCursos.size(); i++) {
				Object[] linea = (Object[]) cuentaCursos.get(i);

				Integer codCentro = (Integer) linea[0];
				Long numCursos = (Long) linea[1];

				// Recuperamos un objeto de la base de datos
				Centros centro = session.get(Centros.class, codCentro);

				centro.setNumCursos(numCursos.intValue());

				session.merge(centro);

			}

			transaction.commit();

			session.close();

			Registro.append("Se han actualizado los contadores de cursos por Centro.");

		} catch (Exception e) {
			// Deshacemos cambios
			if (transaction != null) {
				transaction.rollback();
			}

			GestionErrores.controlErroresGenericos(e);
		}

	}

	private void actualizaCentrosAlumnos() {

		// Paso 1: Obtener el recuento
		String strHQL = """
				select ce.codCentro, coalesce(count(a.nombre),0)
				from Centros ce
					left join ce.cursoses cu
					left join cu.alumnoses a
				group by ce.codCentro
					""";
		Transaction transaction = null;
		Session session = null;

		try {
			session = sesionFactory.openSession();

			transaction = session.beginTransaction();

			Query<?> query = session.createQuery(strHQL, Object[].class);

			List<?> cuentaAlumnos = query.list();

			for (int i = 0; i < cuentaAlumnos.size(); i++) {
				Object[] linea = (Object[]) cuentaAlumnos.get(i);

				Integer codCentro = (Integer) linea[0];
				Long numAlumnos = (Long) linea[1];

				// Recuperamos un objeto de la base de datos
				Centros centro = session.get(Centros.class, codCentro);

				centro.setNumAlumnos(numAlumnos.intValue());

				session.merge(centro);

			}

			transaction.commit();

			session.close();

			Registro.append("Se han actualizado los contadores de alumnos por Centro.");

		} catch (Exception e) {
			// Deshacemos cambios
			if (transaction != null) {
				transaction.rollback();
			}

			GestionErrores.controlErroresGenericos(e);
		}

	}

	/********************************************
	 * EJERCICIO 3
	 *********************************************/
	public void mostrarDatosCursos() {

		// Reseteamos el texto del log
		Registro.clear();

		String strHQL = "from Cursos";

		Session session = null;

		try {
			session = sesionFactory.openSession();

			Query<?> query = session.createQuery(strHQL, Cursos.class);

			List<?> lista = query.list();

			Iterator<?> it = lista.iterator();

			String formatoCurso = "%-10s %-5d %-13s %-5s";
			String formatoCentro = "%-14s %-25s %-10s %-15s";

			while (it.hasNext()) {
				Cursos curso = (Cursos) it.next();
				Centros centro = (Centros) curso.getCentros();

				int codCurso = curso.getCodCurso();
				String nomCurso = curso.getDenominacion();
				String nomCentro = centro.getNombre();
				String localidad = centro.getLocalidad();

				// Información del Curso
				Registro.append(formatearTexto(formatoCurso, "COD-CURSO:", codCurso, "NOMBRE CURSO:", nomCurso));
				// Información del Centro
				Registro.append(formatearTexto(formatoCentro, "NOMBRE CENTRO:", nomCentro, "LOCALIDAD", localidad));

				listarAlumnos(codCurso);

			}

		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

	}

	private int listarAlumnos(int codCurso) {
		Session session = null;
		int numAlumnos = 0;
		double notaClaseEval_1 = 0.0, notaClaseEval_2 = 0.0, notaClaseEval_3 = 0.0, notaMediaClase = 0.0;
		double notaMax = 0.0;
		int codAlumMax = 0;

		DecimalFormat formatoNota = new DecimalFormat("#0.000");

		try {
			session = sesionFactory.openSession();

			String formatoAlumnos = "%-10s %-25s %10s %10s %10s %12s";
			String formatoNotas = "%-36s %10s %10s %10s %12s";

			String strHQL = "from Alumnos a where a.cursos.codCurso = ?1";

			Query<?> query = session.createQuery(strHQL, Alumnos.class);
			query.setParameter(1, codCurso);

			List<?> lista = query.list();

			Iterator<?> it = lista.iterator();

			numAlumnos = lista.size(); // Número de alumnos

			if (numAlumnos == 0) {
				Registro.append("\t" + "-".repeat(35) + "SIN ALUMNOS" + "-".repeat(36) + "\n");
				return 0;
			}

			// Cabecera de los Alumnos del Centro
			Registro.append("\t" + formatearTexto(formatoAlumnos, "NUM_ALUMNO", "NOMBRE", "NOTA_EVA1", "NOTA_EVA2",
					"NOTA_EVA3", "NOTA-MEDIA"));
			Registro.append("\t" + formatearTexto(formatoAlumnos, "-".repeat(10), "-".repeat(25), "-".repeat(10),
					"-".repeat(10), "-".repeat(10), "-".repeat(12)));

			// Listamos los alumnos y sus notas medias por evaluación y totales
			while (it.hasNext()) {
				Alumnos alumno = (Alumnos) it.next();

				int numAlum = alumno.getNumAlumno();
				String nombreAlum = alumno.getNombre();

				// Obtenemos en un array las notas medias
				Double[] notasMedias = obtenerNotasMedias(numAlum);

				// Asignamos estas notas medias a variables que nos valdrán para mostrar la info
				// y calcular medias totales.
				Double notaEval1 = notasMedias[0];
				Double notaEval2 = notasMedias[1];
				Double notaEval3 = notasMedias[2];
				Double notaMedia = notasMedias[3];

				// Obtenemos la nota media max y su numero de alumno
				if (notaMedia > notaMax) {
					notaMax = notaMedia;
					codAlumMax = numAlum;
				}

				Registro.append("\t" + formatearTexto(formatoAlumnos, numAlum, nombreAlum,
						formatoNota.format(notaEval1), formatoNota.format(notaEval2), formatoNota.format(notaEval3),
						formatoNota.format(notaMedia)));

				// Sumatorios para las notas medias del curso
				notaClaseEval_1 = notaClaseEval_1 + notaEval1;
				notaClaseEval_2 = notaClaseEval_2 + notaEval2;
				notaClaseEval_3 = notaClaseEval_3 + notaEval3;
				notaMediaClase = notaMediaClase + notaMedia;
			}

			// Separador
			Registro.append("\t" + formatearTexto(formatoAlumnos, "-".repeat(10), "-".repeat(25), "-".repeat(10),
					"-".repeat(10), "-".repeat(10), "-".repeat(12)));

			// Notas medias del curso
			Registro.append("\t" + formatearTexto(formatoNotas, "NOTAS MEDIAS",
					formatoNota.format(notaClaseEval_1 / numAlumnos), formatoNota.format(notaClaseEval_2 / numAlumnos),
					formatoNota.format(notaClaseEval_3 / numAlumnos), formatoNota.format(notaMediaClase / numAlumnos)));

			// Alumno con más nota media. Lo recuperamos mediante su id desde una session
			// independientemente de la consulta HQL
			Alumnos alumnoMax = new Alumnos();

			// No aseguramos de no rescatar un alumno no existente
			if (codAlumMax != 0) {
				alumnoMax = session.get(Alumnos.class, codAlumMax);

			}
			Registro.append("\tAlumno con más nota media: " + alumnoMax.getNombre() + "\n");

			session.close();

		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}
		return numAlumnos;

	}

	private Double[] obtenerNotasMedias(int numAlumno) {
		Double[] notas = new Double[4];
		Double sumaNotas = 0.0;

		String strHQL = """
				select coalesce(avg(e.nota), 0)
				from Alumnos a
					left join a.evaluacioneses e
				where a.numAlumno = ?1 and e.id.codEvaluacion = ?2
					""";

		Session session = null;
		try {
			session = sesionFactory.openSession();

			Query<?> query = session.createQuery(strHQL, Double.class);
			query.setParameter(1, numAlumno);

			for (int i = 0; i < 3; i++) {
				int numEval = i + 1;

				query.setParameter(2, numEval);

				Double nota = (Double) query.uniqueResult();
				notas[i] = nota;
				sumaNotas = sumaNotas + nota;
			}

			notas[3] = sumaNotas / 3;

			session.close();

			return notas;
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}
		return notas;
	}

	/********************************************
	 * EJERCICIO 4
	 *********************************************/
	public void mostrarEstadistica() {
		// Reseteamos el texto del log
		Registro.clear();

		String msg = "Ejercicio 4. Estadística de centros.";
		Registro.append(msg);
		Registro.append("-".repeat(msg.length()));

		String formatoColumnas = "%-10s %-30s %-10s %-10s %-10s %-10s %-30s";
		String formatoColumnasValores = "%-10s %-30s %10s %10s %10s %10s %-30s";

		cabeceraEstadisticas(formatoColumnas);

		Session session = null;
		long totalCentros = 0;
		long totalCursos = 0;
		long totalAlumnos = 0;
		long totalAsig = 0;
		Double totalMedia = 0.0;

		try {
			session = sesionFactory.openSession();

			// Recorremos los centros
			String strHQL = "from Centros ce";

			Query<?> query = session.createQuery(strHQL, Centros.class);

			List<?> listaCentros = query.list();

			totalCentros = listaCentros.size();

			for (int i = 0; i < totalCentros; i++) {
				Centros centro = (Centros) listaCentros.get(i);

				int codCentro = centro.getCodCentro();
				String nombreCentro = centro.getNombre();

				// Recuperamos valor por columnas
				// NUM-CURSOS
				String strHQLColumna = """
						select coalesce(count(cu.codCurso),0)
						from Centros ce
							left join ce.cursoses cu
						where ce.codCentro = ?1
						group by ce.codCentro
							""";

				Query<?> queryColumna = session.createQuery(strHQLColumna, Long.class);
				queryColumna.setParameter(1, codCentro);

				Long numCursos = (Long) queryColumna.uniqueResult();

				// NUM-ALUMNOS
				strHQLColumna = """
						select coalesce(count(a.nombre),0)
						from Centros ce
							left join ce.cursoses cu
							left join cu.alumnoses a
						where ce.codCentro = ?1
						group by ce.codCentro
							""";

				queryColumna = session.createQuery(strHQLColumna, Long.class);
				queryColumna.setParameter(1, codCentro);

				Long numAlumnos = (Long) queryColumna.uniqueResult();

				// NUM-ASIG
				strHQLColumna = """
						select count(asi.nombre)
						from Centros ce
							left join ce.cursoses cu
							left join cu.asignaturases asi
						where ce.codCentro = ?1
						group by ce.codCentro
							""";

				queryColumna = session.createQuery(strHQLColumna, Long.class);
				queryColumna.setParameter(1, codCentro);

				Long numAsig = (Long) queryColumna.uniqueResult();
				// NUM-ASIG
				strHQLColumna = """
						select round(coalesce(avg(ev.nota),0),1)
						from Centros ce
							left join ce.cursoses cu
							left join cu.asignaturases asi
							left join asi.evaluacioneses ev
							where ce.codCentro = ?1
						group by ce.codCentro
							""";

				queryColumna = session.createQuery(strHQLColumna, Double.class);
				queryColumna.setParameter(1, codCentro);

				Double notaMedia = (Double) queryColumna.uniqueResult();

				// CALCULO DE TOTALES
				totalCursos = totalCursos + numCursos;
				totalAlumnos = totalAlumnos + numAlumnos;
				totalAsig = totalAsig + numAsig;
				totalMedia = totalMedia + notaMedia;

				// ALUMNO MAX
				record AlumnoMaximo(String alumno, Double nota) {
				}

				String strHQLFinal = """
						select alu.nombre, round(coalesce(avg(ev.nota),0),1)
						from Centros ce
							left join ce.cursoses cu
							left join cu.alumnoses alu
							left join alu.evaluacioneses ev
							where ce.codCentro = ?1
						group by ce.codCentro, alu.nombre
						having coalesce(avg(ev.nota),0) in (

							select max(coalesce(avg(ev2.nota),0))
							from Centros ce2
								left join ce2.cursoses cu2
								left join cu2.alumnoses alu2
								left join alu2.evaluacioneses ev2
							where ce2 = ce
							group by ce.codCentro, alu2.nombre
						)
							""";

				Query<AlumnoMaximo> queryMaximo = session.createQuery(strHQLFinal, AlumnoMaximo.class);
				queryMaximo.setParameter(1, codCentro);

				List<AlumnoMaximo> resultados = queryMaximo.getResultList();

				Iterator<?> it = resultados.iterator();

				int indice = 0;
				while (it.hasNext()) {
					indice++;

					AlumnoMaximo alumno = (AlumnoMaximo) it.next();

					String nombreAlumno = alumno.alumno();
					Double notaAlumno = alumno.nota();
					String sAlumnoMax = "";

					if (nombreAlumno == null) {
						nombreAlumno = "SIN ALUMNOS";
						sAlumnoMax = nombreAlumno;
					} else {
						sAlumnoMax = nombreAlumno + " (" + notaAlumno + ")";
					}

					if (indice <= 1) {
						Registro.append(formatearTexto("\t" + formatoColumnasValores, codCentro, nombreCentro,
								numCursos, numAlumnos, numAsig, notaMedia, sAlumnoMax));
					} else {
						Registro.append(formatearTexto("\t" + formatoColumnasValores, " ".repeat(10), " ".repeat(30),
								" ".repeat(10), " ".repeat(10), " ".repeat(10), " ".repeat(10), sAlumnoMax));
					}
				}

			}

			separadorEstadistica(formatoColumnas);

			String formatoTotales = "%-41s %10s %10s %10s %10s";

			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');

			DecimalFormat decimal = new DecimalFormat("#0.0", dfs);
			Registro.append(formatearTexto("\t" + formatoTotales, "TOTALES", totalCursos, totalAlumnos, totalAsig,
					decimal.format(totalMedia / totalCentros)));

			session.close();
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);

		}
	}

	private void cabeceraEstadisticas(String formatoColumnas) {

		Registro.append(formatearTexto("\t" + formatoColumnas, "COD-CENTRO", "NOMBRE", "NUMCURSOS", "NUMALUMNOS",
				"NUM-ASIG", "NOTA-MEDIA", "ALUMNO MÁXIMO"));
		separadorEstadistica(formatoColumnas);
	}

	private void separadorEstadistica(String formatoColumnas) {

		Registro.append(formatearTexto("\t" + formatoColumnas, "-".repeat(10), "-".repeat(30), "-".repeat(10),
				"-".repeat(10), "-".repeat(10), "-".repeat(10), "-".repeat(30)));
	}

//	private static void imprimirTextoFormato(String formato, Object... campos) {
//
//		System.out.printf(formato, campos);
//	}

	private String formatearTexto(String formato, Object... campos) {
		try {
			return String.format(formato, campos);
		} catch (Exception e) {
			// Ignorar los argumentos adicionales y devolver la cadena formateada con los
			// disponibles
			return String.format(formato, Arrays.copyOf(campos, formato.length()));
		}
	}
}
