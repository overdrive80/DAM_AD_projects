package ejemplosHQL_Insert_Update_Delete;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import clases.Conexion;
import clases.LoggerHibernate;
import clases.ManejoExcepciones;

public class ConsultasModificaDepartamentoEmpleado_Hibernate6 {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		modificaDeparEmple(7521,20);    //Existe dep
		modificaDeparEmple(7521,13);   //NO Existe dep

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void modificaDeparEmple(int num, int dep) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
		
		System.out.println("Modifica departamento del empleado\n----------------------");
		String queryHQL = "update Empleados e set e.departamentos.deptNo = :dep where e.empNo = :num";
		
		try {

			MutationQuery query = session.createMutationQuery(queryHQL);
			query.setParameter("dep", dep);
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
