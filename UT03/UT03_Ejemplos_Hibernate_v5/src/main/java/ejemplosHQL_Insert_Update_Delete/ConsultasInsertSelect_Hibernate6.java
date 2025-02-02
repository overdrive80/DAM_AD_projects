package ejemplosHQL_Insert_Update_Delete;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import clases.*;


/**
 * REQUISITOS:
 * 
 * CREAR TABLA CON LOS DATOS A INSERTAR
 * 
DROP TABLE NUEVOS CASCADE CONSTRAINTS ;

CREATE TABLE NUEVOS (
    dept_no INT NOT NULL PRIMARY KEY,
    dnombre VARCHAR2(15),
    loc     VARCHAR2(15)
);

INSERT INTO NUEVOS VALUES (51,'PERSONAL','MADRID');
INSERT INTO NUEVOS VALUES (52,'NÓMINAS','TOLEDO');
INSERT INTO NUEVOS VALUES (53,'OCIO','BARCELONA');

COMMIT;

A continuación, añado la siguiente línea al fichero hibernate.reveng.xml:
<table-filter match-catalog="ejemplo" match-name="nuevos"/>

Y en hibernate.cfg.xml:
  <mapping resource="clases/Nuevos.hbm.xml"/>
 */

public class ConsultasInsertSelect_Hibernate6 {
	private static SessionFactory sesionFactory;

	public static void main(String[] args) {
		// Deshabilitamos el logger de Hibernate
		LoggerHibernate.disable();

		// Creamos una instancia de SessionFactory. Solo es necesario una vez.
		sesionFactory = Conexion.getSessionFactory();

		ejemploInsertDepar();

		// Cerramos sesión
		sesionFactory.close();
		System.exit(0);

	}

	private static void ejemploInsertDepar() {

		// Creamos la sesión de trabajo
		Session session = sesionFactory.openSession();

		// Iniciamos la transacción
		Transaction tx = session.beginTransaction();
		
		try {
			// --------------------EJEMPLO DE INSERT--------------------------
			String queryHQL = "insert into Departamentos (deptNo, dnombre, loc) "
					        + "select n.deptNo, n.dnombre, n.loc from Nuevos n";

			Query<?> query = session.createQuery(queryHQL);

			int filasModificadas = query.executeUpdate();

			tx.commit(); // valida la transacción
			
			System.out.println("FILAS INSERTADAS: " + filasModificadas); // Nº entidades afectadas

		} catch (Exception e) {
			ManejoExcepciones.imprimirMensajeConsola(e);
		}

		session.close();
	}
	

}
