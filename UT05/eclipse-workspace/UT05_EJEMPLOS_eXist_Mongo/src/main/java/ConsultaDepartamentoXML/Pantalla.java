package ConsultaDepartamentoXML;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;

import org.xmldb.api.modules.XPathQueryService;

@SuppressWarnings("unused")
public class Pantalla extends JFrame implements ActionListener {
	
	
	public Pantalla() {
	}

    private static final long serialVersionUID = 1L;
    JLabel etiqueta = new JLabel("");
    JLabel label = new JLabel("Introduce Nº de departamento:");
    JTextField depar = new JTextField(2);
    JButton boton = new JButton("Consultar");
    JTextArea area;

    public void iniciar() {
        setTitle("EMPLEADOS POR DEPARTAMENTO");     
        JPanel panel = new JPanel(false);

        panel.setLayout(null);
        label.setBounds(new Rectangle(30, 15, 200, 25));
        panel.add(label);

        depar.setBounds(new Rectangle(219, 15, 27, 20));
        panel.add(depar);

        boton.setBounds(new Rectangle(260, 15, 100, 20));
        panel.add(boton);

        etiqueta.setBounds(new Rectangle(30, 40, 300, 20));
        panel.add(etiqueta);
     
        area = new JTextArea();     
       
        area.setFont(new Font("Courier", Font.PLAIN, 12));
        area.setEditable(false);
        area.setForeground(Color.blue);
        
        JScrollPane scroll = new JScrollPane(area);         
        scroll.setBounds(new Rectangle(20, 70, 300, 300));     
        panel.add(scroll);
        getContentPane().add(panel);
        
        pack();
        setSize(400, 470);
        setVisible(true);

        boton.addActionListener(this);  //pulsamos el boton    
    }

    //accion cuando pulsamos botones 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton) { //SE PULSA EL BOTON    	
            etiqueta.setText(" Departamento a consultar: " + depar.getText());
            int dep;
            try {
                dep = Integer.parseInt(depar.getText());
                VisualizarDep(dep);//visualiza datos del departamento
            } catch (java.lang.NumberFormatException ex) {
                etiqueta.setText(" DEPARTAMENTO ERRONEO");
            }
        }
    }//

    //LOCALIZAR DATOS DEL DEPARTAMENTO
    void VisualizarDep(int dep) { //pinto datos del dep
        etiqueta.setText(" VISUALIZAR EMPLEADOS DEL DEPARTAMENTO: " + dep);
        String res = "";
        int numemple = 0;

        basedatosXML basedatos = new basedatosXML();
        basedatos.conectar();
        XPathQueryService servicio = basedatos.obtenerservicio();

        try {
            ResourceSet result = servicio.query("for $b in /EMPLEADOS/EMP_ROW[DEPT_NO='" + dep + "']/concat ("
                    + "' ',APELLIDO,'    ',OFICIO) return $b");

            ResourceIterator i;
            i = result.getIterator();
            if (!i.hasMoreResources()) {
                System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTA MAL ESCRITA");
            }

            numemple = 0;

            while (i.hasMoreResources()) {
                Resource r = i.nextResource();
                numemple = numemple + 1;
                //res = res.concat((String) r.getContent());
                res = res.concat((String) r.getContent() + "\n");
            }

            if ((res.trim()).isEmpty()) {
                numemple = 0;
            }

            System.out.println("Número de empleados: " + numemple);
          
            VisualizarEmp(res, numemple);

        } catch (Exception e) {
            System.out.println("ERROOOOORR EN LA CONSULTA");
            e.printStackTrace();
        }

    }//fin VisualizarDep

    //LOCALIZAR DATOS DE LOS EMPLEADOS
    void VisualizarEmp(String dep, int num) //pinto datos de los empleados del depart
    {         
        area.setText("");
        //  
        area.append(" Número de empleados: " + num + "\n");
        area.append(" -------------------------------------");
        if (num == 0) {
            	area.append("\n NO HAY EMPLEADOS EN EL DEPARTAMENTO\n");
        } else {
            area.append("\n APELLIDO\tOFICIO");
            area.append("\n =========================== \n");
            area.append(dep);
        }

    }//fin VisualizarEmp          
}//fin de la clase Pantalla
