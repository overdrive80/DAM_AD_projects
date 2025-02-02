package propiedadesLigadas;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

//Definición del Oyente de cambios de propiedad
class Oyente implements Serializable, PropertyChangeListener {

	private static final long serialVersionUID = 1L;

  // Constructor
	public Oyente() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Manejo del evento de cambio de propiedad
		// Se imprime el valor anterior y el nuevo valor
		System.out.println("Valor anterior: " + evt.getOldValue());
		System.out.println("Nuevo valor: " + evt.getNewValue());
	}
}
