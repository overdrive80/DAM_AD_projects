package ejemplo;

import java.sql.*;
import java.util.*;

/* Las variables de referencia son diferentes; La variable de referencia solo hace referencia a un objeto, pero no contiene el objeto en sí.
Y la conversión de una variable de referencia no toca el objeto al que se refiere, 
sino que solo etiqueta este objeto de otra manera, ampliando o reduciendo las oportunidades de trabajar con él. 
La conversión ascendente reduce la lista de métodos y propiedades disponibles para este objeto, y la conversión descendente puede ampliarla. */

@SuppressWarnings("unused")
public class Casting_Referencias{

	public static void main(String[] args) {
	    // Upcasting implicito
		// Normalmente, el compilador realiza implícitamente la conversión ascendente.
	    O objetoClaseO = new A();
	      objetoClaseO = new C();
	      objetoClaseO = new B();
	    B objetoClaseB = new C();
	    
	    // Upcasting explicito
	    objetoClaseO = (O) new A();
	    objetoClaseO = (O) new C();
	    objetoClaseO = (O) new B();
	    objetoClaseB = (B) new C();
	    
	    if (objetoClaseO instanceof B) {
	    	System.out.println("La referencia de la superclase O tiene una instancia de la subclase B");
	    }

	    //Downcasting explicito. Es el casting de una superclase a una subclase   
	    // Partimos de una referencia de superclase que encapsula una instancia de su subclase
	    objetoClaseO = new B();
	    
	    //Para poder usar metodos de la clase B hacemos downcasting explicito
	    //Modo 1
	    ((B) objetoClaseO).metodoClaseB();
	    
	    //Modo 2
	    objetoClaseB = (B) objetoClaseO;
	    objetoClaseB.metodoClaseB();
	   
	    //Downcasting implicito
	    /*  En Java, el downcasting (casting de una superclase a una subclase) no se realiza
	     *  de forma implícita para variables de tipo referencia. Por razones de seguridad 
	     *  y para prevenir errores en tiempo de ejecución, Java requiere que los downcastings
	     *   se realicen de manera explícita mediante el uso del operador de casting (tipo).
	     */

	}
}