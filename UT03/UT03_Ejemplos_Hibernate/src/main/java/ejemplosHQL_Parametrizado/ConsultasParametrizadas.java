package ejemplosHQL_Parametrizado;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.*;

public class ConsultasParametrizadas {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {

		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		consultasParametros();

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);
	}

	private static void consultasParametros() {
		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// ----------------------Ejemplo 1: consulta empleado 7369-----------------------
		String strHQL = "from Empleados where empNo = :numemple";

		Query<?> query = session.createQuery(strHQL, Empleados.class);

		query.setParameter("numemple", BigInteger.valueOf(7369));

		Empleados emple = (Empleados) query.uniqueResult();
		System.out.println("-".repeat(20));
		System.out.println("Empleado número 7369");
		System.out.printf("%s, %s %n", emple.getApellido(), emple.getOficio());

		// ----------------------Ejemplo 2: consulta empleados del dept 10 y oficio DIRECTOR-----------------------
		strHQL = "from Empleados emp where emp.departamentos.deptNo = :ndep and emp.oficio = :ofi";

		query = session.createQuery(strHQL, Empleados.class);

		query.setParameter("ndep", BigInteger.valueOf(10));
		query.setParameter("ofi", "DIRECTOR");

		List<?> empleados = query.list();
		Iterator<?> it = empleados.iterator();

		System.out.println("-".repeat(20));
		System.out.println("Directores del dep 10");

		while (it.hasNext()) {
			emple = (Empleados) it.next();

			System.out.println(emple.getApellido());
		}

		// ----------------------Ejemplo 3: Igual pero con paramentros posicionales-----------------------
		strHQL = "from Empleados emp where emp.departamentos.deptNo = ?1 and emp.oficio = ?2";

		query = session.createQuery(strHQL, Empleados.class);

		query.setParameter(1, BigInteger.valueOf(10));
		query.setParameter(2, "DIRECTOR");

		empleados = query.list();
		it = empleados.iterator();

		System.out.println("-".repeat(20));
		System.out.println("Directores del dep 10");

		for (int i = 0; i < empleados.size(); i++) {
			emple = (Empleados) empleados.get(i);
			System.out.println(emple.getApellido());
		}

		// ----------------------Ejemplo 4: Empleados fecha de alta 1991-12-03-----------------------
		strHQL = "from Empleados where fechaAlt = :fechalta";
		String strFecha = "1991-12-03";
		
		try {
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
			
			formatoDelTexto.setLenient(false);
			java.util.Date fecha = (Date) formatoDelTexto.parse(strFecha);;

			query = session.createQuery(strHQL, Empleados.class);
			query.setParameter("fechalta", fecha);

			empleados = query.list();
			it = empleados.iterator();

			System.out.println("-".repeat(20));
			System.out.println("Empleados con fecha alta: " + strFecha);

			while (it.hasNext()) {
				emple = (Empleados) it.next();

				System.out.println(emple.getApellido());
			}

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}
		
		// ----------------------Ejemplo 5: Empleados de varios departamentos con parametro tipo lista-----------------------
		strHQL = "from Empleados emp where emp.departamentos.deptNo in (:listadep) order by emp.departamentos.deptNo";
		
		List<BigInteger> numeros = new ArrayList<BigInteger>();
		
		numeros.add(BigInteger.valueOf(10));
		numeros.add(BigInteger.valueOf(20));

		query = session.createQuery(strHQL, Empleados.class);
		query.setParameterList("listadep", numeros);
				
		System.out.println("-".repeat(20));
		System.out.println("Empleados del dep 10 y 20");
		
		empleados = query.list();
		
		for(int i = 0; i < empleados.size(); i++) {
			emple = (Empleados) empleados.get(i);
			
			System.out.println(emple.getApellido());
			
		}

		session.close();
	}

}
