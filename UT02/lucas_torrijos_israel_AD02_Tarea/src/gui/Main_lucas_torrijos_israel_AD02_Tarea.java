package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logica.GestionErrores;
import logica.Operaciones;
import logica.Registro;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Main_lucas_torrijos_israel_AD02_Tarea extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtFecAlta;
	private JTextArea txtLogging;
	private JComboBox<String> cboProyecto;
	private Connection conexion;
	private Operaciones operaciones = new Operaciones(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Main_lucas_torrijos_israel_AD02_Tarea frame = new Main_lucas_torrijos_israel_AD02_Tarea();
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
	public Main_lucas_torrijos_israel_AD02_Tarea() {
		setResizable(false);
		setTitle("OPERACIONES CON PROYECTOS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 984, 873);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEjer1 = new JLabel("EJERCICIO 1. INSERTAR DATOS DE ESTUDIANTES:");
		lblEjer1.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEjer1.setBounds(26, 45, 399, 29);
		contentPane.add(lblEjer1);

		JLabel lblTitulo = new JLabel("PRÁCTICA 2. REALIZADA POR: Israel Lucas Torrijos");
		lblTitulo.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblTitulo.setForeground(new Color(0, 0, 255));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(176, 11, 510, 29);
		contentPane.add(lblTitulo);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtNombre.setBounds(26, 100, 171, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblNombre.setBounds(27, 85, 100, 14);
		contentPane.add(lblNombre);

		txtDireccion = new JTextField();
		txtDireccion.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(227, 100, 240, 20);
		contentPane.add(txtDireccion);

		JLabel lblDireccion = new JLabel("DIRECCION");
		lblDireccion.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblDireccion.setBounds(228, 85, 100, 14);
		contentPane.add(lblDireccion);

		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(499, 100, 112, 20);
		contentPane.add(txtTelefono);

		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblTelefono.setBounds(500, 85, 100, 14);
		contentPane.add(lblTelefono);

		JLabel lblFecAlta = new JLabel("FECHA ALTA");
		lblFecAlta.setFont(new Font("Century Gothic", Font.BOLD, 10));
		lblFecAlta.setBounds(645, 85, 100, 14);
		contentPane.add(lblFecAlta);

		txtFecAlta = new JTextField();
		txtFecAlta.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		txtFecAlta.setHorizontalAlignment(SwingConstants.CENTER);
		txtFecAlta.setEditable(false);
		txtFecAlta.setEnabled(false);
		txtFecAlta.setColumns(10);
		txtFecAlta.setBounds(644, 100, 112, 20);
		contentPane.add(txtFecAlta);

		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.InsertarAlumno();
			}
		});
		btnInsertar.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnInsertar.setBounds(822, 63, 124, 57);
		contentPane.add(btnInsertar);

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(new Color(0, 0, 160));
		sep1.setBounds(26, 141, 920, 2);
		contentPane.add(sep1);

		JLabel lblEjer2 = new JLabel("EJERCICIO 2. LISTAR DATOS DEL PROYECTO");
		lblEjer2.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEjer2.setBounds(26, 170, 294, 29);
		contentPane.add(lblEjer2);

		cboProyecto = new JComboBox<String>();
		cboProyecto.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		cboProyecto.setBounds(330, 170, 355, 29);
		contentPane.add(cboProyecto);

		JButton btnMostrarProyecto = new JButton("Mostrar proyecto");
		btnMostrarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.mostrarProyecto();
			}
		});
		btnMostrarProyecto.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnMostrarProyecto.setBounds(754, 154, 192, 57);
		contentPane.add(btnMostrarProyecto);

		JSeparator sep2 = new JSeparator();
		sep2.setForeground(new Color(0, 0, 160));
		sep2.setBounds(26, 228, 920, 2);
		contentPane.add(sep2);

		JLabel lblEjer3 = new JLabel("EJERCICIO 3. AÑADIR COLUMNAS A LA TABLA PROYECTOS Y MOSTRAR LOS DATOS\r\n");
		lblEjer3.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEjer3.setBounds(26, 262, 557, 29);
		contentPane.add(lblEjer3);

		JButton btnAdicionarColumnas = new JButton("Añadir columnas");
		btnAdicionarColumnas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.insertarColumnas();
			}
		});
		btnAdicionarColumnas.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnAdicionarColumnas.setBounds(754, 247, 192, 57);
		contentPane.add(btnAdicionarColumnas);

		JSeparator sep3 = new JSeparator();
		sep3.setForeground(new Color(0, 0, 160));
		sep3.setBounds(26, 318, 920, 2);
		contentPane.add(sep3);

		JLabel lblEjer4 = new JLabel("EJERCICIO 4. GENERAR PDF CON LA INFORMACIÓN DE EMPRESAS\r\n");
		lblEjer4.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEjer4.setBounds(26, 351, 557, 29);
		contentPane.add(lblEjer4);

		JButton btnGenerarPfd = new JButton("Generar PDF");
		btnGenerarPfd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.generarInforme();
			}
		});
		btnGenerarPfd.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnGenerarPfd.setBounds(754, 336, 192, 57);
		contentPane.add(btnGenerarPfd);

		JSeparator sep4 = new JSeparator();
		sep4.setForeground(new Color(0, 0, 160));
		sep4.setBounds(26, 407, 920, 2);
		contentPane.add(sep4);

		JLabel lblEjer5 = new JLabel("EJERCICIO 5. DATOS DE TABLAS\r\n");
		lblEjer5.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblEjer5.setBounds(26, 440, 557, 29);
		contentPane.add(lblEjer5);

		JButton btnMostrarTablas = new JButton("Mostrar tablas");
		btnMostrarTablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operaciones.verTablas();
			}
		});
		btnMostrarTablas.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btnMostrarTablas.setBounds(754, 425, 192, 57);
		contentPane.add(btnMostrarTablas);

		JSeparator sep5 = new JSeparator();
		sep5.setForeground(new Color(0, 0, 160));
		sep5.setBounds(26, 498, 920, 2);
		contentPane.add(sep5);

		JLabel lblLog = new JLabel("Área de texto para mostrar las informaciones:");
		lblLog.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblLog.setBounds(26, 505, 557, 29);
		contentPane.add(lblLog);

		JScrollPane scrollLogging = new JScrollPane();
		scrollLogging.setBounds(26, 539, 920, 284);
		contentPane.add(scrollLogging);

		txtLogging = new JTextArea();
		scrollLogging.setViewportView(txtLogging);
		txtLogging.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 160)));
		txtLogging.setEditable(false);

		// Establecemos el JTextArea en la clase que controla los errores
		// GestionErrores.setAreaTexto(txtLogging);
		Registro.setLogger(txtLogging);
		
		// Establecemos la conexión con la base de datos
		try {
			conexion = establecerConexion();

			setValorFecha();
			setValoresCboProyecto();
			
			operaciones.setConexion();

		} catch (SQLException e) {
			GestionErrores.controlErroresSQL(e);
		} catch (Exception e) {
			GestionErrores.controlErroresGenericos(e);
		}

	}

	private Connection establecerConexion() throws SQLException, Exception {

		// Cargar el driver
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// Establecemos la conexion con la BD
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROYECTOS", "proyectos");

		return conn;

	}

	private void setValorFecha() {

		// Inicializar fecha
		LocalDate fechaHoy = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateada = fechaHoy.format(formatter);

		txtFecAlta.setText(fechaFormateada);

	}

	private void setValoresCboProyecto() throws SQLException {

		Statement stmt = conexion.createStatement();

		String sqlQuery = "SELECT CODIGOPROYECTO, NOMBRE FROM PROYECTOS";

		ResultSet rs = stmt.executeQuery(sqlQuery);

		while (rs.next()) {
			int codProyecto = rs.getInt(1);
			String nomProyecto = rs.getString(2);

			String fila = String.format("%d - %s", codProyecto, nomProyecto);
			cboProyecto.addItem(fila);

		}

		rs.close();
		stmt.close();

	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(JTextField txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtFecAlta() {
		return txtFecAlta;
	}

	public void setTxtFecAlta(JTextField txtFecAlta) {
		this.txtFecAlta = txtFecAlta;
	}

	public JTextArea getTxtLogging() {
		return txtLogging;
	}

	public void setTxtLogging(JTextArea txtLogging) {
		this.txtLogging = txtLogging;
	}

	public JComboBox<String> getCboProyecto() {
		return cboProyecto;
	}

	public void setCboProyecto(JComboBox<String> cboProyecto) {
		this.cboProyecto = cboProyecto;
	}

	public Connection getConexion() {
		return conexion;
	}

}
