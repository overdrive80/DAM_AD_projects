package ejemplosHQL;
import java.util.List;
import clases.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListarDepartamentos {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		listadoDepartamentos();
		listadoDepartamentos2();
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}
	
		
	private static void listadoDepartamentos() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		Departamentos dep = new Departamentos();
		
		Query<?> queryHQL = session.createQuery("from Departamentos", Departamentos.class); // Ojo con las mayúsculas para identificar la clase
		
		List<?> listadoDep = queryHQL.list();
		
		int num = listadoDep.size();
		
		System.out.println("---------Metodo .list()---------");		
		System.out.println("Número de departamentos: " + num);
		
		for (int i = 0; i < num; i++) {
			dep = (Departamentos) listadoDep.get(i);
			System.out.println(dep.getDeptNo() + "*" + dep.getDnombre());
		}
		
		System.out.println("");
		session.close();
	}
	
	private static void listadoDepartamentos2() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		Departamentos dep = new Departamentos();
		
		Query<?> queryHQL = session.createQuery("from Departamentos", Departamentos.class); // Ojo con las mayúsculas para identificar la clase
		
		List<?> listadoDep = queryHQL.getResultList();
		
		int num = listadoDep.size();
		
		System.out.println("----Metodo .getResultList()----");		
		System.out.println("Número de departamentos: " + num);
		
		for (int i = 0; i < num; i++) {
			dep = (Departamentos) listadoDep.get(i);
			System.out.println(dep.getDeptNo() + "*" + dep.getDnombre());
		}
		
		System.out.println("");
		session.close();
	}
}
