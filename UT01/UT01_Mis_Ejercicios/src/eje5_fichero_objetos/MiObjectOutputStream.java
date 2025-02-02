package eje5_fichero_objetos;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/* Para no tener problemas con las cabeceras de los ficheros de objetos y evitar el error  
 * StreamCorruptedException, creamos una clase con nuestro propio 
 * ObjectOutputStream, heredando del original y redefiniendo el método 
 * writeStreamHeader() vacío, para que no haga nada.
 * Esta clase se llama MiObjectOutputStream que nos quedará así:
*/
public class MiObjectOutputStream extends ObjectOutputStream
{    // Constructor que recibe OutputStream 
    public MiObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }
    // Constructor sin parámetros 
    protected MiObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }
    // Redefinición del método de escribir la cabecera para que no haga nada. 
    protected void writeStreamHeader() throws IOException
    {  }
} // fin MiObjectOutputStream