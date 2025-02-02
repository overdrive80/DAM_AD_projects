package parte_1;

import java.io.File;

public class RenombrarFichero {

	public static void main(String[] args) {
		 File f = new File("D:\\ADAT\\UNI1\\VerInf.java");  
		 File f2=new File("D:\\ADAT\\VerInf2.java");
		 f.renameTo(f2);
	}

}
