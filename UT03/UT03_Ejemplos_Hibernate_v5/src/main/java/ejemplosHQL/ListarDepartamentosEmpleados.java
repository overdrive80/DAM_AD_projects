package ejemplosHQL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import clases.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListarDepartamentosEmpleados {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		listarDepartamentos();
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}
	
		
	private static void listarDepartamentos() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		String strHQL = "from Departamentos";
		Query<?> queryHQL = session.createQuery(strHQL, Departamentos.class);
		
		List<?> deptos = queryHQL.list(); // Con list podemos modificar con getResultList no
		int numDeptos = deptos.size();
		
		for (int i = 0; i < numDeptos; i++) {
			// Obtenemos el departamento
			Departamentos dep = (Departamentos) deptos.get(i);
			imprimirDepartamento(dep);
			
			// Obtenemos la lista de empleados del departamento
			Set<?> empleados = dep.getEmpleadoses();
			
			//Recorremos el Set con un iterator
			Iterator<?> it = empleados.iterator();
						
			if (!it.hasNext()) {
				System.out.print("\n               ");
				System.out.println("EL DEPARTAMENTO NO TIENE EMPLEADOS");
				return;
			} 
				
			imprimirCabeceraEmpleados();
			
			Double salarioTotal = 0.0;
			while (it.hasNext()) {
				Empleados emplea = (Empleados) it.next();
				
				imprimirEmpleado(emplea);
				salarioTotal = salarioTotal + emplea.getSalario();
			}
			imprimirSalarioTotal(salarioTotal);
		}
		session.close();
	}
	
	private static void imprimirEmpleado(Empleados emple) {
		String formato = "%12s %20s %20s %15s %15s%n";
		
		System.out.printf(formato,
				emple.getEmpNo(), emple.getApellido(),emple.getOficio(),
				emple.getFechaAlt(), emple.getSalario());
		
	}
	
	private static void imprimirSalarioTotal(double cantidad) {
		String formato = "%12s %20s %20s %15s %15s%n";
				
		System.out.printf(formato,
				"------------", "--------------------","--------------------",
				"---------------", "---------------");
		
		System.out.printf("%-28s %20s %20s %15s %n",
				"Total salario: ","","", cantidad);
		
	}
	
	private static void imprimirCabeceraEmpleados() {
		
		String formato = "%12s %20s %20s %15s %15s%n";
		
		System.out.printf(formato,"EMPNO", "APELLIDO","OFICIO","FECHAALTA", "SALARIO");
		
		System.out.printf(formato,
				"------------", "--------------------","--------------------",
				"---------------", "---------------");
	}
	
	private static void imprimirDepartamento(Departamentos dep) {
		System.out.println("======================================================================================");
		System.out.printf("Num dep: %3d Nombre Dep: %13s  Localidad: %10s  Número de empleados: %2d%n",
		dep.getDeptNo(), dep.getDnombre(), dep.getLoc(), dep.getEmpleadoses().size());
		
	}
}
