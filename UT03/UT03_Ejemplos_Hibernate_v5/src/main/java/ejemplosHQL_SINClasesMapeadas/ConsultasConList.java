package ejemplosHQL_SINClasesMapeadas;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.Conexion;
import clases.LoggerHibernate;

//Proyecciones DTO
public class ConsultasConList {
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

	    //------------------PASO 1: Determinamos la consulta------------------------
	    String strHQL = "select new list(d.deptNo, d.dnombre, coalesce(avg(e.salario),0), count(e.empNo)) "
	    		+ "from Departamentos d left join Empleados e on d.deptNo = e.departamentos.deptNo group by d.deptNo, d.dnombre";

	    //------------------PASO 2: Proyección utilizando constructor de clase------------------------
	    Query<?> query = session.createQuery(strHQL);

	    //------------------PASO 3: Obtener resultados----------------------------
	    List<?> totales = query.getResultList();

	    String formato = "%-10s %-15s %-15s %-15s%n";
		
		imprimirTextoFormato(formato, "NUMERO DEP", "NOMBRE","SALARIO MEDIO", "NUM EMPLES");
		imprimirTextoFormato(formato, "-".repeat(10), "-".repeat(15), "-".repeat(15), "-".repeat(15));

	    for (Object total : totales) {
	        // El objeto 'total' es una lista que contiene los elementos seleccionados en la proyección
	        List<?> totalList = (List<?>) total;

	        // Accedes a los elementos de la lista
			String sNumDep = "   " + totalList.get(0);
			String sNombre = totalList.get(1).toString();
			String sSalario = String.format("%.1f", totalList.get(2));
			String sNumEmp = String.format("%d", totalList.get(3));

	        // Puedes imprimir o procesar estos valores según tus necesidades
			imprimirTextoFormato(formato, sNumDep, sNombre, sSalario, sNumEmp);
	    }

	    session.close();
	}


	
	private static void imprimirTextoFormato(String formato, Object... campos) {

        System.out.printf(formato, (Object[]) campos);
    }

}