package propiedadesRestringidas;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

//Definición del Oyente de cambios de propiedad
class OyenteRestringuidas implements Serializable, VetoableChangeListener {

	private static final long serialVersionUID = 1L;

  // Constructor
	public OyenteRestringuidas() {
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		
		Integer newValue = (Integer) evt.getNewValue();
		
		if (newValue % 2 != 0) {
			throw new PropertyVetoException("El valor dado no es par.", evt);
		}
		
		// Manejo del evento de cambio de propiedad
		// Se imprime el valor anterior y el nuevo valor
		System.out.println("Valor anterior: " + evt.getOldValue());
		System.out.println("Nuevo valor: " + newValue);
		
	}
}
