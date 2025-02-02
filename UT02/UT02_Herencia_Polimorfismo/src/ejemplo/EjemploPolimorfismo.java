package ejemplo;

public class EjemploPolimorfismo {
    public static void main(String[] args) {
        O objetoClaseO = new O();
        A objetoClaseA = new A();
        B objetoClaseB = new B();
        C objetoClaseC = new C();
        
        /*** EJEMPLO 1 ***/

        // Polimorfismo: obj_clase_A es tratada como instancia de la superclase O
        O referenciaPolimorfica = objetoClaseA;

        // Acceso al método de la superclase O
        referenciaPolimorfica.metodo(); // Salida: Método de la clase A

        // Sin polimorfismo: acceso directo al método de la subclase O
        objetoClaseO.metodo(); // Salida: Método de la clase O
        
        /*** EJEMPLO 2 ***/

        // Polimorfismo: obj_clase_C es tratada como instancia de la superclase O
        referenciaPolimorfica = objetoClaseC;

        // Acceso al método de la superclase O
        referenciaPolimorfica.metodo(); // Salida: Método de la clase C

        // Sin polimorfismo: acceso directo al método de la subclase B
        objetoClaseB.metodo(); // Salida: Método de la clase B
        
	    // Declaraciones incorrectas
//	    objetoClaseB = new O();	    
//	    objetoClaseC = new B();
    }
}


    

