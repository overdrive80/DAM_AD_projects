package ejemplosHQL_SINClasesMapeadas;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.Conexion;
import clases.LoggerHibernate;
import clasesDTO.TotalesDepartamento;

//Proyecciones DTO
public class ConsultasConClaseDTO {
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
		String strHQL = "select new clasesDTO.TotalesDepartamento(d.deptNo, count(e.empNo), coalesce(avg(e.salario),0), d.dnombre) "
				+ "from Departamentos d left join Empleados e on d.deptNo = e.departamentos.deptNo "
				+ "group by d.deptNo,d.dnombre";	

		//------------------PASO 2: Creamos la clase DTO en base a las columnas de la consulta------------------------
		//------------------PASO 3: Generar consulta----------------------------
		Query<?> query = session.createQuery(strHQL, TotalesDepartamento.class);
		
		List<?> totales = query.list();
		
		Iterator<?> it = totales.iterator();
		
		String formato = "%-10s %-15s %-15s %-15s%n";
		
		imprimirTextoFormato(formato, "NUMERO DEP", "NOMBRE","SALARIO MEDIO", "NUM EMPLES");
		imprimirTextoFormato(formato, "-".repeat(10), "-".repeat(15), "-".repeat(15), "-".repeat(15));

		while (it.hasNext()) {
			TotalesDepartamento totalDep = (TotalesDepartamento) it.next();
			
			String sNumDep = "   " + totalDep.getNumero();
			String sNombre = totalDep.getNombre();
			String sSalario = String.format("%.1f", totalDep.getSalarioPromedio());
			String sNumEmp = String.format("%d", totalDep.getNumEmpleados());
			
			imprimirTextoFormato(formato, sNumDep, sNombre, sSalario, sNumEmp);

		}
		
		session.close();
	}

	
	private static void imprimirTextoFormato(String formato, Object... campos) {

        System.out.printf(formato, (Object[]) campos);
    }

}

/* 
CONSULTA1. Solo devuelve departamentos con empleados
	select de.deptNo, count(em.empNo), coalesce(avg(em.salario),0), de.dnombre
	from Departamentos as de, Empleados as em 
	where de.deptNo=em.departamentos.deptNo 
	group by de.deptNo,de.dnombre

CONSULTA2. Solo devuelve departamentos con empleados
	select em.departamentos.deptNo, count(em.empNo), coalesce(avg(em.salario),0), em.departamentos.dnombre 
	from Empleados as em 
	group by em.departamentos.deptNo,em.departamentos.dnombre
	
CONSULTA3. Devuelve todos los departamentos, tengan o no empleados
	select d.deptNo, count(e.empNo), coalesce(avg(e.salario),0), d.dnombre 
	from Empleados as e 
		right join e.departamentos as d 
	group by d.deptNo, d.dnombre
	
CONSULTA4. Devuelve todos los departamentos, tengan o no empleados
	select d.deptNo, count(em.empNo), coalesce(avg(em.salario),0), d.dnombre 
	from Departamentos d 
		left join d.empleadoses em
	group by d.deptNo,d.dnombre
*/