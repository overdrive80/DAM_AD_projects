package parte_5;

import java.io.*;

public class LeerFichAleatorio {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		int medidaRegistro = 36;
		int id, dep, posicion;
		Double salario;
		// char apellido[] = new char[10], aux;

		File f = new File("AleatorioEmple.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile raf = new RandomAccessFile(f, "r");

		posicion = 0; // para situarnos al principio

		for (; raf.getFilePointer() + medidaRegistro <= raf.length();) { // recorro el fichero
			raf.seek(posicion); // nos posicionamos en posicion
			id = raf.readInt(); // obtengo id de empleado

			// recorro uno a uno los caracteres del apellido
			//for (int i = 0; i < apellido.length; i++) {
			//	aux = raf.readChar();
			//	apellido[i] = aux; // los voy guardando en el array
			//}

			byte[] cad = new byte[20];
			raf.read(cad);

			String apellidos = new String(cad, "UTF-8").replaceAll("\0", ""); // 
			//String apellidos = new String(apellido);
			
			dep = raf.readInt(); // obtengo dep
			salario = raf.readDouble(); // obtengo salario

			if (id > 0)
				System.out.printf("ID: %s, Apellido: %s, Departamento: %d, Salario: %.2f %n", id, apellidos.trim(), dep,
						salario);

			// me posiciono para el sig empleado, cada empleado ocupa 36 bytes
			posicion = posicion + medidaRegistro;

		} // fin bucle for
		raf.close(); // cerrar fichero
	}

	@SuppressWarnings("unused")
	private static boolean existeRegistro(File archivo, int numRegistro, int medidaReg)  {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(archivo, "r");

			// Si no tiene contenido
			if (raf.length() <= 0) { return false; }

			int pos = (numRegistro - 1) * medidaReg;

			// Si no existe el registro
			if (pos >= raf.length()) { return false; }

			raf.seek(pos);
			int codigo = raf.readInt();

			// Si no coincide hay un hueco
			if (codigo != numRegistro) { return false; }

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { raf.close(); } catch (IOException e) {e.printStackTrace();}
		}

		return true;
	}

}
