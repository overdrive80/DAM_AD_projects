package ejemplosHQL;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.*;

public class ListarEmpleados {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		listadoEmpleados(30);
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}
	
		
	private static void listadoEmpleados(int numDep) {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		Empleados emplea = new Empleados();
		
		Query<?> queryHQL = session.createQuery("from Empleados e where e.departamentos.deptNo=" + numDep, Empleados.class); // Ojo con las mayúsculas para identificar la clase
		
		List<?> listado = queryHQL.list();
		
		int num = listado.size();
		
		System.out.println("-------------------------------");		
		System.out.println("Número de empleados del departamento " + numDep + ": " + num);
		
		for (int i = 0; i < num; i++) {
			emplea = (Empleados) listado.get(i);
			System.out.println(emplea.getEmpNo() + "*" + emplea.getApellido());
		}
		
		System.out.println("");
		session.close();
	}
	

}
