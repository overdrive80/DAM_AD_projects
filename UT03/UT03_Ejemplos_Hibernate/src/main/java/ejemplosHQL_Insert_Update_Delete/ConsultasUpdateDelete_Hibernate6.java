package ejemplosHQL_Insert_Update_Delete;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import clases.Conexion;
import clases.LoggerHibernate;
import clases.ManejoExcepciones;

public class ConsultasUpdateDelete_Hibernate6 {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		ejemploUpdate("SALA", 2500.0);
		ejemploDelete(20);


		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void ejemploUpdate(String apellido, Double cantidad) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
		
		try {
			// --------------------EJEMPLO DE UPDATE--------------------------
			String queryHQL = "update Empleados e set e.salario = :nuevoSal where e.apellido = :ape";

			MutationQuery query = session.createMutationQuery(queryHQL);
			query.setParameter("nuevoSal", cantidad);
			query.setParameter("ape", apellido);

			int filasModificadas = query.executeUpdate();

			tx.commit(); // valida la transacción
			
			System.out.println("FILAS MODIFICADAS: " + filasModificadas); // Nº entidades afectadas

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
	
	private static void ejemploDelete(int numDep) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
		
		try {
			// --------------------EJEMPLO DE DELETE--------------------------
			String queryHQL = "delete Empleados e where e.departamentos.deptNo = ?1";
			
			MutationQuery query = session.createMutationQuery(queryHQL);
			query.setParameter(1, BigInteger.valueOf(numDep));
			
			int filasModificadas = query.executeUpdate();
			
			tx.commit(); // valida la transacción

			System.out.println("FILAS ELIMINADAS: " + filasModificadas); // Nº entidades afectadas

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
}
