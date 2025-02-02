package parte_5;

import java.io.*;

public class EscribirFichAleatorioUnReg {
	public static void main(String[] args) throws IOException {
		File fichero = new File("AleatorioEmple.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		int medidaReg = 36;
		StringBuffer buffer = null; 
		String apellido = "LUCAS";
		Double salario = 1230.87;
		int dep = 10;

		// INSERTAR UN REGISTRO AL FINAL DEL FICHERO
		long posicion = file.length();
		file.seek(posicion);
		int id = (int) (file.length() / medidaReg) + 1;

		//	int id = 20; 
		//	long posicion = (id - 1 ) * medidaReg;      
		//	file.seek(posicion); // nos posicionamos

		file.writeInt(id); 
		buffer = new StringBuffer(apellido);
		buffer.setLength(10); 
		file.writeChars(buffer.toString());
		file.writeInt(dep); 
		file.writeDouble(salario);

		file.close(); // cerrar fichero
	}
}