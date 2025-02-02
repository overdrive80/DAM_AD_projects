package logica;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import clases.Estudiante;
import clases.Participa;
import clases.Proyecto;

public class Operaciones {
	private static Connection conexion;
	private static ODB odb = null;

//	static {
//		try {
//			odb = ODBFactory.open("proyectos.dat");
//		} catch (ODBRuntimeException e) {
//			System.out.println("La base de datos NeoDatis está bloqueada porque otro proceso la abrió.");
//			// System.err.println("Mensaje de error: " + e.getMessage());
//			System.exit(-1);
//		}
//
//	}
	
	private static void abrirBBDD() {
		if (odb == null || odb.isClosed()) {
			try {
				odb = ODBFactory.open("proyectos.dat");
			} catch (ODBRuntimeException e) {
				System.out.println("La base de datos NeoDatis está bloqueada porque otro proceso la abrió.");
				// System.err.println("Mensaje de error: " + e.getMessage());
				System.exit(-1);
			}
		}
		
	}
	
	
	// EJERCICIO 01
	public static void crearBBDD() {
		abrirBBDD();

		try {
			cargarProyectos();
			cargarEstudiantes();
			cargarParticipa();
			cargarListParticipaProyectos();
			cargarListParticipaEstudiantes();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (odb != null) {
				// Cerramos la base de datos
				odb.close();
			}
		}
	}

