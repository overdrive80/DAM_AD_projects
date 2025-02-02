package propiedadesLigadas;

//Clase para demostrar el uso del ComponenteBean y el Oyente
public class Ligadas {
	public static void main(String[] args) {
		propiedadesLigadas.Componente componente = new Componente();
		Oyente listener = new Oyente();

		// Se añade el oyente al componente
		componente.addPropertyChangeListener(listener);

		// Se cambia la propiedad del componente
		componente.setPropiedad(6480);
	}

}



