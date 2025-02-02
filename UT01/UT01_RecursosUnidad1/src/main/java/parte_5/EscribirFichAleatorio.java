package parte_5;


import java.io.*;
import java.util.Arrays;
public class EscribirFichAleatorio {
  public static void main(String[] args) throws IOException { 
	  
	  
   File f = new File("AleatorioEmple.dat");
   RandomAccessFile raf = new RandomAccessFile(f, "rw");

   String apellidos[] = {"FERNANDEZ","GIL","LOPEZ"}; 
   int dep[] = {10, 20, 30};       
   Double salario[]={1000.45, 2400.60, 3000.0};
   
   //StringBuffer buffer = null;
   int numEmple = apellidos.length;
   
   for (int i=0; i < numEmple; i++){      	  
	   raf.writeInt(i+1); 
	   //buffer = new StringBuffer( apellidos[i] );      
	   //buffer.setLength(10); 
	   //raf.writeChars(buffer.toString());

	   byte[] byteArray = Arrays.copyOf(apellidos[i].getBytes(), 20);
	   raf.write(byteArray);
	   raf.writeInt(dep[i]);       
	   raf.writeDouble(salario[i]);

   }     
   raf.close();  //cerrar fichero 
   }
}
