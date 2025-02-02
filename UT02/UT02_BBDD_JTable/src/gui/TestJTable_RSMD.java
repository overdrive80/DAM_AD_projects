package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TestJTable_RSMD extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblDepartamentos;
	private String username = "EJEMPLO";
	private String password = "ejemplo";

	private Connection conexion = null;
	private DefaultTableModel modelo = null;

	private String sqlDepart = "SELECT * FROM DEPARTAMENTOS";

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TestJTable_RSMD frame = new TestJTable_RSMD();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TestJTable_RSMD() {
		/*** CONSTRUCCION ASPECTO ***/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("JTable - Tabla departamentos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel.setBounds(47, 11, 329, 36);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 414, 247);
		contentPane.add(scrollPane);

		tblDepartamentos = new JTable();
		scrollPane.setViewportView(tblDepartamentos);

		/*** CREAR CONEXION ***/
		if (conectarBBDD()) {
			// cargarDatosEnTabla();
		} else {
			JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.");
		}

		/*** MODELO TABLA ***/
		crearModelo();
	}

	private boolean conectarBBDD() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);

			return true;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void crearModelo() {
		modelo = new DefaultTableModel();

		try {
			Statement stmt = conexion.createStatement();
			ResultSet tabla = stmt.executeQuery(sqlDepart);
			ResultSetMetaData rsmd = tabla.getMetaData();

			/*** COLUMNAS ***/
			int numCol = rsmd.getColumnCount();

			for (int i = 1; i <= numCol; i++) {
				modelo.addColumn(rsmd.getColumnName(i));
			}

			/*** FILAS ***/
			while (tabla.next()) {
				// Creamos un vector de objetos para las columnas
				Object[] campos = new Object[numCol];

				// Recorremos los campos de la fila actual del resultset
				for (int j = 1; j <= numCol; j++) {
					campos[j - 1] = tabla.getObject(j);
				}

				modelo.addRow(campos);
			}

			tblDepartamentos.setModel(modelo);

			tabla.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
