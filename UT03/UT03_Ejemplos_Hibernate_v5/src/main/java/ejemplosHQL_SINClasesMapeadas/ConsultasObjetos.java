package ejemplosHQL_SINClasesMapeadas;

import clases.*;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
public class ConsultasObjetos {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {

		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		consultasObjetos();

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}

	private static void consultasObjetos() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// ------------------Ejemplo 1: Listar todos los Empleados y sus
		// departamentos------------------------
		String strHQL = "from Empleados e, Departamentos d where e.departamentos.deptNo = d.deptNo order by apellido";

		Query<?> query = session.createQuery(strHQL, Object[].class);
		List<?> listado = query.list();

		Iterator<?> it = listado.iterator();

		while (it.hasNext()) {
			Object[] par = (Object[]) it.next();
			Empleados em = (Empleados) par[0];
			Departamentos de = (Departamentos) par[1];
			System.out.printf("%s, %.2f, %s, %s %n", em.getApellido(), em.getSalario(), de.getDnombre(), de.getLoc());
		}
		System.out.println("=".repeat(45));

		// ------------------Ejemplo 2: Mostrar salario medio de los
		// Empleados------------------------
		strHQL = "select avg(em.salario) from Empleados as em";
		query = session.createQuery(strHQL, Object[].class);

		Double suma = (Double) query.uniqueResult();
		System.out.printf("Salario medio: %.2f%n", suma);
		System.out.println("=".repeat(45));

		// ------------------Ejemplo 3: Mostrar salario medio y el numero de
		// empleados------------------------
		strHQL = "select avg(salario), count(empNo) from Empleados ";
		query = session.createQuery(strHQL); // , Object[].class

		Object[] resultado = (Object[]) query.uniqueResult();

		System.out.printf("Salario medio: %.2f%n", resultado[0]);
		System.out.printf("Número de empleados: %d%n", resultado[1]);
		System.out.println("=".repeat(45));

		// ------------------Ejemplo 4: Mostrar salario medio y numero empleados por
		// departamento------------------------
		strHQL = "select e.departamentos.deptNo, avg(salario), " + " count(empNo) from Empleados e "
				+ " group by e.departamentos.deptNo ";
		query = session.createQuery(strHQL, Object[].class);

		listado = query.list();
		it = listado.iterator();

		while (it.hasNext()) {
			Object[] par = (Object[]) it.next();

			BigInteger depar = (BigInteger) par[0];
			Double media = (Double) par[1];
			Long cuenta = (Long) par[2];

			System.out.printf("Dep: %d, Media: %.2f, Nº emp: %d %n", depar, media, cuenta);
		}
		System.out.println("=".repeat(45));

		session.close();
	}

}
