package principal;

import java.math.BigInteger;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import clases.Departamentos;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class Principal {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// Desactivar Log de Hibernate
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		
		// Inicializa el entorno Hibernate
		Configuration cfg = new Configuration().configure();
		
		// Crea el ejemplar de sesion factory (fabrica de sesiones)
		SessionFactory sessionFactory = cfg.buildSessionFactory();
		
		// Obtiene un objeto session
		Session sesion = sessionFactory.openSession();
		Transaction tx = sesion.beginTransaction();
	
		// Creamos un departamento
		Departamentos dep = new Departamentos();
		dep.setDeptNo(BigInteger.valueOf(63));
		dep.setDnombre("MARKETING");
		dep.setLoc("GUADALAJARA");
		
		try {
			System.out.println("Inserto una fila en la tabla DEPARTAMENTOS.");
			
			sesion.save(dep);
			tx.commit();
			System.out.println("REGISTRO GRABADO ");
		} catch (JDBCException j) {
			System.out.println("Codigo error: " + j.getErrorCode());
			System.out.println("Mensaje : " + j.getMessage());
		} catch (Exception e) {
			System.out.println("Codigo error: " + e.hashCode());
			System.out.println("Mensaje : " + e.getMessage());
		}
		sesion.close();
		sessionFactory.close();
	}

}
