package propiedadesLigadas;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

class Componente implements Serializable {
	private static final long serialVersionUID = 1L;

	// Objeto que dispara eventos de cambio de propiedad
	private PropertyChangeSupport propertyCSupport;

	// Propiedad
	private int propiedad;

	// Constructor
	public Componente() {
		// Se crea el objeto que permitirá notificar, a los listeners registrados,
		// los cambios en las propiedades de la clase pasada como argumento (this)
		propertyCSupport = new PropertyChangeSupport(this);
	}

	// Método para añadir un listener al objeto que notifica los cambios en las
	// propiedades
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyCSupport.addPropertyChangeListener(listener);
	}

	// Método para eliminar un listener del objeto que notifica los cambios en las
	// propiedades
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyCSupport.removePropertyChangeListener(listener);
	}

	// Método que permite modificar el valor de la propiedad
	// El objeto de notificación de cambios dispara un evento
	public void setPropiedad(int nuevoValor) {
		int anteriorValor = propiedad;
		propiedad = nuevoValor; // Se actualiza el valor de la propiedad

		// Se dispara un evento de la clase 'PropertyChangeEvent', y permite
		// notificar a los listeners registrados los cambios en la propiedad
		propertyCSupport.firePropertyChange("nombre_propiedad", anteriorValor, nuevoValor);
	}
}
