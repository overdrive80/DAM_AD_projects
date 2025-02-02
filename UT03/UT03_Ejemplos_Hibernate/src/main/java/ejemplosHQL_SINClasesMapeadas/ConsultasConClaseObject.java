package ejemplosHQL_SINClasesMapeadas;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.Conexion;
import clases.Empleados;
import clases.LoggerHibernate;

//Proyecciones DTO
@SuppressWarnings("deprecation")
public class ConsultasConClaseObject {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {

		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		consultasObjetos();
		consultaOficios();
		consultaCargosEmpleados();

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}

	private static void consultasObjetos() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// ------------------PASO 1: Determinamos la consulta------------------------
		String strHQL = "select d.deptNo, count(e.empNo), coalesce(avg(e.salario),0), d.dnombre "
				+ "from Departamentos d left join Empleados e on d.deptNo = e.departamentos.deptNo "
				+ "group by d.deptNo,d.dnombre";

		// ------------------PASO 2: Creamos la clase DTO en base a las columnas de la
		// consulta------------------------
		// ------------------PASO 3: Generar consulta----------------------------
		Query<?> query = session.createQuery(strHQL, Object[].class);

		List<?> totales = query.getResultList();

		Iterator<?> it = totales.iterator();

		String formato = "%-10s %-15s %-15s %-15s%n";

		imprimirTextoFormato(formato, "NUMERO DEP", "NOMBRE", "SALARIO MEDIO", "NUM EMPLES");
		imprimirTextoFormato(formato, "-".repeat(10), "-".repeat(15), "-".repeat(15), "-".repeat(15));

		while (it.hasNext()) {
			Object[] filaActual = (Object[]) it.next();

			String sNumDep = "   " + filaActual[0];
			String sNumEmp = String.format("%d", filaActual[1]);
			String sSalario = String.format("%.1f", filaActual[2]);
			String sNombre = (String) filaActual[3];

			imprimirTextoFormato(formato, sNumDep, sNombre, sSalario, sNumEmp);
		}

		session.close();
	}

	private static void consultaOficios() {
		Session session = sesionFactory.openSession();

		
		Query<?> query = session.createQuery("select distinct e.oficio from Empleados e");
		List<?> filas = query.list();

		System.out.println("-----------VER OFICIOS----------");

		for (int i = 0; i < filas.size(); i++) {
			Object filaActual = filas.get(i); // Acceso a una fila
			// O también lo ponemos así
			// String filaActual = (String)filas.get(i);
			System.out.println(filaActual);
		}
		session.close();
	}

	private static void consultaCargosEmpleados() {
		Session session = sesionFactory.openSession();
			
		String strHQL = "from Empleados as emp order by emp.empNo";
		
		Query<?> query = session.createQuery(strHQL);
		List<?> listado = query.list();
		
		System.out.println("-----------VER DIRECTORES----------");
		for (Object fila : listado) {
			
			Empleados emple = (Empleados) fila;
			
			String apellidos = emple.getApellido();
			String oficio = emple.getOficio();
		
			if (oficio.equals("DIRECTOR")) {
				System.out.printf("El empleado %s es DIRECTOR%n", apellidos);	
			} else {
				System.out.printf("El empleado %s%n", apellidos);	
				
			}			
			
		}
		session.close();
	}
	
	
	private static void imprimirTextoFormato(String formato, Object... campos) {

		System.out.printf(formato, (Object[]) campos);
	}

}