package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dto.Entidad;
import dto.Estudiante;
import dto.Proyecto;
import logica.GestionErrores;
import logica.Logica;
import logica.Registro;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaTablas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Connection conexion;
	private static final String sqlProyectos = "SELECT CODIGOPROYECTO, NOMBRE, FECHAINICIO, FECHAFIN, PRESUPUESTO, EXTRAAPORTACION FROM PROYECTOS";
	private static final String sqlEstudiantes = "SELECT CODESTUDIANTE, NOMBRE, DIRECCION, TLF, FECHAALTA FROM ESTUDIANTES";
	private static final String sqlEntidades = "SELECT CODENTIDAD, DESCRIPCION, TELEFONO, DIRECCION, CONTACTO FROM ENTIDADES";

	/**
	 * Create the frame.
	 */
	public VentanaTablas(Main_lucas_torrijos_israel_AD02_Tarea formulario) {
		setTitle("MOSTRAR DATOS DE TABLAS");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 946, 634);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPanelTablas = new JScrollPane();
		scrollPanelTablas.setBounds(10, 181, 910, 403);
		contentPane.add(scrollPanelTablas);

		table = new JTable();
		scrollPanelTablas.setViewportView(table);
		table.setBorder(new LineBorder(new Color(192, 192, 192)));

		JButton btnProyectos = new JButton("Ver proyectos");
		btnProyectos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarProyectos();
			}
		});
		btnProyectos.setFont(new Font("Century Gothic", Font.BOLD, 18));
		btnProyectos.setBounds(10, 36, 223, 77);
		contentPane.add(btnProyectos);

		JButton btnEstudiantes = new JButton("Ver estudiantes");
		btnEstudiantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarEstudiantes();
			}
		});
		btnEstudiantes.setFont(new Font("Century Gothic", Font.BOLD, 18));
		btnEstudiantes.setBounds(353, 36, 223, 77);
		contentPane.add(btnEstudiantes);

		JButton btnEntidades = new JButton("Ver entidades");
		btnEntidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarEntidades();
			}
		});
		btnEntidades.setFont(new Font("Century Gothic", Font.BOLD, 18));
		btnEntidades.setBounds(697, 36, 223, 77);
		contentPane.add(btnEntidades);

		this.conexion = formulario.getConexion();
		
		// Cargamos los datos de las tablas 
		// en las listas de la clase Logica. Se usa como contenedor
		inicializarDatos();
	}

	private void inicializarDatos() {

		refrescarDatosEstudiantes();
		refrescarDatosProyectos();
		refrescarDatosEntidades();
	}

	private void refrescarDatosEntidades() {

		// Cargamos estudiantes
		List<Entidad> datosEntidades = cargarDatosEntidades(sqlEntidades);
		Logica.setEntidades(datosEntidades);
	}

	private void refrescarDatosEstudiantes() {

		// Cargamos estudiantes
		List<Estudiante> datosEstudiantes = cargarDatosEstudiantes(sqlEstudiantes);
		Logica.setEstudiantes(datosEstudiantes);
	}

	private void refrescarDatosProyectos() {

		// Cargamos estudiantes
		List<Proyecto> datosProyectos = cargarDatosProyectos(sqlProyectos);
		Logica.setProyectos(datosProyectos);
	}

	private void mostrarProyectos() {

		DefaultTableModel dtm = new DefaultTableModel();

		Registro.append(Registro.sepLineas);

		// Leemos las columnas de la tabla
		String[] cabeceraTabla = getNombreColumnas(sqlProyectos);
		dtm.setColumnIdentifiers(cabeceraTabla);

		refrescarDatosProyectos();

		for (Proyecto proyecto : Logica.getProyectos()) {

			dtm.addRow(proyecto.toArray());
		}

		table.setModel(dtm);

		Registro.append("La tabla Proyectos se ha cargado.");

	}

	private List<Proyecto> cargarDatosProyectos(String sqlQuery) {

		Statement stmt;
		List<Proyecto> proyectos = new ArrayList<Proyecto>();

		try {
			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				Proyecto proyecto = new Proyecto();

				proyecto.setCodProyecto(rs.getLong(1));
				proyecto.setNombre(rs.getString(2));
				proyecto.setFechaInicio(rs.getDate(3));
				proyecto.setFechaFin(rs.getDate(4));
				proyecto.setPresupuesto(rs.getDouble(5));
				proyecto.setExtraAportacion(rs.getDouble(6));

				proyectos.add(proyecto);

			}

			return proyectos;
		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

		return proyectos;
	}

	private void mostrarEstudiantes() {

		DefaultTableModel dtm = new DefaultTableModel();

		Registro.append(Registro.sepLineas);

		// Leemos las columnas de la tabla
		String[] cabeceraTabla = getNombreColumnas(sqlEstudiantes);
		dtm.setColumnIdentifiers(cabeceraTabla);

		refrescarDatosEstudiantes();

		for (Estudiante estudiante : Logica.getEstudiantes()) {

			dtm.addRow(estudiante.toArray());
		}

		table.setModel(dtm);

		Registro.append("La tabla Estudiantes se ha cargado.");

	}

	private List<Estudiante> cargarDatosEstudiantes(String sqlQuery) {

		Statement stmt;
		List<Estudiante> estudiantes = new ArrayList<Estudiante>();

		try {
			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				Estudiante estudiante = new Estudiante();

				estudiante.setCodigo(rs.getLong(1));
				estudiante.setNombre(rs.getString(2));
				estudiante.setDireccion(rs.getString(3));
				estudiante.setTelefono(rs.getString(4));
				estudiante.setFechaAlta(rs.getDate(5));

				/*
				 * Recordatorio: Estamos recuperando una fecha de una base de datos. Ese objeto
				 * es de tipo java.sql.Date, que es una clase que hereda de java.util.Date Y no
				 * implementa todos los métodos de java.util.Date.
				 * 
				 * Eso nos permite asignar sin errores la subclase (java.sql.Date) a la super
				 * clase (java.util.Date).
				 * 
				 * Esto también puede ocasionar shadowed.
				 * 
				 * Por lo que aquí podríamos hacer una conversión:
				 * 
				 * Date fechaTemporal = new Date(fechaAlta.getTime());
				 * 
				 * Sin embargo, la conversión la haremos en las clases POJO, mediante
				 * 'instanceof'
				 */

				estudiantes.add(estudiante);

			}

			return estudiantes;

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

		return estudiantes;
	}

	private void mostrarEntidades() {

		DefaultTableModel dtm = new DefaultTableModel();

		Registro.append(Registro.sepLineas);

		// Leemos las columnas de la tabla
		String[] cabeceraTabla = getNombreColumnas(sqlEntidades);
		dtm.setColumnIdentifiers(cabeceraTabla);

		refrescarDatosEntidades();

		for (Entidad entidad : Logica.getEntidades()) {

			dtm.addRow(entidad.toArray());
		}

		table.setModel(dtm);

		Registro.append("La tabla Entidades se ha cargado.");

	}

	private List<Entidad> cargarDatosEntidades(String sqlQuery) {

		Statement stmt;
		List<Entidad> entidades = new ArrayList<Entidad>();

		try {
			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				Entidad entidad = new Entidad();

				entidad.setCodigo(rs.getLong(1));
				entidad.setDescripcion(rs.getString(2));
				entidad.setTelefono(rs.getString(3));
				entidad.setDireccion(rs.getString(4));
				entidad.setContacto(rs.getString(5));

				entidades.add(entidad);

			}

			return entidades;

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

		return entidades;
	}

	private String[] getNombreColumnas(String sqlQuery) {

		Statement stmt;

		try {
			stmt = conexion.createStatement();

			ResultSet rs = stmt.executeQuery(sqlQuery);

			// Leemos las columnas del resultset mediante la metadata
			ResultSetMetaData rsmd = rs.getMetaData();

			int numeroColumnas = rsmd.getColumnCount();

			String[] columnas = new String[numeroColumnas];

			for (int i = 0; i < numeroColumnas; i++) {
				columnas[i] = rsmd.getColumnName(i + 1);
			}

			rs.close();
			stmt.close();

			return columnas;

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

		return new String[0];
	}

}
