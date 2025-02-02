package ejemplosHQL_Insert_Update_Delete;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import clases.Conexion;
import clases.LoggerHibernate;
import clases.ManejoExcepciones;

public class ConsultasInsertValues_Hibernate6 {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		ejemploInsertDepar(12, "INNOVACION", "MOSTOLES"); //Error por definicion tamaño columna tabla
		ejemploInsertDepar(123, "INNOVACION", "MOSTOLES"); //Error por definicion tamaño columna tabla

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void ejemploInsertDepar(int numDep, String nombre, String localidad) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
		
		try {
			// --------------------EJEMPLO DE INSERT--------------------------
			String queryHQL = "insert into Departamentos (deptNo, dnombre, loc) values (?1, ?2, ?3)";

			MutationQuery query = session.createMutationQuery(queryHQL);
			query.setParameter(1, numDep);
			query.setParameter(2, nombre);
			query.setParameter(3, localidad);

			int filasModificadas = query.executeUpdate();

			tx.commit(); // valida la transacción
			
			System.out.println("FILAS INSERTADAS: " + filasModificadas); // Nº entidades afectadas

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
	

}
