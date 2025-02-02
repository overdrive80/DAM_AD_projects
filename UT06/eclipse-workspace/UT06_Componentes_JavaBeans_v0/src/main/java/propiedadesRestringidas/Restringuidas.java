package propiedadesRestringidas;

//Clase para demostrar el uso del ComponenteBean y el Oyente
public class Restringuidas {
	public static void main(String[] args) {
		ComponenteRestringidas componente = new ComponenteRestringidas();
		OyenteRestringuidas listener = new OyenteRestringuidas();

		// Se añade el oyente al componente
		componente.addVetoableChangeListener(listener);

		// Se cambia la propiedad del componente
		componente.setPropiedad(5);
		
		componente.setPropiedad(10);
	}

}



