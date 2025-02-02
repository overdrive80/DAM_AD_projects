package propiedadesRestringidas;

import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

class ComponenteRestringidas implements Serializable {
	private static final long serialVersionUID = 1L;

	// Objeto que dispara eventos de cambio de propiedad
	private VetoableChangeSupport VetoableCSupport;

	// Propiedad
	private int propiedad;

	// Constructor
	public ComponenteRestringidas() {
		// Se crea el objeto que permitirá notificar, a los listeners registrados,
		// los cambios en las propiedades de la clase pasada como argumento (this)
		VetoableCSupport = new VetoableChangeSupport(this);
	}
	
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		
		VetoableCSupport.addVetoableChangeListener(listener);
	}
	
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		VetoableCSupport.removeVetoableChangeListener(listener);
	}


	// Método que permite modificar el valor de la propiedad
	// El objeto de notificación de cambios dispara un evento
	public void setPropiedad(int nuevoValor) {
		int anteriorValor = propiedad;
		propiedad = nuevoValor; // Se actualiza el valor de la propiedad

		// Se dispara un evento de la clase 'PropertyChangeEvent', y permite
		// notificar a los listeners registrados los cambios en la propiedad
		try {
			VetoableCSupport.fireVetoableChange("nombre_propiedad", anteriorValor, nuevoValor);
		} catch (PropertyVetoException e) {
			// Deshacemos cambios
			propiedad = anteriorValor;
			System.out.println(e.getMessage() + " Valor pasado: " + e.getPropertyChangeEvent().getNewValue());
		}
		

	}
}
