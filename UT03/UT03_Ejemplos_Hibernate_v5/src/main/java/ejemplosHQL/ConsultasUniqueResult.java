package ejemplosHQL;
import clases.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ConsultasUniqueResult {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		consultas_uniqueResult();
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}
	
		
	private static void consultas_uniqueResult() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		Departamentos depart = new Departamentos();
		
		String separador = "-".repeat(38);
		
		//-----------------------DEPARTAMENTO 10--------------------------------------------------------------------
		System.out.println(separador);
		
		String strHQL = "from Departamentos as d where d.deptNo= 10";
		Query<?> queryHQL = session.createQuery(strHQL, Departamentos.class); // Ojo con las mayúsculas para identificar la clase
		
		depart = (Departamentos) queryHQL.uniqueResult();
		
		System.out.println(depart.getLoc() +"*"+depart.getDnombre());
		
		//-----------------------DEPARTAMENTO CONTABILIDAD----------------------------------------------------------
		System.out.println(separador);
		strHQL = "from Departamentos as d where d.dnombre = 'CONTABILIDAD'";
		queryHQL = session.createQuery(strHQL, Departamentos.class); // Ojo con las mayúsculas para identificar la clase
		
		depart = (Departamentos) queryHQL.uniqueResult();
		
		System.out.println(depart.getLoc() +"*"+depart.getDeptNo());
		
		//----------------------- NUMERO DE EMPLEADOS---------------------------------------------------------------
		System.out.println(separador);
		strHQL = "select count(*) from Empleados";
		queryHQL = session.createQuery(strHQL, Long.class); // Ojo con las mayúsculas para identificar la clase
		
		Long cont = (Long) queryHQL.uniqueResult();
		
		System.out.println("Número de empleados: " + cont);
		
		//----------------------- MEDIA SALARIOS DE EMPLEADOS---------------------------------------------------------------
		System.out.println(separador);
		strHQL = "select avg(e.salario) from Empleados e";
		queryHQL = session.createQuery(strHQL, Double.class); // Ojo con las mayúsculas para identificar la clase
		
		Double media = (Double) queryHQL.uniqueResult();
		
		System.out.printf("Media de salario de empleados: %.2f%n" , media);
		
		//----------------------- SALARIO MAXIMO DE EMPLEADOS---------------------------------------------------------------
		System.out.println(separador);
		strHQL = "select max(e.salario) from Empleados e";
		queryHQL = session.createQuery(strHQL, Double.class); // Ojo con las mayúsculas para identificar la clase
		
		Double salarioMax = (Double) queryHQL.uniqueResult();
		
		System.out.printf("Salario máximo de empleados: %.2f%n" , salarioMax);
		
		session.close();
	}
	

}
