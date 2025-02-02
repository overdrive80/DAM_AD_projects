package gestionHibernate;

import java.time.format.DateTimeParseException;

import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;

import org.hibernate.TransientPropertyValueException;

public class ManejoExcepciones {

	public static void imprimirMensajeConsola(Exception e) {
		//Jakarta Exceptions
		if (e instanceof ConstraintViolationException || 
			e instanceof DataException || 
			e instanceof GenericJDBCException) {
			
			imprimirErrorHibernate((JDBCException) e);
		
		//Javax Exceptions
		} else if (e instanceof javax.persistence.PersistenceException) {
		    Throwable cause = e.getCause();
		    
		    //Causes exception javax
		    if (cause instanceof ConstraintViolationException ||
		        cause instanceof DataException ||
		        cause instanceof GenericJDBCException) {
		    	
		        imprimirErrorHibernate((JDBCException) cause);
		    } else {
		        // Otros tipos de excepciones de persistencia
		        e.printStackTrace();
		    }

		} else if (e instanceof TransientPropertyValueException) {
			imprimirErrorHibernate((TransientPropertyValueException) e);

		} else if (e instanceof DateTimeParseException) {
			imprimirErrorParseado((DateTimeParseException) e);

		} else {

			System.out.println("Se produjo una excepción no controlada: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void imprimirErrorHibernate(JDBCException e) {
		System.out.printf("MENSAJE: %s%n", e.getMessage());
		System.out.printf("CAUSA: %s%n", (e.getCause().toString()).trim());
		System.out.printf("COD ERROR: %d%n", e.getErrorCode());
		System.out.printf("ERROR SQL: %s%n", e.getSQLState());
	}

	private static void imprimirErrorHibernate(TransientPropertyValueException e) {
		System.out.printf("MENSAJE: %s%n", e.getMessage());
		System.out.printf("PROPIEDAD: %s%n", e.getPropertyName());
	}

	private static void imprimirErrorParseado(DateTimeParseException e) {
		System.out.printf("MENSAJE: %s%n", e.getMessage());
		System.out.printf("CAUSA: %s%n", e.getCause());
		System.out.printf("STACKTRACE: %s%n", e.getStackTrace()[0]);
	}
}