	private static void cargarListParticipaEstudiantes() {
		try {
			// Recuperamos los proyectos
			Objects<Estudiante> estudiantes = odb.getObjects(Estudiante.class);

			// Recorremos los proyectos
			while (estudiantes.hasNext()) {
				// Recuperamos un proyecto y su codigo para filtrar la tabla dependiente
				Estudiante estudiante = estudiantes.next();
				int codEstudiante = estudiante.getCodestudiante();

				ICriterion filtrado = Where.equal("estudiante.codestudiante", codEstudiante);
				CriteriaQuery consulta = new CriteriaQuery(Participa.class, filtrado);

				Objects<Participa> participaciones = odb.getObjects(consulta);

				List<Participa> participaEst = new ArrayList<Participa>();

				while (participaciones.hasNext()) {
					Participa participa = participaciones.next();

					participaEst.add(participa);

				}

				estudiante.setParticipaen(participaEst);
				odb.store(estudiante);

			}

			odb.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void cargarListParticipaProyectos() {
		try {
			// Recuperamos los proyectos
			Objects<Proyecto> proyectos = odb.getObjects(Proyecto.class);

			// Recorremos los proyectos
			while (proyectos.hasNext()) {
				// Recuperamos un proyecto y su codigo para filtrar la tabla dependiente
				Proyecto proyecto = proyectos.next();
				int codProyecto = proyecto.getCodigoproyecto();

				ICriterion filtrado = Where.equal("proyecto.codigoproyecto", codProyecto);
				CriteriaQuery consulta = new CriteriaQuery(Participa.class, filtrado);

				Objects<Participa> participaciones = odb.getObjects(consulta);

				List<Participa> participaPro = new ArrayList<Participa>();

				while (participaciones.hasNext()) {
					Participa participa = participaciones.next();

					participaPro.add(participa);

				}

				proyecto.setParticipantes(participaPro);
				odb.store(proyecto);

			}

			odb.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void cargarParticipa() {

		try {
			// Usamos paquete SQL
			String sSQL = "SELECT * FROM PARTICIPA";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sSQL);

			// Ahora usamos NEODATIS
			IQuery consulta = null;

			while (rs.next()) {
				// Recuperamos los campos identificadores de cada tabla
				int codParticipa = rs.getInt(1);
				int codEstudiante = rs.getInt(2);
				int codProyecto = rs.getInt(3);

				consulta = new CriteriaQuery(Participa.class, Where.equal("codparticipacion", codParticipa));

				Objects<?> resultado = odb.getObjects(consulta);

				if (resultado.isEmpty()) {
					System.out.printf("La participacion (%d) no existe y es añadida.%n", codParticipa);
					Participa participa = new Participa();

					participa.setCodparticipacion(codParticipa);
					participa.setTipoparticipacion(rs.getString(4));
					participa.setNumaportaciones(rs.getInt(5));

					// Ahora recuperamos objetos de Proyecto de la base de datos Neodatis
					IQuery consultaProyecto = new CriteriaQuery(Proyecto.class,
							Where.equal("codigoproyecto", codProyecto));
					Objects<Proyecto> proyectos = odb.getObjects(consultaProyecto);

					// Si tiene un proyecto o más le asignamos el primero
					if (proyectos.size() >= 1) {
						participa.setProyecto(proyectos.getFirst());
					}

					// Ahora recuperamos objetos de Estudiantes de la base de datos Neodatis
					IQuery consultaEstudiante = new CriteriaQuery(Estudiante.class,
							Where.equal("codestudiante", codEstudiante));
					Objects<Estudiante> estudiantes = odb.getObjects(consultaEstudiante);

					// Si tiene un proyecto o más le asignamos el primero
					if (estudiantes.size() >= 1) {
						participa.setEstudiante(estudiantes.getFirst());
					}

					odb.store(participa);

				} else {
					System.out.printf("La participacion (%d) ya existe.%n", codParticipa);
				}
			}

			rs.close();
			stmt.close();
			odb.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void cargarEstudiantes() {

		try {
			// Usamos paquete SQL
			String sSQL = "SELECT * FROM ESTUDIANTES";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sSQL);

			// Ahora usamos NEODATIS
			IQuery consulta = null;

			while (rs.next()) {
				int codEstudiante = rs.getInt(1);

				consulta = new CriteriaQuery(Estudiante.class, Where.equal("codestudiante", codEstudiante));

				Objects<?> resultado = odb.getObjects(consulta);

				if (resultado.isEmpty()) {
					System.out.printf("El estudiante %s (%d) no existe y es añadido.%n", rs.getString(2),
							codEstudiante);
					Estudiante estudiante = new Estudiante();

					estudiante.setCodestudiante(codEstudiante);
					estudiante.setNombre(rs.getString(2));
					estudiante.setDireccion(rs.getString(3));
					estudiante.setTlf(rs.getString(4));

					java.util.Date fechaAlta = new java.util.Date(rs.getDate(5).getTime());
					estudiante.setFechaalta(fechaAlta);

					odb.store(estudiante);

				} else {
					System.out.printf("El estudiante %s (%d) ya existe.%n", rs.getString(2), codEstudiante);
				}
			}

			rs.close();
			stmt.close();
			odb.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void cargarProyectos() {

		try {
			// Usamos paquete SQL
			String sSQL = "SELECT * FROM PROYECTOS";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sSQL);

			// Ahora usamos NEODATIS
			IQuery consulta = null;

			while (rs.next()) {
				int codProyecto = rs.getInt(1);

				consulta = new CriteriaQuery(Proyecto.class, Where.equal("codigoproyecto", codProyecto));

				Objects<?> resultado = odb.getObjects(consulta);

				if (resultado.isEmpty()) {
					System.out.printf("El proyecto %s (%d) no existe y es añadido.%n", rs.getString(2), codProyecto);
					Proyecto proyecto = new Proyecto();

					proyecto.setCodigoproyecto(codProyecto);
					proyecto.setNombre(rs.getString(2));

					proyecto.setPresupuesto(rs.getFloat(5));
					proyecto.setExtraaportacion(rs.getFloat(6));

					java.util.Date fechaInicio = new java.util.Date(rs.getDate(3).getTime());
					java.util.Date fechaFin = new java.util.Date(rs.getDate(4).getTime());

					proyecto.setFechainicio(fechaInicio);
					proyecto.setFechafin(fechaFin);

					odb.store(proyecto);

				} else {
					System.out.printf("El proyecto %s (%d) ya existe.%n", rs.getString(2), codProyecto);
				}
			}

			rs.close();
			stmt.close();
			odb.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void setConexion(Connection conexion) {
		Operaciones.conexion = conexion;
	}

	// EJERCICIO 2
	public static void listarProyecto(int codProyecto) {
		abrirBBDD();
		imprimirDatosProyecto(codProyecto);

	}

	private static void imprimirDatosProyecto(int codProyecto) {

		try {
			System.out.println("-".repeat(80));

			ICriterion filtrado = Where.equal("codigoproyecto", codProyecto);
			IQuery consulta = new CriteriaQuery(Proyecto.class, filtrado);

			Objects<?> resultado = odb.getObjects(consulta);

			// Comprobamos que existe en la base de datos e imprimimos la salida
			Proyecto proyecto = null;
			if (resultado.isEmpty()) {
				System.out.printf("El proyecto con número %d no existe en la Base de datos.%n", codProyecto);
				System.out.println("-".repeat(80));
				return;
			} else {
				proyecto = (Proyecto) resultado.getFirst();
				System.out.printf("Código de proyecto: %-7s Nombre: %-40s%n", proyecto.getCodigoproyecto(),
						proyecto.getNombre());

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date fInicio = proyecto.getFechainicio();
				Date fFin = proyecto.getFechafin();
				System.out.printf("Fecha inicio: %-13s Fecha fin: %-40s%n", sdf.format(fInicio), sdf.format(fFin));

				System.out.printf("Presupuesto: %-14s Extraaportación: %-40s%n", proyecto.getPresupuesto(),
						proyecto.getExtraaportacion());

			}

			System.out.println("-".repeat(80));

			// Recuperamos los participantes
			List<Participa> participantes = proyecto.getParticipantes();

			// Si no hay participantes
			if (participantes.size() <= 0 || participantes == null) {
				System.out.println("No hay participantes en este proyecto");
				return;
			}

			// Recorremos los participantes
			String msg = "Participantes en el proyecto:";
			System.out.println(msg);
			System.out.println("-".repeat(msg.length()));

			// Imprimimos cabecera de la tabla dependiente
			String formatoColumnas = "%-16s %-13s %-25s %-20s %-15s %-9s";
			String formatoColumnasValores = "%-16s %-13s %-25s %-20s %15s %9s";

			System.out.println(formatearTexto(formatoColumnas, "CODPARTICIPACION", "CODESTUDIANTE", "NOMBREESTUDIANTE",
					"TIPAPORTACION", "NUMAPORTACIONES", "IMPORTE"));
			System.out.println(formatearTexto(formatoColumnas, "-".repeat(16), "-".repeat(13), "-".repeat(25),
					"-".repeat(20), "-".repeat(15), "-".repeat(9)));

			Double extraAporta = (double) proyecto.getExtraaportacion();
			Double total = 0d;
			int totalAporta = 0;

			for (int i = 0; i < participantes.size(); i++) {
				Participa participa = participantes.get(i);

				int codParticipa = participa.getCodparticipacion();
				Estudiante estudiante = participa.getEstudiante();

				int codEstudiante = estudiante.getCodestudiante();
				String nombre = estudiante.getNombre();

				String tipoAporta = participa.getTipoparticipacion();
				int numAporta = participa.getNumaportaciones();

				Double importe = numAporta * extraAporta;

				total += importe;
				totalAporta += numAporta;

				System.out.println(formatearTexto(formatoColumnasValores, codParticipa, codEstudiante, nombre,
						tipoAporta, numAporta, importe));
			}

			System.out.println(formatearTexto(formatoColumnas, "-".repeat(16), "-".repeat(13), "-".repeat(25),
					"-".repeat(20), "-".repeat(15), "-".repeat(9)));
			System.out.println(formatearTexto(formatoColumnasValores, "TOTALES:", " ".repeat(13), " ".repeat(25),
					" ".repeat(20), totalAporta, total));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (odb != null) {
				// Cerramos la base de datos
				odb.close();
			}
		}

	}

	private static String formatearTexto(String formato, Object... campos) {
		try {
			return String.format(formato, campos);
		} catch (Exception e) {
			// Ignorar los argumentos adicionales y devolver la cadena formateada con los
			// disponibles
			return String.format(formato, Arrays.copyOf(campos, formato.length()));
		}
	}

	// EJERCICIO 3
	public static void insertarParticipacion(int codProyecto, int codEstudiante, String tipoParticipa, int numAporta) {
		abrirBBDD();
		try {
			// Comprobar que existe proyecto
			ICriterion filtrado = Where.equal("codigoproyecto", codProyecto);
			IQuery consulta = new CriteriaQuery(Proyecto.class, filtrado);

			Objects<?> proyectos = odb.getObjects(consulta);

			if (proyectos.isEmpty()) {
				System.out.printf("El código de proyecto %d no existe y no se grabarán los datos.%n", codProyecto);
				return;
			}

			// Comprobar que existe el estudiante
			filtrado = Where.equal("codestudiante", codEstudiante);
			consulta = new CriteriaQuery(Estudiante.class, filtrado);

			Objects<?> estudiantes = odb.getObjects(consulta);

			if (estudiantes.isEmpty()) {
				System.out.printf("El código de estudiante %d no existe y no se grabarán los datos.%n", codEstudiante);
				return;
			}

			// Procedemos a grabar los datos. Recuperamos el objeto Proyecto y Estudiante
			Proyecto proyecto = (Proyecto) proyectos.getFirst();
			Estudiante estudiante = (Estudiante) estudiantes.getFirst();

			// Obtenemos el último número de participantes
			IValuesQuery consultaValor = new ValuesCriteriaQuery(Participa.class).max("codparticipacion");
			Values valores = odb.getValues(consultaValor);

			// Si retorna varias filas de la consulta lo recorreremos. En este caso solo
			// devuelve 1.
			int maximo = 0;
			while (valores.hasNext()) {

				ObjectValues objectValue = (ObjectValues) valores.next();
				BigDecimal codigo = (BigDecimal) objectValue.getByIndex(0);
				maximo = codigo.intValue();
			}

			// Incrementamos la variable
			maximo += 1;

			Participa participa = new Participa(maximo, estudiante, proyecto, tipoParticipa, numAporta);

			// ES IMPORTANTE EL ORDEN Y EL GUARDADO. Primero guardamos los objetos
			// secundarios (Participa)
			// Almacenamos la participacion.
			odb.store(participa);

			proyecto.addParticipante(participa);
			odb.store(proyecto);

			estudiante.addParticipaen(participa);
			odb.store(estudiante);

			odb.commit();
			System.out.printf("Se ha insertado una nueva participación con código %d%n", maximo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (odb != null) {
				// Cerramos la base de datos
				odb.close();
			}
		}
	}
}
