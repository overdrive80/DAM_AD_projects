package parte_5;

import java.io.*;

public class ModifFichAleatorio {
	public static void main(String[] args) throws IOException {
		File f = new File("AleatorioEmple.dat");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		
		int medidaRegistro = 36;
		int registro = 3;

		long posicion = (registro - 1) * medidaRegistro; // (4+20+4+8) 
		posicion = posicion + 4 + 20; // ID+apellido
		
		raf.seek(posicion); 
		raf.writeInt(40); // Departamento
		raf.writeDouble(4000.87); // Salario
		
		raf.close(); 
	}
}