package gestionHibernate;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerHibernate {
	
	public static void disable() {
		
		// Desactivar Log de Hibernate
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
	}

}
