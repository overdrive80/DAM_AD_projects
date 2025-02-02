package ejemplos;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.Conexion;
import clases.Departamentos;
import clases.LoggerHibernate;
import clases.ManejoExcepciones;

public class InsertarModificarDep {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {

		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		insertaModificaDepar(15, "DEP INFOR", "TALAVERA");

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void insertaModificaDepar(int numDep, String nom, String loc) {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();

		// Cargamos el departamento
		Departamentos dep = (Departamentos) session.get(Departamentos.class, BigInteger.valueOf(numDep));

		System.out.println("==============================");
		System.out.println("DATOS DEL DEPARTAMENTO " + numDep);

		try {
			// Si no existe el departamento lo creamos
			if (dep == null) {
				System.out.println("El departamento no existe, LO CREO");
				dep = new Departamentos();
				dep.setDeptNo(BigInteger.valueOf(numDep));
				dep.setDnombre(nom);
				dep.setLoc(loc);
				
				session.persist(dep);
				
			} else {
				System.out.println("El departamento existe, LO MODIFICO");
				dep.setDnombre(nom);
				dep.setLoc(loc);
				
				session.persist(dep);
			}
			
			tx.commit();
		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}
		
		session.close();
	}
}
