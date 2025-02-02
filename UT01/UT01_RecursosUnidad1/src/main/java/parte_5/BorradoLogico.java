package parte_5;

import java.io.*;

public class BorradoLogico {
	public static void main(String[] args) throws IOException {
		File f = new File("AleatorioEmple.dat");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		
		int medidaRegistro = 36;
		int registro = 3;

		long posicion = (registro - 1) * medidaRegistro; // (4+20+4+8) 
		
		raf.seek(posicion); 
		raf.writeInt(0); // ID
		
		raf.close(); 
	}
}