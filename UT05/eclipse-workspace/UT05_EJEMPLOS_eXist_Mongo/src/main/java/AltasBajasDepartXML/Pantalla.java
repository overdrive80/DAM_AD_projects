package AltasBajasDepartXML;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Pantalla extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField depar = new JTextField(2);
	JTextField nombre = new JTextField();
	JTextField loc = new JTextField();

	JLabel etiqueta = new JLabel("");
	JLabel Lnumero = new JLabel("Nº de departamento:");
	JLabel Lnombre = new JLabel("Nombre:");
	JLabel Lloc = new JLabel("Localidad:");

	JLabel mensaje = new JLabel("-----------------------");

	JButton btnAlta = new JButton("Alta");
	JButton btnBaja = new JButton("Baja");
	JButton btnModif = new JButton("Modificación");
	JButton btnLimpiar = new JButton("Limpiar");
	JButton btnConsultar = new JButton("Consultar");
	JButton btnvertodos = new JButton("Ver todos");

	basedatosXML basedatos = new basedatosXML();

	// Inicia la pantalla
	public void iniciar() {
		
		setTitle("ALTAS BAJAS Y MODIFICACIONES DE DEPARTAMENTOS");
		basedatos.conectar();
		JPanel panel = new JPanel(false);
		JLabel cab = new JLabel("GESTI�N DE DEPARTAMENTOS");
		panel.setLayout(null);
		cab.setBounds(new Rectangle(30, 15, 200, 25));
		panel.add(cab);

		Lnumero.setBounds(new Rectangle(75, 50, 140, 20));
		depar.setBounds(new Rectangle(220, 50, 80, 20));

		Lnombre.setBounds(new Rectangle(75, 75, 120, 20));
		nombre.setBounds(new Rectangle(150, 75, 175, 20));

		Lloc.setBounds(new Rectangle(75, 105, 120, 20));
		loc.setBounds(new Rectangle(150, 105, 250, 20));

		etiqueta.setBounds(new Rectangle(100, 150, 400, 20));
		panel.add(etiqueta);

		panel.add(Lnumero);
		panel.add(depar, null);
		panel.add(Lnombre);
		panel.add(nombre);
		panel.add(Lloc);
		panel.add(loc);

		btnAlta.setBounds(new Rectangle(30, 205, 120, 30));
		btnBaja.setBounds(new Rectangle(150, 205, 120, 30));
		btnModif.setBounds(new Rectangle(270, 205, 120, 30));
		btnLimpiar.setBounds(new Rectangle(390, 205, 120, 30));
		btnConsultar.setBounds(new Rectangle(320, 50, 100, 20));
		btnvertodos.setBounds(new Rectangle(430, 50, 100, 20));

		panel.add(btnConsultar);
		panel.add(btnAlta);
		panel.add(btnBaja);
		panel.add(btnModif);
		panel.add(btnLimpiar);
		panel.add(btnvertodos);

		getContentPane().add(panel);
		pack();
		setSize(550, 300);
		setVisible(true);

		btnAlta.addActionListener(this); // pulsamos el boton
		btnBaja.addActionListener(this); // pulsamos el boton
		btnModif.addActionListener(this); // pulsamos el boton
		btnLimpiar.addActionListener(this); // pulsamos el boton
		btnConsultar.addActionListener(this);
		btnvertodos.addActionListener(this);
	}// fin iniciar pantalla
		

	public void actionPerformed(ActionEvent e) // accion cuando pulsamos botones
	{
		int dep;
		try {
			if (e.getSource() == btnvertodos)
				basedatos.vertodosporconsola();

			dep = Integer.parseInt(depar.getText());
			if (e.getSource() == btnAlta) { // SE PULSA EL BOTON ALTA
				if (basedatos.insertardep(dep, nombre.getText(), loc.getText()))
					etiqueta.setText(" DEPARTAMENTO INSERTADOOO. ");
				else
					etiqueta.setText(" YA EXISTEEE");
			}
			
			if (e.getSource() == btnBaja) { // SE PULSA EL BOTON BAJA
				if (basedatos.bajadep(dep))
					etiqueta.setText(" DEPARTAMENTO BORRADOOO. ");
				else
					etiqueta.setText(" NO SE PUEDE BORRAR, NO EXISTE");

			}
			
			if (e.getSource() == btnModif) { // SE PULSA EL BOTON MOFIFICACION
				if (basedatos.ModificaDep(dep, nombre.getText(), loc.getText()))
					etiqueta.setText(" DEPARTAMENTO MODIFICADOO. ");
				else
					etiqueta.setText(" NO SE PUEDE MODIFICAR, NO EXISTE");

			}
			if (e.getSource() == btnConsultar) { // SE PULSA EL BOTON CONSULTAR
				if (basedatos.consultar(dep)) {
					basedatos.visualizar(dep, nombre, loc);
					// Existe
				} else {
					etiqueta.setText(" DEPARTAMENTO NO EXISTE. ");
				}				
			}
			if (e.getSource() == btnLimpiar) {
				// SE PULSA LIMPIAR
				LimpiarCampos();
			}
		} catch (java.lang.NumberFormatException ex) {
			etiqueta.setText(" DEPARTAMENTO ERRONEO");
			return;
		}
	}// fin accion de botones

	void LimpiarCampos() {
		depar.setText("");
		nombre.setText("");
		loc.setText("");
		etiqueta.setText("");
	}

}// fin de la clase Pantalla
