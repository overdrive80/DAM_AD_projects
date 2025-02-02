package clases;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class Principal {


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
		C1Especialidad esp = new C1Especialidad();
		esp.setEspecialidad("XA");
		esp.setNombreEspe("XXXXX");
		
		
		try {
			System.out.println("Inserto una fila en la tabla ESPECIALIDAD.");
			
			//sesion.save(esp);
			sesion.persist(esp);
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
