package ejemplosHQL_Insert_Update_Delete;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import clases.Conexion;
import clases.LoggerHibernate;
import clases.ManejoExcepciones;

public class ConsultasModificaSalario_Hibernate6 {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		modificaEmpleSalario(7521,200); //Existe
		modificaEmpleSalario(21,200);   //NO Existe

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void modificaEmpleSalario(int num, int subida) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
		
		//Recuperamos el salario del empleado
		Double salario = (Double) session
				.createQuery("select e.salario from Empleados e where e.empNo = :num", Double.class)
				.setParameter("num", num).uniqueResult();
		
		if (salario == null) {
			System.out.printf("El empleado con número %d no existe.%n", num );
			return;
		}
		
		try {
			// --------------------EJEMPLO DE UPDATE--------------------------
			String queryHQL = "update Empleados e set e.salario = e.salario+:subida where e.empNo = :num";

			MutationQuery query = session.createMutationQuery(queryHQL);
			query.setParameter("subida", subida);
			query.setParameter("num", num);

			int filasModificadas = query.executeUpdate();

			tx.commit(); // valida la transacción
			
			System.out.println("Empleados actualizados: " + filasModificadas); // Nº entidades afectadas

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
	

}
