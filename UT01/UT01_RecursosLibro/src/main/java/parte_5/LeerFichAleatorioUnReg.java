package parte_5;

import java.io.*;

public class LeerFichAleatorioUnReg {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		File fichero = new File("AleatorioEmple.dat");
		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		//
		int id, dep, posicion;
		Double salario;
		char apellido[] = new char[10], aux;
		int registro = Integer.parseInt(args[0]); // recoger id de empleado a consultar

		posicion = (registro - 1) * 36;
		if (posicion >= file.length())
			System.out.printf("ID: %d, NO EXISTE EMPLEADO...", registro);
		else {
			file.seek(posicion); // nos posicionamos
			id = file.readInt(); // obtengo id de empleado
			for (int i = 0; i < apellido.length; i++) {
				aux = file.readChar();// recorro uno a uno los caracteres del apellido
				apellido[i] = aux; // los voy guardando en el array
			}
			String apellidoS = new String(apellido);// convierto a String el array
			dep = file.readInt();// obtengo dep
			salario = file.readDouble(); // obtengo salario

			System.out.println("ID: " + registro + ", Apellido: " + apellidoS.trim() + ", Departamento: " + dep
					+ ", Salario: " + salario);
		}
		file.close(); // cerrar fichero
	}

	@SuppressWarnings("unused")
	private boolean existeRegistro(File archivo, int numRegistro, int medidaReg) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(archivo, "r");

			// Si no tiene contenido
			if (raf.length() <= 0) {
				return false;
			}

			int pos = (numRegistro - 1) * medidaReg;

			// Si no existe el registro
			if (pos >= raf.length()) {
				return false;
			}

			raf.seek(pos);
			int codigo = raf.readInt();

			// Si no coincide hay un hueco
			if (codigo != numRegistro) {
				return false;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

}