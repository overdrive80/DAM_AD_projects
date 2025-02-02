package ejemplos;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.*;

public class BorrarDepartamento {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		borrarDepar(15);
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}
	
		
	private static void borrarDepar(int numDep) {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();
		
		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();

		// Comprobamos si existe 
		Departamentos dep = (Departamentos) session.get(Departamentos.class, BigInteger.valueOf(numDep));
		
		// Si no existe informamos y salimos
		if (dep == null) {
			System.out.println("El departamento " + numDep + " no existe");
			return;
		} 
		
		try {
			System.out.println("Eliminando el departamento " + numDep);
			
			session.remove(dep);
			tx.commit();

			System.out.println("El registro se ha borrado correctamente.");

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}
	}
}
