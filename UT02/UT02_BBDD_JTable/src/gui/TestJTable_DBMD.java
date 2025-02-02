package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TestJTable_DBMD extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tblDepartamentos;
    private String username = "EJEMPLO";
    private String password = "ejemplo";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TestJTable_DBMD frame = new TestJTable_DBMD();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TestJTable_DBMD() {
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

        DefaultTableModel modelo = inicializarTabla();
        agregarDatosATabla(modelo);

        tblDepartamentos.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("rawtypes")
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("He pulsado en la fila " + tblDepartamentos.getSelectedRow());
                Vector datostabla = modelo.getDataVector();
                
                List datosFila = (List) datostabla.get(tblDepartamentos.getSelectedRow());
                
                if (datosFila.size() > 0) {
                    for (int i = 0; i < datosFila.size(); i++) {
                        System.out.println("Dato " + i + " : " + datosFila.get(i));
                    }
                }
            }
        });
    }

    private DefaultTableModel inicializarTabla() {
        DefaultTableModel dtm = new DefaultTableModel();
        Vector<String> columnas = getColumnasDepartamentos();

        if (columnas != null) {
            dtm.setColumnIdentifiers(columnas);
            tblDepartamentos.setModel(dtm);
        } else {
            System.out.println("Error al obtener los nombres de las columnas.");
        }

        return dtm;
    }

    private Vector<String> getColumnasDepartamentos() {
        Connection conexion = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);

            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet resultSet = dbmd.getColumns(null, "EJEMPLO", "DEPARTAMENTOS", null);

            Vector<String> columnNames = new Vector<>();

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }

            return columnNames;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return new Vector<>(); 
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void agregarDatosATabla(DefaultTableModel model) {
        Connection conexion = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);

            String sql = "SELECT * FROM EJEMPLO.DEPARTAMENTOS";
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int columnCount = model.getColumnCount();

            while (resultSet.next()) {
                Vector<Object> rowData = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    rowData.add(value);
                }
                model.addRow(rowData);
            }

            Color fg = Color.PINK;
            tblDepartamentos.setBackground(fg);
            tblDepartamentos.setForeground(Color.BLUE);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
	
	/* private void llenatJTableDepar() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");
			Statement sentencia = conexion.createStatement();
			String consulta = "SELECT *  FROM departamentos ";
			ResultSet resul = sentencia.executeQuery(consulta);

			ResultSetMetaData rsmd = resul.getMetaData();

			// Número de columnas
			int nColumnas = rsmd.getColumnCount();

			// Número de filas
			String consulta2 = "select count(*) from  departamentos";

			Statement sentencia2 = conexion.createStatement();
			ResultSet resul2 = sentencia2.executeQuery(consulta2);
			resul2.next();
			int filas = resul2.getInt(1);
			resul2.close();
			sentencia2.close();

			// Se obtiene cada una de las etiquetas para cada columna
			String[] etiquetas = new String[nColumnas];
			for (int i = 1; i <= nColumnas; i++) {
				rsmd.getColumnName(i);
				System.out.println("Añado la columna " + rsmd.getColumnName(i).toUpperCase());
				etiquetas[i - 1] = rsmd.getColumnName(i).toUpperCase();
			}
			System.out.println("Filas: " + filas + ", columnas: " + nColumnas);

			// Recorremos el resul para cargar las filas de la consulta al array
			// bidimensional de objetos
			int numeroFila = 0;
			Object[][] datos = new Object[filas][nColumnas];
			resul = sentencia.executeQuery(consulta);
			while (resul.next()) {
				// Bucle para cada fila, añadir las columnas
				for (int i = 0; i < nColumnas; i++) {
					datos[numeroFila][i] = resul.getObject(i + 1);
					System.out.println("Añado la columna " + i + ", datos " + resul.getString(i + 1));
				}
				numeroFila++;
			}

			// Asignamos los datos al modelo
			DefaultTableModel modelo = new DefaultTableModel(datos, etiquetas);
			modelo.setColumnIdentifiers(etiquetas); // esto puede sobrar
			modelo.setDataVector(datos, etiquetas); // esto puede sobrar


			// Asignamos el modelo a la tabla
			tblDepartamentos.setModel(modelo);
			Color fg = Color.PINK;
			tblDepartamentos.setBackground(fg);
			tblDepartamentos.setForeground(Color.BLUE);

			resul.close();
			conexion.close();

		} catch (ClassNotFoundException cn) {
			System.out.println("-------------------------------------");
			System.out.println("ERRORRRR EN EL DRIVER MYSQL");
			System.out.println(cn);
			System.out.println("-------------------------------------");
		} catch (SQLException e) {
			System.out.println("-------------------------------------");
			// e.printStackTrace();
			System.out.println("Código de error: " + e.getErrorCode());
			System.out.println("Mensaje de error: " + e.getMessage());
			System.out.println("-------------------------------------");
		}
	} */

