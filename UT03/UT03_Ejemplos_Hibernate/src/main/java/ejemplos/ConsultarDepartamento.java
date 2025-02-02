package ejemplos;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import clases.*;

public class ConsultarDepartamento {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		verDepartamento(10);
		verDepartamento(88);
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}
	
		
	private static void verDepartamento(int numDep) {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Comprobamos si existe 
		Departamentos dep = (Departamentos) session.get(Departamentos.class, BigInteger.valueOf(numDep));
		
		// Si no existe informamos y salimos
		if (dep == null) {
			System.out.println("El departamento " + numDep + " no existe");
			return;
		} 
		
		// Impresion datos del departamento
		System.out.println("==============================");
		System.out.println("DATOS DEL DEPARTAMENTO " + numDep);
		System.out.println("Nombre Dep:" + dep.getDnombre());
		System.out.println("Localidad:" + dep.getLoc());

		// Impresion de empleados del departamento 
		Set<?> listaemple = dep.getEmpleadoses();// obtenemos empleados
		Iterator<?> it = listaemple.iterator();
	
		System.out.println("==============================");
		System.out.println("EMPLEADOS DEL DEPARTAMENTO 10.");
		System.out.printf("Número de empleados: %d %n", listaemple.size());
		
		// Recorremos el conjunto
		while (it.hasNext()) {
			Empleados emple = (Empleados) it.next();
			System.out.printf("%s * %.2f %n", emple.getApellido(), emple.getSalario());
		}
		
		System.out.println("==============================");
		session.close();
	}
}
