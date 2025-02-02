package ejemplos;

import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.*;

/**
 * Acción Hibernate: Añadir un empleado al que le asignamos un departamento
 * Acción analoga BBDD: Añadir un registro en la tabla Empleados 
 *                      y en el campo Dept_no asignarle el número de departamento
 * Realiza la acción: objeto Empleados
 */
public class InsertarEmpleado {
	private static SessionFactory sesionFactory;
	
	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();
		
		insertarEmpleado(30, 4456, 7499);
		
		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
		
	}
	
	private static void insertarEmpleado(int numDep, int numEmp, int numDir) {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();

		// Creamos un objeto Empleados
		Double salario = 1500.0;// inicializo el salario
		Double comision = 10.0; // inicializo la comisión
		
		Empleados em = new Empleados(); // creo un objeto empleados
			em.setEmpNo(BigInteger.valueOf( numEmp )); // el numero de empleado es 4455
			em.setApellido("PEPE");
			em.setDir(BigInteger.valueOf( numDir )); // el director es el numero de empleado 7499
			em.setOficio("VENDEDOR");
			em.setSalario(salario);
			em.setComision(comision);
			
		// fecha de alta
		java.util.Date hoy = new java.util.Date();
		java.sql.Date fecha = new java.sql.Date(hoy.getTime());
			em.setFechaAlt(fecha);

		// Creamos un objeto Departamentos
		Departamentos d = new Departamentos(); // creo un objeto Departamentos
			d.setDeptNo(BigInteger.valueOf( numDep)); // el número de dep es 10
			d.setDnombre("RECURSOS");
			d.setLoc("MADRID");
				
		// Verificamos que exista el departamento
		Departamentos existeDep = session.get(Departamentos.class, d.getDeptNo());
		
		try {
			// Si no existe lo creamos y añadimos. 
			if (existeDep == null) {
				session.persist(d);
				em.setDepartamentos(d);
			} else {
				//Si existe añadimos el que acabamos de recuperar
				em.setDepartamentos(existeDep);
			}
			
			System.out.println("Inserto un EMPLEADO EN EL DEPARTAMENTO 10.");
			
			session.persist(em);
			tx.commit();
			
			System.out.println("El empleado se ha insertado correctamente.");
		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
		System.exit(0);
	}
}
