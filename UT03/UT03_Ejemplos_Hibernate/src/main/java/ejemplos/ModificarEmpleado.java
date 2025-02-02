package ejemplos;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.*;


public class ModificarEmpleado {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {

		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		modificarDatosEmpleado(7369, 30, 2000.0);

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void modificarDatosEmpleado(int numEmple, int nuevoDep, Double nuevoSalario) {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();

		try {
			// Cargamos el empleado
			Empleados emple = (Empleados) session.get(Empleados.class, BigInteger.valueOf(numEmple));
			
			// Si no existe el empleado no se puede actualizar
			if (emple == null) {
				System.out.println("El Empleado no existe. No se puede actualizar.");
				return;
			}
			
			// Si existe imprimimos los datos
			System.out.printf("Modificación empleado: %d%n", emple.getEmpNo());
			System.out.printf("Salario antiguo: %.2f%n", emple.getSalario());
			System.out.printf("Departamento antiguo: %s%n", emple.getDepartamentos().getDnombre());	
						
			// Cargamos el nuevo departamento
			Departamentos dep = (Departamentos) session.get(Departamentos.class, BigInteger.valueOf(nuevoDep));
			
			// Si no existe el departamento no actualizamos este valor
			if (dep == null) {
				System.out.println("El departamento " + nuevoDep + " NO existe");
				return;
			}
			
			// Si existe lo modificamos
			emple.setSalario(nuevoSalario);
			emple.setDepartamentos(dep);
			session.merge(emple);
			tx.commit();
			
			System.out.printf("Salario nuevo: %.2f%n",emple.getSalario());
			System.out.printf("Departamento nuevo: %s%n", emple.getDepartamentos().getDnombre());
			
		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}
		
		session.close();
	}
}
