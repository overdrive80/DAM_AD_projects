package ejemplos;

import clases.*;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Acción Hibernate: Añadir al Set de un departamento un empleado ya existente que tiene asignado otro departamento
 * Acción analoga BBDD: Modificar el campo Dept_no de un registro existente de la tabla Empleados 
 *                      con el nuevo número de departamento.
 * 						
 * Realiza la acción: objeto Departamentos
 * Responsabilidad de la acción según atributo inverse del archivo Departamentos.hbm.xml:
 *     false (Dueño-no inverso): el lado "Departamentos" es responsable de la relación en términos de actualizaciones
 *     en la base de datos. Si se añade, modifica o elimina un empleado y se guarda el departamento,
 *     Hibernate también actualizará la relación en la tabla de empleados.
 *     
 *     true (No dueño-inverso): el lado "Empleados" sería responsable de la relación. 
 *     En este caso, si se añade, modifica o elimina un empleado y se guarda el departamento, 
 *     Hibernate no actualizaría automáticamente la relación en la tabla de empleados.
 */

public class InsertarEmpleadoSetDepartamento {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		insertaEmpleado_SetDepartamento(30, 7934);
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
		
	}
	
	private static void insertaEmpleado_SetDepartamento(int numDep, int numEmp) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
				
		// Verificamos que exista el departamento
		Departamentos existeDep = session.get(Departamentos.class, BigInteger.valueOf(numDep));
		
		try {
			// Si no existe salimos
			if (existeDep == null) {
				System.out.println("El departamento no existe. No se puede insertar.");
				return;
			}
			
			// Compruebo empleado
			Empleados emple = (Empleados) session.get(Empleados.class, BigInteger.valueOf(numEmp));
			
			// Si no existe salimos
			if (emple == null) {
				System.out.println("El Empleado no existe. No se puede insertar.");
				return;
			}
			
			//Si existe el departamento y el empleado
			System.out.println("Empleado " + numDep + " añadido al departamento " + numEmp);
			existeDep.getEmpleadoses().add(emple);
			session.merge(existeDep);
			tx.commit();
			
		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
		System.exit(0);
	}
}
