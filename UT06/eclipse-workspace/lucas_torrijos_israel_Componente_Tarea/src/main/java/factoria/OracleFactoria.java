package factoria;

import java.sql.Connection;

import clasesfuncionales.BaseDatos;
import implDAO.*;
import interfacesDAO.*;


public class OracleFactoria extends FactoriaDAO {
	
	private static Connection conexion = null;
	private static BaseDatos baseDatos = null;
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URLDB = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO = "VUELOS";
    private static final String CLAVE = "vuelos";
    
    public OracleFactoria() {
    	
    }
	
	public static Connection crearConexion() {
		
		//Sigue patrón singleton, si ya existe instancia omitirá la creación
		baseDatos = BaseDatos.crearInstancia(DRIVER, URLDB, USUARIO, CLAVE);
		
		//Si no existe conexión la crea
		if (conexion == null) {
			conexion = baseDatos.getConexion();
			
		}
		
		return conexion;
	}
	
	public AeropuertoDAO getAeropuertoDAO() {
		// Devuelve una implemetación de la interfaz encapsulada en su clase padre.
		return new OracleAeropuertoDAO();
	}
	
	public PasajeDAO getPasajeDAO() {

		return new OraclePasajeDAO();
	}

	@Override
	public PasajeroDAO getPasajeroDAO() {

		return new OraclePasajeroDAO();
	}

	@Override
	public VueloDAO getVueloDAO() {

		return new OracleVueloDAO();
	}
}
