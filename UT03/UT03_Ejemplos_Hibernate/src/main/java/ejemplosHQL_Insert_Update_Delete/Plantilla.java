package ejemplosHQL_Insert_Update_Delete;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.*;

@SuppressWarnings("unused")
public class Plantilla {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		ejemploUpdateDelete();
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
		
	}
	
	private static void ejemploUpdateDelete() {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();


		
		try {

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
}
