package ejemplos;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.*;

public class InsertarDepartamento {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		insertardepartamento();
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}

	private static void insertardepartamento() {
		
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();

		// Creamos un objeto transitorio
		Departamentos dep = new Departamentos();
		dep.setDeptNo(BigInteger.valueOf(60));
		dep.setDnombre("MARKETING");
		dep.setLoc("GUADALAJARA");

		try {

			System.out.println("Insertando fila en la tabla DEPARTAMENTOS.");

			session.persist(dep);
			tx.commit();

			System.out.println("La fila se ha insertado correctamente.");

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
}
