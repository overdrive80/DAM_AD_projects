package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.*;
import gestionHibernate.Conexion;
import gestionHibernate.LoggerHibernate;
import logica.GestionErrores;
import logica.Operaciones;
import logica.Registro;

public class Main_lucas_torrijos_israel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEval;
	private JTextField txtNota;
	private static SessionFactory sesionFactory;
	private JComboBox<String> cboAlumno;
	private JComboBox<String> cboAsigna;
	private JTextArea txtAreaLog;
	private Operaciones operaciones = new Operaciones(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Main_lucas_torrijos_israel frame = new Main_lucas_torrijos_israel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main_lucas_torrijos_israel() {
		setResizable(false);
		setTitle("PRÁCTICA 3. Israel Lucas Torrijos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("PRÁCTICA 3. REALIZADA POR: Israel Lucas Torrijos");
		lblTitulo.setForeground(new Color(0, 0, 255));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblTitulo.setBounds(23, 11, 821, 23);
		contentPane.add(lblTitulo);

		JPanel JPanelEje1 = new JPanel();
		JPanelEje1.setBackground(new Color(255, 255, 235));
		JPanelEje1.setBounds(23, 46, 404, 234);
		contentPane.add(JPanelEje1);
		JPanelEje1.setLayout(null);

		JLabel lblEje1Titulo = new JLabel("EJERCICIO 1. INSERTAR EVALUACIONES:");
		lblEje1Titulo.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblEje1Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEje1Titulo.setBounds(22, 11, 364, 14);
		JPanelEje1.add(lblEje1Titulo);

		JLabel lblEval = new JLabel("Teclea evaluación (1, 2, 3):");
		lblEval.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblEval.setBounds(22, 47, 152, 14);
		JPanelEje1.add(lblEval);

		JLabel lblSelAlumno = new JLabel("Selecciona alumno:");
		lblSelAlumno.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblSelAlumno.setBounds(22, 80, 107, 14);
		JPanelEje1.add(lblSelAlumno);

		JLabel lblSelAsigna = new JLabel("Selecciona asignatura:");
		lblSelAsigna.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblSelAsigna.setBounds(22, 117, 129, 14);
		JPanelEje1.add(lblSelAsigna);

		JLabel lblNota = new JLabel("Teclea nota entre 1 y 10:");
		lblNota.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblNota.setBounds(22, 155, 138, 14);
		JPanelEje1.add(lblNota);

		JButton btnInsertarNota = new JButton("Insertar");
		btnInsertarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.insertarNota();
			}
		});
		btnInsertarNota.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		btnInsertarNota.setBounds(22, 197, 107, 23);
		JPanelEje1.add(btnInsertarNota);

		JButton btnComprobar = new JButton("Comprobar");
		btnComprobar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.comprobarDatosNotas();
			}
		});
		btnComprobar.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		btnComprobar.setBounds(141, 198, 107, 23);
		JPanelEje1.add(btnComprobar);

		txtEval = new JTextField();
		txtEval.setHorizontalAlignment(SwingConstants.CENTER);
		txtEval.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		txtEval.setBounds(161, 44, 225, 20);
		JPanelEje1.add(txtEval);
		txtEval.setColumns(10);

		cboAlumno = new JComboBox<String>();
		cboAlumno.setToolTipText("Selecciona alumno");
		cboAlumno.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		cboAlumno.setBounds(161, 79, 225, 20);
		JPanelEje1.add(cboAlumno);

		cboAsigna = new JComboBox<String>();
		cboAsigna.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		cboAsigna.setBounds(161, 113, 225, 20);
		JPanelEje1.add(cboAsigna);

		txtNota = new JTextField();
		txtNota.setHorizontalAlignment(SwingConstants.CENTER);
		txtNota.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		txtNota.setColumns(10);
		txtNota.setBounds(161, 152, 225, 20);
		JPanelEje1.add(txtNota);

		JPanel JPanelEje2 = new JPanel();
		JPanelEje2.setLayout(null);
		JPanelEje2.setBackground(new Color(255, 255, 235));
		JPanelEje2.setBounds(440, 45, 404, 53);
		contentPane.add(JPanelEje2);

		JLabel lblEje2Titulo = new JLabel("EJERCICIO 2. ACTUALIZAR CONTADORES");
		lblEje2Titulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblEje2Titulo.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblEje2Titulo.setBounds(12, 19, 210, 14);
		JPanelEje2.add(lblEje2Titulo);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.actualizarContadores();
			}
		});
		btnActualizar.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		btnActualizar.setBounds(275, 15, 107, 23);
		JPanelEje2.add(btnActualizar);

		JPanel JPanelEje3 = new JPanel();
		JPanelEje3.setLayout(null);
		JPanelEje3.setBackground(new Color(255, 255, 235));
		JPanelEje3.setBounds(440, 109, 404, 81);
		contentPane.add(JPanelEje3);

		JLabel lblEje3Titulo = new JLabel("EJERCICIO 3. MOSTRAR DATOS DE CURSOS");
		lblEje3Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEje3Titulo.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblEje3Titulo.setBounds(10, 13, 384, 14);
		JPanelEje3.add(lblEje3Titulo);

		JButton btnMostrarDatosCursos = new JButton("Mostrar datos de todos los cursos");
		btnMostrarDatosCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.mostrarDatosCursos();
			}
		});
		btnMostrarDatosCursos.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		btnMostrarDatosCursos.setBounds(92, 40, 221, 23);
		JPanelEje3.add(btnMostrarDatosCursos);

		JPanel JPanelEje4 = new JPanel();
		JPanelEje4.setLayout(null);
		JPanelEje4.setBackground(new Color(255, 255, 235));
		JPanelEje4.setBounds(440, 202, 404, 78);
		contentPane.add(JPanelEje4);

		JLabel lblEje4Titulo = new JLabel("EJERCICIO 4. MOSTRAR ESTADÍSTICA DE CENTROS");
		lblEje4Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEje4Titulo.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblEje4Titulo.setBounds(12, 13, 382, 14);
		JPanelEje4.add(lblEje4Titulo);

		JButton btnMostrarEstadistica = new JButton("Mostrar estadística");
		btnMostrarEstadistica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.mostrarEstadistica();
			}
		});
		btnMostrarEstadistica.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		btnMostrarEstadistica.setBounds(124, 38, 153, 23);
		JPanelEje4.add(btnMostrarEstadistica);

		JSeparator separator = new JSeparator();
		separator.setBounds(23, 291, 820, 2);
		contentPane.add(separator);

		JLabel lblTextArea = new JLabel("TextArea - Área para mostrar las informaciones");
		lblTextArea.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblTextArea.setBounds(23, 296, 290, 14);
		contentPane.add(lblTextArea);

		JScrollPane SPanelTextArea = new JScrollPane();
		SPanelTextArea.setBounds(23, 315, 821, 295);
		contentPane.add(SPanelTextArea);

		txtAreaLog = new JTextArea();
		txtAreaLog.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtAreaLog.setEditable(false);
		SPanelTextArea.setViewportView(txtAreaLog);

		// --------------------------------- AREA PERSONALIZADA
		// Establecemos el JTextArea en la clase que controla los errores
		// GestionErrores.setAreaTexto(txtLogging);
		Registro.setLogger(txtAreaLog);

		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
				
		Operaciones.setSesionFactory(sesionFactory);
		
		setCboAlumnos();
		setCboAsignaturas();
	}

	private void setCboAlumnos() {

		try {
			Session session = sesionFactory.openSession();

			String strHQL = "from Alumnos order by numAlumno";

			Query<?> query = session.createQuery(strHQL, Alumnos.class);

			List<?> listado = query.getResultList();
			Iterator<?> it = listado.iterator();

			while (it.hasNext()) {
				Alumnos fila = (Alumnos) it.next();

				String elemento = String.format("%d - %s", fila.getNumAlumno(), fila.getNombre());
				cboAlumno.addItem(elemento);

			}

			session.close();
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

	}
	
	private void setCboAsignaturas() {

		try {
			Session session = sesionFactory.openSession();

			String strHQL = "from Asignaturas order by codAsig";

			Query<?> query = session.createQuery(strHQL, Asignaturas.class);

			List<?> listado = query.getResultList();
			Iterator<?> it = listado.iterator();

			while (it.hasNext()) {
				Asignaturas fila = (Asignaturas) it.next();

				String elemento = String.format("%d - %s", fila.getCodAsig(), fila.getNombre());
				cboAsigna.addItem(elemento);

			}

			session.close();
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

	}

	public JTextField getTxtEval() {
		return txtEval;
	}

	public void setTxtEval(JTextField txtEval) {
		this.txtEval = txtEval;
	}

	public JTextField getTxtNota() {
		return txtNota;
	}

	public void setTxtNota(JTextField txtNota) {
		this.txtNota = txtNota;
	}

	public JComboBox<String> getCboAlumno() {
		return cboAlumno;
	}

	public void setCboAlumno(JComboBox<String> cboAlumno) {
		this.cboAlumno = cboAlumno;
	}

	public JComboBox<String> getCboAsigna() {
		return cboAsigna;
	}

	public void setCboAsigna(JComboBox<String> cboAsigna) {
		this.cboAsigna = cboAsigna;
	}

	public static SessionFactory getSesionFactory() {
		return sesionFactory;
	}
	
	
}
